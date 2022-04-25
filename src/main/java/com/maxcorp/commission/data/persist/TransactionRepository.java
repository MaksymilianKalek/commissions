package com.maxcorp.commission.data.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByClientIdAndDateIsBetween(long clientId, LocalDate dateStart, LocalDate dateEnd);
}
