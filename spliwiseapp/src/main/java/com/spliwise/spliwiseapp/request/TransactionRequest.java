package com.spliwise.spliwiseapp.request;

import java.util.List;

import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.service.splitservice.TransactionSplitFunction;

import lombok.Data;

@Data
public class TransactionRequest {

	// private String splitFunction;
	private TransactionSplitFunction splitFunction;
	private List<Payer> payers;
	private List<Payee> payees;

}
