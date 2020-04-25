package com.spliwise.spliwiseapp.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spliwise.spliwiseapp.entity.transaction.Transaction;
import com.spliwise.spliwiseapp.exceptionhandling.exception.InvalidTransactionException;
import com.spliwise.spliwiseapp.request.TransactionRequest;
import com.spliwise.spliwiseapp.service.splitservice.SplitByPercent;
import com.spliwise.spliwiseapp.service.splitservice.SplitByShare;
import com.spliwise.spliwiseapp.service.splitservice.SplitEqually;
import com.spliwise.spliwiseapp.service.splitservice.SplitFunction;
import com.spliwise.spliwiseapp.service.splitservice.SplitUnequally;
import com.spliwise.spliwiseapp.service.splitservice.TransactionSplitFunction;

@Component
public class TransactionFactory {
	private static TransactionFactory instance = null;
	private static Logger logger = LoggerFactory.getLogger(TransactionFactory.class);

	private TransactionFactory() {
	}

	public TransactionFactory getInstance() {
		if (instance == null)
			instance = new TransactionFactory();

		return instance;
	}

	public Transaction createTransaction(TransactionRequest transactionRequest) {
		Transaction transaction = null;
		SplitFunction splitFunction = null;
		TransactionSplitFunction txnSplitFunction = transactionRequest.getSplitFunction();

		if (txnSplitFunction == TransactionSplitFunction.SPLIT_EQUALLY) {
			splitFunction = new SplitEqually();
		} else if (txnSplitFunction == TransactionSplitFunction.SPLIT_UNEQUALLY) {
			splitFunction = new SplitUnequally();
		} else if (txnSplitFunction == TransactionSplitFunction.SPLIT_BY_PERCENT) {
			splitFunction = new SplitByPercent();
		} else if (txnSplitFunction == TransactionSplitFunction.SPLIT_BY_SHARE) {
			splitFunction = new SplitByShare();
		} else {
			String err = "Invalid split function entered.";
			logger.error(err);
			throw new InvalidTransactionException(err);
		}

		if (splitFunction.validateTransactionRequest(transactionRequest)) {
			transaction = new Transaction(splitFunction, transactionRequest.getPayers(),
					transactionRequest.getPayees());
		} else {
			String err = "Invalid transaction.";
			logger.error(err);
			throw new InvalidTransactionException(err);
		}
		return transaction;
	}
}
