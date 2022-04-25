package com.maxcorp.commission.rest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommissionRequest {

    private String amount;
    private String currency;
    private String date;
    @JsonProperty("client_id")
    private int clientId;

}
