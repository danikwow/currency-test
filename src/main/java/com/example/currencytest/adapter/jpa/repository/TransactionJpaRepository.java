package com.example.currencytest.adapter.jpa.repository;

import com.example.currencytest.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCreateDateBetween(Date startDate, Date endDate);
}
