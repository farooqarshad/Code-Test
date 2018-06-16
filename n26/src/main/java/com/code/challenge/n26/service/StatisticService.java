package com.code.challenge.n26.service;

import java.util.List;

import com.code.challenge.n26.exception.TransactionExpiredException;
import com.code.challenge.n26.exception.TransactionOutOfFutureWindow;
import com.code.challenge.n26.persistence.entity.Statistic;
import com.code.challenge.n26.persistence.entity.Transaction;

public interface StatisticService {
	
	public List<Statistic> findAll();
	public Statistic findCurrent();
	
	public void add(Transaction transaction) throws TransactionExpiredException, TransactionOutOfFutureWindow;
	
}
