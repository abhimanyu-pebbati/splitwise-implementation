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

public class SplitByShare implements SplitFunction {

	@Autowired
	LendOweMapper lendOweMapper;
	private static Logger logger = LoggerFactory.getLogger(SplitByShare.class);

	public SplitByShare() {
		this.lendOweMapper = new SortMapper();
	}

	public SplitByShare(LendOweMapper lendOweMapper) {
		this.lendOweMapper = lendOweMapper;
	}

	@Override
	public List<LedgerEntry> computeLedgerEntries(List<Payer> payers, List<Payee> payees) {
		double totalAmount = 0, totalShare = 0;

		for (Payer payer : payers)
			totalAmount += payer.getAmount();

		for (Payee payee : payees)
			totalShare += payee.getAmount();

		Payee payee;
		double amount, countAmount = 0;
		for (int i = 0; i < payees.size() - 1; i++) {
			payee = payees.get(i);
			amount = totalAmount * (payee.getAmount() / totalShare);
			countAmount += amount;
			payee.setAmount(amount);
		}

		payee = payees.get(payees.size() - 1);
		amount = totalAmount - countAmount;
		payee.setAmount(amount);

		SplitFunction splitUnequally = new SplitUnequally();
		return splitUnequally.computeLedgerEntries(payers, payees);
	}

	@Override
	public boolean validateTransactionRequest(TransactionRequest transactionRequest) {
		for (Payee payee : transactionRequest.getPayees()) {
			if (payee.getAmount() <= 0) {
				String err = "Share has to be greater than zero.";
				logger.error(err);
				throw new InvalidTransactionException(err);
			}
		}
		return true;
	}
}
