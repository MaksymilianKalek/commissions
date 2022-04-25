package com.maxcorp.commission;

import com.maxcorp.commission.data.persist.Transaction;
import com.maxcorp.commission.data.persist.TransactionService;
import com.maxcorp.commission.data.rest.CommissionRequest;
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
        var amount = Double.parseDouble(commissionRequest.getAmount());
        if (!commissionRequest.getCurrency().equals(EUR)) {
            amount = ExchangeService.convertToEUR(commissionRequest.getCurrency(), date, commissionRequest.getAmount());
        }

        var clientId = commissionRequest.getClientId();

        var clientsMonthlyTransactions = transactionService.getMonthlyTransactions(clientId, LocalDate.parse(commissionRequest.getDate()));
        var sumOfClientsMonthlyTransactions = clientsMonthlyTransactions.stream().mapToDouble(Transaction::getAmount).sum();

        var newTransaction = Transaction.builder().amount(amount).clientId(clientId).date(LocalDate.parse(date)).build();
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
