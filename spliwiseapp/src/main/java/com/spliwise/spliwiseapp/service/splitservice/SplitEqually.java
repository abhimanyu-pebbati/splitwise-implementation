package com.spliwise.spliwiseapp.service.splitservice;

import java.util.List;

import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.service.lendowemap.LendOweMapper;

public class SplitEqually extends SplitFunction {

	public SplitEqually() {
		super();
	}

	public SplitEqually(LendOweMapper lendOweMapper) {
		super(lendOweMapper);
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
