package com.spliwise.spliwiseapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spliwise.spliwiseapp.entity.User;
import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.request.UserRequest;
import com.spliwise.spliwiseapp.service.SplitwiseService;

@RestController
public class SplitwiseController {

	@Autowired
	SplitwiseService splitwiseService;

	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public User addUser(@RequestBody UserRequest userRequest) {
		return splitwiseService.addUser(userRequest);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public List<User> addUser(@RequestBody List<UserRequest> userRequest) {
		return splitwiseService.addUsers(userRequest);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/transaction")
	public List<LedgerEntry> addTransaction(@RequestBody TransactionRequest transactionRequest) {
		return splitwiseService.addTransaction(transactionRequest);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user-balance")
	public List<LedgerEntry> viewUserBalances(@RequestParam String userId) {
		return splitwiseService.viewUserBalances(userId);
	}

}