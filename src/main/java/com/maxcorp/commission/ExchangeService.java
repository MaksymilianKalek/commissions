package com.maxcorp.commission;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class ExchangeService {

    private ExchangeService() {
        throw new IllegalStateException("Utility class");
    }

    public static double convertToEUR(String currency, String date, String amount) throws IllegalArgumentException {
        var exchangeRateConvertUrl = "https://api.exchangerate.host/convert?from=" + currency + "&to=EUR&date=" + date + "&amount=" + amount;
        var response = (new RestTemplate()).getForEntity(exchangeRateConvertUrl, String.class);
        var jsonResponse = new JSONObject(response.getBody());
        var convertedAmount = jsonResponse.get("result").toString();
        if (convertedAmount.equals("null") || Double.parseDouble(amount) == Double.parseDouble(convertedAmount)) {
            throw new IllegalArgumentException();
        }
        return Double.parseDouble(convertedAmount);
    }

}
