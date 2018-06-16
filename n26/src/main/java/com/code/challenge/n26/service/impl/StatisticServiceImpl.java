package com.code.challenge.n26.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.code.challenge.n26.exception.TransactionExpiredException;
import com.code.challenge.n26.exception.TransactionOutOfFutureWindow;
import com.code.challenge.n26.persistence.entity.Statistic;
import com.code.challenge.n26.persistence.entity.Transaction;
import com.code.challenge.n26.service.StatisticService;
import com.code.challenge.n26.util.DateUtil;

/**
 * 
 * Statistics Service
 * 
 *
 */
@Service
public class StatisticServiceImpl implements StatisticService {

	// Lock for ADD / REMOVE
	private Object LOCK = new Object();
	
	private List<Transaction> transactions;
	
	@Value("${statisticService.windowInMs}")
	private Long windowInMs;
	
	Statistic statistic=null;
	
	public StatisticServiceImpl() {
		 this.transactions = new ArrayList<Transaction>();
		 this.statistic= this.createInitStatistic();
	}
	
	/**
	 * 
	 * Create init statistic
	 * 
	 * Time complexity O(1)
	 * 
	 * @return Statistic with init values
	 */
	private Statistic createInitStatistic() {
		Statistic statistic = new Statistic();
		statistic.setDate(LocalDateTime.now());
		statistic.setMax(0d);
		statistic.setMin(0d);
		statistic.setSum(0.0);
		statistic.setCount(0l);
		return statistic;
	}

	/**
	 * 
	 * Add statistic from transaction
	 * 
	 * Time complexity: O(${windowInMs}/1000 * log statisticTimestamps.size()) -> O(1)
	 * @throws TransactionOutOfFutureWindow 
	 * 
	 */
	@Override
	public void add(Transaction transaction) throws TransactionExpiredException, TransactionOutOfFutureWindow {
		
		Long currentTimestamp = DateUtil.converToTimeStamp(LocalDateTime.now());
		Long transactionTimestamp = DateUtil.converToTimeStamp(transaction.getDate());
		
		if (transactionTimestamp + windowInMs < currentTimestamp) throw new TransactionExpiredException();
		if (currentTimestamp + windowInMs < transactionTimestamp) throw new TransactionOutOfFutureWindow();
		synchronized(LOCK) {
			//ADD Latest Transaction
			transactions.add(transaction);
			
			//Filter out the old ones
			List<Transaction> trs=transactions.stream()
					.filter(tran -> DateUtil.converToTimeStamp(tran.getDate())+windowInMs <currentTimestamp)
					.collect(Collectors.toList());
			
				int count =trs.size();
				double sum = trs.stream().mapToDouble(d-> d.getAmount()).sum();
				double max = trs.stream().mapToDouble(d -> d.getAmount()).max().getAsDouble();
				double min = trs.stream().mapToDouble(d -> d.getAmount()).min().getAsDouble();
				double avg = sum / count;
				
				statistic = this.createInitStatistic();
				statistic.setAvg(avg);
				statistic.setSum(sum);;
				statistic.setMax(max);
				statistic.setMin(min);
				//Reset the list to store only transaction for last 60 secs
				transactions=trs;
				
		}
	}
	
	/**
	 * 
	 * Get current statistic
	 * 
	 * Time complexity: O(1)
	 * 
	 */
	@Override
	public Statistic findCurrent() {
		return statistic;
	}		
}