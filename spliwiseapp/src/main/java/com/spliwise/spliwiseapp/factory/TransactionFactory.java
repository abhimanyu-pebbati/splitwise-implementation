package com.spliwise.spliwiseapp.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.entity.transaction.Transaction;
import com.spliwise.spliwiseapp.service.splitservice.SplitByPercent;
import com.spliwise.spliwiseapp.service.splitservice.SplitByShare;
import com.spliwise.spliwiseapp.service.splitservice.SplitEqually;
import com.spliwise.spliwiseapp.service.splitservice.SplitFunction;
import com.spliwise.spliwiseapp.service.splitservice.SplitUnequally;
import com.spliwise.spliwiseapp.util.Constants;

@Component
public class TransactionFactory {
	private static TransactionFactory instance = null;

	private TransactionFactory() {
	}

	public TransactionFactory getInstance() {
		if (instance == null)
			instance = new TransactionFactory();

		return instance;
	}

	public Transaction createTransaction(String splitFunctionStr, List<Payer> payers, List<Payee> payees) {
		if (!this.dataValidation(splitFunctionStr, payers, payees))
			return null;

		Transaction transaction = null;
		if (splitFunctionStr.equalsIgnoreCase(Constants.SPLIT_EQUALLY)) {
			SplitFunction splitFunction = new SplitEqually();
			transaction = new Transaction(splitFunction, payers, payees);
		}

		if (splitFunctionStr.equalsIgnoreCase(Constants.SPLIT_UNEQUALLY)) {
			SplitFunction splitFunction = new SplitUnequally();
			transaction = new Transaction(splitFunction, payers, payees);
		}

		if (splitFunctionStr.equalsIgnoreCase(Constants.SPLIT_BY_PERCENT)) {
			SplitFunction splitFunction = new SplitByPercent();
			transaction = new Transaction(splitFunction, payers, payees);
		}

		if (splitFunctionStr.equalsIgnoreCase(Constants.SPLIT_BY_SHARE)) {
			SplitFunction splitFunction = new SplitByShare();
			transaction = new Transaction(splitFunction, payers, payees);
		}

		return transaction;
	}

	private boolean dataValidation(String splitFunctionStr, List<Payer> payers, List<Payee> payees) {
		if (splitFunctionStr.equalsIgnoreCase(Constants.SPLIT_UNEQUALLY)) {
			int totalLent = 0, totalBorrowed = 0;

			for (Payer payer : payers)
				totalLent += payer.getAmount();

			for (Payee payee : payees)
				totalBorrowed += payee.getAmount();

			if (totalLent != totalBorrowed) {
				System.out.println("Total amount lent must equal total amount borrowed.");
				return false;
			}
		}

		if (splitFunctionStr.equalsIgnoreCase(Constants.SPLIT_BY_PERCENT)) {
			int totalPercent = 0;

			for (Payee payee : payees)
				totalPercent += payee.getAmount();

			if (totalPercent != 100) {
				System.out.println("Total percentage must equal 100.");
				return false;
			}
		}

		if (splitFunctionStr.equalsIgnoreCase(Constants.SPLIT_BY_SHARE)) {
			for (Payee payee : payees) {
				if (payee.getAmount() <= 0) {
					System.out.println("Share has to be greater than zero.");
					return false;
				}
			}
		}

		return true;
	}
}
