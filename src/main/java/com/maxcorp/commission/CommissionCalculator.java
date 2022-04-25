package com.maxcorp.commission;

import com.maxcorp.commission.data.Transaction;
import com.maxcorp.commission.data.TransactionService;
import com.maxcorp.commission.rest.entities.CommissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommissionCalculator {

    public static final String EUR = "EUR";
    private final TransactionService transactionService;

    @Autowired
    public CommissionCalculator(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public String calculateCommission(CommissionRequest commissionRequest) {
        var date = commissionRequest.getDate();
        var localDate = LocalDate.parse(date);
        var amount = Double.parseDouble(commissionRequest.getAmount());
        var currency = commissionRequest.getCurrency();
        if (!commissionRequest.getCurrency().equals(EUR)) {
            try {
                amount = ExchangeService.convertToEUR(currency, date, commissionRequest.getAmount());
            } catch (CommissionException e) {
                System.err.println(e.getDescription() + ": " + currency);
                return null;
            }
        }

        var clientId = commissionRequest.getClientId();
        var clientsMonthlyTransactions = transactionService.getMonthlyTransactions(clientId, localDate);
        var sumOfClientsMonthlyTransactions = clientsMonthlyTransactions.stream().mapToDouble(Transaction::getAmount).sum();

        var newTransaction = Transaction.builder().amount(amount).clientId(clientId).date(localDate).build();
        transactionService.saveTransaction(newTransaction);

        var commissions = getCommissions(amount, sumOfClientsMonthlyTransactions, clientId);
        return String.valueOf(commissions.stream().mapToDouble(Double::doubleValue).min());
    }

    private List<Double> getCommissions(double amount, double sumOfClientsMonthlyTransactions, long clientId) {
        List<Double> commissions = new ArrayList<>();
        commissions.add(amount * 0.005);
        if (clientId == 42) {
            commissions.add(0.05);
        }
        if (sumOfClientsMonthlyTransactions >= 1000) {
            commissions.add(0.03);
        }
        return commissions;
    }

}
