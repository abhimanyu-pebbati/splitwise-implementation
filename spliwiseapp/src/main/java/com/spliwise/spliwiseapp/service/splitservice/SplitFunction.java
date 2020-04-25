package com.spliwise.spliwiseapp.service.splitservice;

import java.util.List;

import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.request.TransactionRequest;

public interface SplitFunction {

	public List<LedgerEntry> computeLedgerEntries(List<Payer> payers, List<Payee> payees);

	public boolean validateTransactionRequest(TransactionRequest transactionRequest);
}