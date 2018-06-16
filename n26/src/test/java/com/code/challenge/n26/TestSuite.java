package com.code.challenge.n26;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.code.challenge.n26.service.StatisticsServiceTest;
import com.code.challenge.n26.service.TransactionServiceTest;
import com.code.challenge.n26.util.DateUtilTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({ DateUtilTest.class,TransactionServiceTest.class,StatisticsServiceTest.class })
public class TestSuite {

}