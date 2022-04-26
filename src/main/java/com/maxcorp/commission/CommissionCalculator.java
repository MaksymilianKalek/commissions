package com.maxcorp.commission;

import com.maxcorp.commission.data.Transaction;
import com.maxcorp.commission.data.TransactionService;
import com.maxcorp.commission.rest.entities.CommissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CommissionCalculator {

    public static final String EUR = "EUR";
    private final TransactionService transactionService;

    @Autowired
    public CommissionCalculator(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public String calculateCommission(CommissionRequest commissionRequest) {
        var localDate = LocalDate.parse(commissionRequest.getDate());
        var amount = getAmount(commissionRequest);

        var clientId = commissionRequest.getClientId();
        var clientsMonthlyTransactions = transactionService.getMonthlyTransactions(clientId, localDate);
        var sumOfClientsMonthlyTransactions = clientsMonthlyTransactions.stream().mapToDouble(Transaction::getAmount).sum();

        var commission = String.format(Locale.ENGLISH, "%.2f", getLowestCommission(amount, sumOfClientsMonthlyTransactions, clientId));

        var newTransaction = Transaction.builder().amount(amount).clientId(clientId).date(localDate).commission(commission).build();
        transactionService.saveTransaction(newTransaction);

        return commission;
    }

    private double getAmount(CommissionRequest commissionRequest) {
        var currency = commissionRequest.getCurrency();
        if (currency.equals(EUR)) {
            return Double.parseDouble(commissionRequest.getAmount());
        } else {
            return ExchangeService.convertToEUR(currency, commissionRequest.getDate(), commissionRequest.getAmount());
        }
    }

    private double getLowestCommission(double amount, double sumOfClientsMonthlyTransactions, long clientId) {
        List<Double> commissions = new ArrayList<>();
        commissions.add(Math.max(amount * 0.005, 0.05));
        if (clientId == 42) {
            commissions.add(0.05);
        }
        if (sumOfClientsMonthlyTransactions >= 1000) {
            commissions.add(0.03);
        }
        var lowestCommission = commissions.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
        return BigDecimal.valueOf(lowestCommission).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
