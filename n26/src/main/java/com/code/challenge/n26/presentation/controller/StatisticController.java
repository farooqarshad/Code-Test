package com.code.challenge.n26.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.challenge.n26.persistence.entity.Statistic;
import com.code.challenge.n26.service.StatisticService;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
	
	@Autowired
	private StatisticService statisticService;
	
	@GetMapping
	public ResponseEntity<Statistic> findCurrent() {
		
		Statistic statistic = this.statisticService.findCurrent();
		
		ResponseEntity<Statistic> response = new ResponseEntity<Statistic>(statistic, HttpStatus.OK);
		
		return response;
	}
	
	@GetMapping(value="/all")
	public ResponseEntity<List<Statistic>> findAll() {
		
		List<Statistic> statistics = this.statisticService.findAll();
		
		ResponseEntity<List<Statistic>> response = new ResponseEntity<List<Statistic>>(statistics, HttpStatus.OK);
		
		return response;
	}

}
