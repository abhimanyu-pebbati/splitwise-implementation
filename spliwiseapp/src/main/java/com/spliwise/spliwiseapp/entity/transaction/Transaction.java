package com.spliwise.spliwiseapp.entity.transaction;

import java.util.List;

import com.spliwise.spliwiseapp.service.splitservice.SplitFunction;

import lombok.Data;

@Data
public class Transaction {
	private SplitFunction splitFunction;
	private List<String> ledgerEntryIds;
	private List<Payer> payers;
	private List<Payee> payees;

	public Transaction(SplitFunction splitFunction, List<Payer> payers, List<Payee> payees) {
		this.splitFunction = splitFunction;
		this.payers = payers;
		this.payees = payees;
	}

}
