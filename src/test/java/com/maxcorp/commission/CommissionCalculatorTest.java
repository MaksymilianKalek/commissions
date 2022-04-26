package com.maxcorp.commission;

import com.maxcorp.commission.data.TransactionRepository;
import com.maxcorp.commission.rest.entities.CommissionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommissionCalculatorTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CommissionCalculator commissionCalculator;

    @BeforeEach
    void before() {
        transactionRepository.deleteAll();
    }

    @AfterEach
    void after() {
        transactionRepository.deleteAll();
    }

    @Test
    void shouldCalculateCommission() {
        // given
        var commissionRequests = List.of(
                CommissionRequest.builder().date("2021-01-02").amount("2000.00").clientId(42).currency("EUR").build(),
                CommissionRequest.builder().date("2021-01-03").amount("500.00").clientId(1).currency("EUR").build(),
                CommissionRequest.builder().date("2021-01-04").amount("499.00").clientId(1).currency("EUR").build(),
                CommissionRequest.builder().date("2021-01-05").amount("100.00").clientId(1).currency("EUR").build(),
                CommissionRequest.builder().date("2021-01-06").amount("1.00").clientId(1).currency("EUR").build(),
                CommissionRequest.builder().date("2021-02-01").amount("500.00").clientId(1).currency("EUR").build()
        );

        // when
        var commissions = commissionRequests.stream().map(cr -> commissionCalculator.calculateCommission(cr)).toList();

        // then
        assertEquals("0.05", commissions.get(0));
        assertEquals("2.50", commissions.get(1));
        assertEquals("2.50", commissions.get(2));
        assertEquals("0.50", commissions.get(3));
        assertEquals("0.03", commissions.get(4));
        assertEquals("2.50", commissions.get(5));
    }
}