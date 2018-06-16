package com.code.challenge.n26.presentation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.challenge.n26.exception.TransactionExpiredException;
import com.code.challenge.n26.exception.TransactionOutOfFutureWindow;
import com.code.challenge.n26.persistence.entity.Transaction;
import com.code.challenge.n26.presentation.json.TransactionPostJson;
import com.code.challenge.n26.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<Transaction> post(@RequestBody @Valid TransactionPostJson bodyJson) {

		try {
			Transaction transaction = this.transactionService.process(bodyJson);
			
			ResponseEntity<Transaction> response = new ResponseEntity<Transaction>(transaction, HttpStatus.CREATED);
			return response; 
		} 
		catch (TransactionExpiredException | TransactionOutOfFutureWindow e) {
			return new ResponseEntity<Transaction>(HttpStatus.NO_CONTENT);
		}
	}

}
