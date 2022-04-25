package com.maxcorp.commission.rest.controller;

import com.maxcorp.commission.CommissionCalculator;
import com.maxcorp.commission.rest.entities.CommissionRequest;
import com.maxcorp.commission.rest.entities.CommissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommissionController {

    @Autowired
    CommissionCalculator commissionCalculator;

    @PostMapping(value = "/calculate-commission")
    public CommissionResponse getCalculatedCommission(@RequestBody CommissionRequest commissionRequest) {
        return new CommissionResponse(commissionCalculator.calculateCommission(commissionRequest), "EUR");
    }

}
