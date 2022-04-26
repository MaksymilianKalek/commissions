package com.maxcorp.commission.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxcorp.commission.CommissionCalculator;
import com.maxcorp.commission.rest.entities.CommissionRequest;
import com.maxcorp.commission.rest.entities.CommissionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommissionController {

    private static final Logger logger = LoggerFactory.getLogger(CommissionController.class);
    private final CommissionCalculator commissionCalculator;

    @Autowired
    public CommissionController(CommissionCalculator commissionCalculator) {
        this.commissionCalculator = commissionCalculator;
    }

    @PostMapping(value = "/calculate-commission")
    public ResponseEntity<CommissionResponse> getCalculatedCommission(@RequestBody CommissionRequest commissionRequest) {
        try {
            var commission = commissionCalculator.calculateCommission(commissionRequest);
            return ResponseEntity.ok(new CommissionResponse(commission, "EUR"));
        } catch (IllegalArgumentException illegalArgumentException) {
            try {
                var mapper = new ObjectMapper();
                logger.error("Error during processing of commission request: {}", mapper.writeValueAsString(commissionRequest));
            } catch (JsonProcessingException jsonProcessingException) {
                logger.error("Invalid request JSON");
            }
        } catch (NullPointerException nullPointerException) {
            logger.error("All properties need to be filled");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
