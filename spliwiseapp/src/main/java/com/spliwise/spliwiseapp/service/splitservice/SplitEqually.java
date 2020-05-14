package com.spliwise.spliwiseapp.service.splitservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.service.lendowemap.LendOweMapper;
import com.spliwise.spliwiseapp.service.lendowemap.SortMapper;

public class SplitEqually implements SplitFunction {

	@Autowired
	LendOweMapper lendOweMapper;

	public SplitEqually() {
		this.lendOweMapper = new SortMapper();
	}

	public SplitEqually(LendOweMapper lendOweMapper) {
		this.lendOweMapper = lendOweMapper;
	}

	@Override
	public List<LedgerEntry> computeLedgerEntries(List<Payer> payers, List<Payee> payees) {
		for (Payee payee : payees)
			payee.setAmount(1);

		SplitFunction splitByShare = new SplitByShare(this.lendOweMapper);
		return splitByShare.computeLedgerEntries(payers, payees);
	}

	@Override
	public boolean validateTransactionRequest(TransactionRequest transactionRequest) {
		return true;
	}

}
