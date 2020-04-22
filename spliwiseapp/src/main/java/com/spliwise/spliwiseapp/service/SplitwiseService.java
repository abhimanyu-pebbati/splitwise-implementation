package com.spliwise.spliwiseapp.service;

import java.util.List;

import com.spliwise.spliwiseapp.entity.User;
import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.request.UserRequest;

public interface SplitwiseService {

	public User addUser(UserRequest userRequest);

	public List<LedgerEntry> addTransaction(TransactionRequest transactionRequest);

	public List<User> addUsers(List<UserRequest> userRequest);

	public List<LedgerEntry> viewUserBalances(String userId);

}
