package com.maxcorp.commission.data.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getMonthlyTransactions(long clientId, LocalDate date) {
        return transactionRepository.findByClientIdAndDateIsBetween(clientId, date.withDayOfMonth(1), date.withDayOfMonth(date.getMonth().length(date.isLeapYear())));
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
