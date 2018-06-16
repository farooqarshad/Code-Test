package com.code.challenge.n26.service;

import com.code.challenge.n26.exception.TransactionExpiredException;
import com.code.challenge.n26.exception.TransactionOutOfFutureWindow;
import com.code.challenge.n26.persistence.entity.Transaction;
import com.code.challenge.n26.presentation.json.TransactionPostJson;

public interface TransactionService {
	
	public Transaction process(TransactionPostJson json) throws TransactionExpiredException, TransactionOutOfFutureWindow;

}
