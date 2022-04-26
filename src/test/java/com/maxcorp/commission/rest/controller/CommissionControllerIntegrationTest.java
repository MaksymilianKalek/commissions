package com.maxcorp.commission.rest.controller;

import com.maxcorp.commission.data.TransactionRepository;
import com.maxcorp.commission.rest.entities.CommissionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CommissionControllerIntegrationTest {

    @Autowired
    private CommissionController commissionController;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void before() {
        transactionRepository.deleteAll();
    }

    @AfterEach
    void after() {
        transactionRepository.deleteAll();
    }

    @Test
    void shouldGetCalculatedCommission() {
        // given
        var commissionRequests = List.of(
                CommissionRequest.builder().date("2021-01-02").amount("2000.00").clientId(42).currency("EUR").build(),
                CommissionRequest.builder().date("2021-01-03").amount("500.00").clientId(1).currency("EUR").build()
        );

        // when
        var commissionResponseEntities = commissionRequests.stream().map(cr -> commissionController.getCalculatedCommission(cr)).toList();

        // then
        assertEquals(2, commissionResponseEntities.size());
        assertEquals("0.05", commissionResponseEntities.get(0).getBody().getAmount());
        assertEquals("EUR", commissionResponseEntities.get(0).getBody().getCurrency());

        assertEquals("2.50", commissionResponseEntities.get(1).getBody().getAmount());
        assertEquals("EUR", commissionResponseEntities.get(1).getBody().getCurrency());
    }

}