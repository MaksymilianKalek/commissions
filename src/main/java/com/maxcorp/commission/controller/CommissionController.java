package com.maxcorp.commission.controller;

import com.maxcorp.commission.CommissionCalculator;
import com.maxcorp.commission.data.rest.CommissionRequest;
import com.maxcorp.commission.data.rest.CommissionResponse;
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
