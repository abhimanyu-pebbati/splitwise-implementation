package com.spliwise.spliwiseapp.service.splitservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.spliwise.spliwiseapp.entity.transaction.BalanceOwed;
import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.service.lendowemap.LendOweMapper;
import com.spliwise.spliwiseapp.service.lendowemap.SortMapper;

public class SplitUnequally implements SplitFunction {

	@Autowired
	LendOweMapper lendOweMapper = new SortMapper();

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

}
