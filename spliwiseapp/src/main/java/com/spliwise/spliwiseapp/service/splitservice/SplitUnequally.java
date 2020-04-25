package com.spliwise.spliwiseapp.service.splitservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.spliwise.spliwiseapp.entity.transaction.BalanceOwed;
import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.exceptionhandling.exception.InvalidTransactionException;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.service.lendowemap.LendOweMapper;
import com.spliwise.spliwiseapp.service.lendowemap.SortMapper;

public class SplitUnequally implements SplitFunction {

	@Autowired
	LendOweMapper lendOweMapper = new SortMapper();
	private static Logger logger = LoggerFactory.getLogger(SplitUnequally.class);

	@Override
	public List<LedgerEntry> computeLedgerEntries(List<Payer> payers, List<Payee> payees) {
		Map<String, Payer> payerMap = payers.stream()
				.collect(Collectors.toMap(Payer::getPayerUserId, Function.identity()));
		List<BalanceOwed> balances = new ArrayList<BalanceOwed>();
		BalanceOwed balanceOwed;

		for (Payee payee : payees) {
			balanceOwed = new BalanceOwed(payee.getPayeeUserId(), payee.getAmount());
			if (payerMap.containsKey(payee.getPayeeUserId())) {
				balanceOwed.setAmount(payee.getAmount() - payerMap.get(payee.getPayeeUserId()).getAmount());
				payerMap.remove(payee.getPayeeUserId());
			}
			balances.add(balanceOwed);
		}

		for (Payer payer : payerMap.values()) {
			balanceOwed = new BalanceOwed(payer.getPayerUserId(), payer.getAmount() * -1);
			balances.add(balanceOwed);
		}

		return lendOweMapper.mapLenderBorrowers(balances);
	}

	@Override
	public boolean validateTransactionRequest(TransactionRequest transactionRequest) {
		int totalLent = 0, totalBorrowed = 0;

		for (Payer payer : transactionRequest.getPayers())
			totalLent += payer.getAmount();

		for (Payee payee : transactionRequest.getPayees())
			totalBorrowed += payee.getAmount();

		if (totalLent != totalBorrowed) {
			String err = "Total amount lent must equal total amount borrowed.";
			logger.error(err);
			throw new InvalidTransactionException(err);
		}
		return true;
	}

}
