package com.spliwise.spliwiseapp.service.splitservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.exceptionhandling.exception.InvalidTransactionException;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.service.lendowemap.LendOweMapper;
import com.spliwise.spliwiseapp.service.lendowemap.SortMapper;

public class SplitByPercent implements SplitFunction {

	@Autowired
	LendOweMapper lendOweMapper = new SortMapper();
	private static Logger logger = LoggerFactory.getLogger(SplitByPercent.class);

	@Override
	public List<LedgerEntry> computeLedgerEntries(List<Payer> payers, List<Payee> payees) {
		double totalAmount = 0;
		
		for (Payer payer : payers)
			totalAmount += payer.getAmount();
		
		Payee payee;
		double amount, countAmount = 0;
		for (int i =0 ; i<payees.size()-1; i++)
		{
			payee = payees.get(i);
			amount = totalAmount * (payee.getAmount()*0.01);
			countAmount += amount;
			payee.setAmount(amount);
		}
		
		payee = payees.get(payees.size()-1);
		amount = totalAmount - countAmount;
		payee.setAmount(amount);
		
		SplitFunction splitUnequally = new SplitUnequally();
		return splitUnequally.computeLedgerEntries(payers, payees);
	}

	@Override
	public boolean validateTransactionRequest(TransactionRequest transactionRequest) {
		int totalPercent = 0;

		for (Payee payee : transactionRequest.getPayees())
			totalPercent += payee.getAmount();

		if (totalPercent != 100) {
			String err = "Total percentage must equal 100.";
			logger.error(err);
			throw new InvalidTransactionException(err);
		}
		
		return true;
	}
}
