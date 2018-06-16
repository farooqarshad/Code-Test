package com.code.challenge.n26.service;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.code.challenge.n26.exception.TransactionExpiredException;
import com.code.challenge.n26.exception.TransactionOutOfFutureWindow;
import com.code.challenge.n26.persistence.entity.Statistic;
import com.code.challenge.n26.persistence.entity.Transaction;
import com.code.challenge.n26.util.DateUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatisticsServiceTest {
	
	@Autowired
	private StatisticService statisticService;
	
	@Value("${statisticService.windowInMs}")
	private Long windowInMs;
	
	@Test(expected = TransactionExpiredException.class)
	public void addExpired() throws TransactionExpiredException, TransactionOutOfFutureWindow {
		
		Transaction transaction = new Transaction();
		transaction.setAmount(5.0);
		transaction.setDate(LocalDateTime.now().minusSeconds(windowInMs/1000).minusSeconds(1l));
		
		this.statisticService.add(transaction);
	}
	
	
	@Test(expected = TransactionOutOfFutureWindow.class)
	public void addOutOfFutureWindow() throws TransactionExpiredException, TransactionOutOfFutureWindow {
		
		Transaction transaction = new Transaction();
		transaction.setAmount(5.0);
		transaction.setDate(LocalDateTime.now().plusSeconds(windowInMs/1000).plusSeconds(1l));
		
		this.statisticService.add(transaction);
	}
	
	@Test
	public void findCurrentEmpty() {
		
		Statistic statistic = this.statisticService.findCurrent();
		Assert.assertEquals(Long.valueOf(0l), statistic.getCount());
	}
	
	

}