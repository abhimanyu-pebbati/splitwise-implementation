package com.spliwise.spliwiseapp.service.splitservice;

import java.util.List;

import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.service.lendowemap.LendOweMapper;
import com.spliwise.spliwiseapp.service.lendowemap.SortMapper;

public abstract class SplitFunction {

	LendOweMapper lendOweMapper;

	public SplitFunction() {
		this.lendOweMapper = new SortMapper();
	}

	public SplitFunction(LendOweMapper lendOweMapper) {
		this.lendOweMapper = lendOweMapper;
	}

	public abstract List<LedgerEntry> computeLedgerEntries(List<Payer> payers, List<Payee> payees);

	public abstract boolean validateTransactionRequest(TransactionRequest transactionRequest);
}