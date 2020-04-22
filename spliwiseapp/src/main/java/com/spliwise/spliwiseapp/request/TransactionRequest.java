package com.spliwise.spliwiseapp.request;

import java.util.List;

import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;

import lombok.Data;

@Data
public class TransactionRequest {

	private String splitFunction;
	private List<Payer> payers;
	private List<Payee> payees;

}
