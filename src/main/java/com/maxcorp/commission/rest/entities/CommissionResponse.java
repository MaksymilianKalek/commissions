package com.maxcorp.commission.rest.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommissionResponse {

    private String amount;
    private String currency;

}
