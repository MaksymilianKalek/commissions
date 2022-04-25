package com.maxcorp.commission.data.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommissionRequest {

    private String amount;
    private String currency;
    private String date;
    private long clientId;

}
