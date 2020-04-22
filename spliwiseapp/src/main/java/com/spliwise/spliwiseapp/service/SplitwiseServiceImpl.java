package com.spliwise.spliwiseapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spliwise.spliwiseapp.entity.User;
import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Transaction;
import com.spliwise.spliwiseapp.factory.TransactionFactory;
import com.spliwise.spliwiseapp.factory.UserFactory;
import com.spliwise.spliwiseapp.model.LedgerEntryModel;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.request.UserRequest;

@Service
public class SplitwiseServiceImpl implements SplitwiseService {

	@Autowired
	private UserFactory userFactory;

	@Autowired
	private TransactionFactory transactionFactory;

	private LedgerEntryModel ledgerEntryModel = LedgerEntryModel.getInstance();

	@Override
	public User addUser(UserRequest userRequest) {
		return userFactory.createUser(userRequest.getFullName(), userRequest.getEmail());
	}

	@Override
	public List<User> addUsers(List<UserRequest> userRequests) {
		List<User> users = new ArrayList<User>();
		for (UserRequest userRequest : userRequests)
			users.add(userFactory.createUser(userRequest.getFullName(), userRequest.getEmail()));
		return users;
	}

	@Override
	public List<LedgerEntry> addTransaction(TransactionRequest transactionRequest) {
		Transaction transaction = transactionFactory.createTransaction(transactionRequest.getSplitFunction(),
				transactionRequest.getPayers(), transactionRequest.getPayees());
		List<LedgerEntry> ledgerEntries = transaction.getSplitFunction().computeLedgerEntries(transaction.getPayers(),
				transaction.getPayees());
		ledgerEntryModel.persistLedgerEntries(ledgerEntries);
		System.out.println(ledgerEntries);

		return ledgerEntries;
	}

	@Override
	public List<LedgerEntry> viewUserBalances(String userId) {
		List<LedgerEntry> ledgerEntries = ledgerEntryModel.getUserLedgers(userId);
		Map<String, LedgerEntry> map = new HashMap<String, LedgerEntry>();
		LedgerEntry ledgerEntry;

		for (LedgerEntry le : ledgerEntries) {
			if (le.getPayeeUserId().equalsIgnoreCase(userId)) {
				if (!map.containsKey(le.getPayerUserId()))
					map.put(le.getPayerUserId(), new LedgerEntry(null, userId, le.getPayerUserId(), 0));
				map.get(le.getPayerUserId())
						.setAmount(map.get(le.getPayerUserId()).getAmount() + (le.getAmount() * -1));
			} else if (le.getPayerUserId().equalsIgnoreCase(userId)) {
				if (!map.containsKey(le.getPayeeUserId()))
					map.put(le.getPayeeUserId(), new LedgerEntry(null, userId, le.getPayeeUserId(), 0));
				map.get(le.getPayeeUserId()).setAmount(map.get(le.getPayeeUserId()).getAmount() + le.getAmount());
			}
		}
		return map.values().stream().collect(Collectors.toList());
	}

}
