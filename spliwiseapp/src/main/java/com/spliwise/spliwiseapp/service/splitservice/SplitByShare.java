package com.spliwise.spliwiseapp.service.splitservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.entity.transaction.Payee;
import com.spliwise.spliwiseapp.entity.transaction.Payer;
import com.spliwise.spliwiseapp.service.lendowemap.LendOweMapper;
import com.spliwise.spliwiseapp.service.lendowemap.SortMapper;

public class SplitByShare implements SplitFunction {

	@Autowired
	LendOweMapper lendOweMapper = new SortMapper();

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
}
