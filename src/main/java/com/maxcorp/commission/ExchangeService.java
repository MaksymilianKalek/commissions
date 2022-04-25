package com.maxcorp.commission;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class ExchangeService {

    public static double convertToEUR(String currency, String date, String amount) throws CommissionException {
        var exchangeRateConvertUrl = "https://api.exchangerate.host/convert?from=EUR&to=" + currency + "&date=" + date + "&amount=" + amount;
        var response = (new RestTemplate()).getForEntity(exchangeRateConvertUrl, String.class);
        var jsonResponse = new JSONObject(response.getBody());
        var convertedAmount = jsonResponse.get("result").toString();
        if (convertedAmount.equals("null")) {
            throw new CommissionException("Wrong currency");
        }
        return Double.parseDouble(convertedAmount);
    }

}
