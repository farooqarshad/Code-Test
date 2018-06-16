package com.code.challenge.n26.util;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {
	
	@Test
	public void converToTimeStamp() {

		Long timestamp = DateUtil.converToTimeStamp(LocalDateTime.of(2017, 10, 01, 9, 45, 30));
		Assert.assertEquals(Long.valueOf(1506851130000l), timestamp);
		
	}
	
}