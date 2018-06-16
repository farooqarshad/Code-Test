package com.code.challenge.n26.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.challenge.n26.exception.TransactionExpiredException;
import com.code.challenge.n26.exception.TransactionOutOfFutureWindow;
import com.code.challenge.n26.persistence.entity.Transaction;
import com.code.challenge.n26.presentation.json.TransactionPostJson;
import com.code.challenge.n26.service.StatisticService;
import com.code.challenge.n26.service.TransactionService;
import com.code.challenge.n26.util.DateUtil;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private StatisticService statisticService;

	@Override
	public Transaction process(TransactionPostJson json) throws TransactionExpiredException, TransactionOutOfFutureWindow {
		
		Transaction transaction = new Transaction();
		transaction.setAmount(json.getAmount());
		transaction.setDate(DateUtil.convertToLocalDateTime(json.getTimestamp()));
		
		this.statisticService.add(transaction);
		
		return transaction;
		
	}
	
}
