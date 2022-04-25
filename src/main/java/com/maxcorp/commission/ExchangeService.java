package com.maxcorp.commission;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class ExchangeService {

    public static double convertToEUR(String currency, String date, String amount) {
        var exchangeRateConvertUrl = "https://api.exchangerate.host/convert?from=EUR&to=" + currency + "&date=" + date + "&amount=" + amount;
        var response = (new RestTemplate()).getForEntity(exchangeRateConvertUrl, String.class);
        var jsonResponse = new JSONObject(response.getBody());
        return Double.parseDouble(jsonResponse.get("result").toString());
    }

}
