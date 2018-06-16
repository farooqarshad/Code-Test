package com.code.challenge.n26.service;


import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.code.challenge.n26.exception.TransactionExpiredException;
import com.code.challenge.n26.exception.TransactionOutOfFutureWindow;
import com.code.challenge.n26.presentation.json.TransactionPostJson;
import com.code.challenge.n26.util.DateUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {
	
	@Autowired
	private TransactionService transactionService;
	
	@Value("${statisticService.windowInMs}")
	private Long windowInMs;
	
	@Test(expected = TransactionOutOfFutureWindow.class)
	public void processExpired() throws TransactionExpiredException, TransactionOutOfFutureWindow {
		
		TransactionPostJson json = new TransactionPostJson();
		json.setAmount(6.0);
		json.setTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now()) - windowInMs - 1000);
		
		this.transactionService.process(json);
	}
	
	@Test(expected = TransactionOutOfFutureWindow.class)
	public void processFuture() throws TransactionExpiredException, TransactionOutOfFutureWindow {
		
		TransactionPostJson json = new TransactionPostJson();
		json.setAmount(6.0);
		json.setTimestamp(DateUtil.converToTimeStamp(LocalDateTime.now()) + windowInMs + 1000);
		
		this.transactionService.process(json);
	}
	

}