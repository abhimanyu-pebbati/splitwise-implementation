package com.spliwise.spliwiseapp.service.lendowemap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.spliwise.spliwiseapp.entity.transaction.BalanceOwed;
import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;
import com.spliwise.spliwiseapp.util.UniqueIdFunction;

public class SortMapper implements LendOweMapper {

	@Override
	public List<LedgerEntry> mapLenderBorrowers(List<BalanceOwed> balances) {
		balances.sort(Comparator.comparing(BalanceOwed::getAmount));
		List<LedgerEntry> entries = new ArrayList<LedgerEntry>();

		int borrowerIndex = -1;
		int i, b, l;
		for (i = 0; i < balances.size(); i++) {
			if (balances.get(i).getAmount() > 0) {
				borrowerIndex = i;
				break;
			}
		}

		l = 0;
		b = borrowerIndex;
		LedgerEntry ledgerEntry;
		while (l <= borrowerIndex && b < balances.size()) {
			if (Math.abs(balances.get(l).getAmount()) > Math.abs(balances.get(b).getAmount())) {
				ledgerEntry = new LedgerEntry(UniqueIdFunction.generateUniqueId(), balances.get(l).getBorrowedUserId(),
						balances.get(b).getBorrowedUserId(), Math.abs(balances.get(b).getAmount()));
				balances.get(l).setAmount(balances.get(l).getAmount()+balances.get(b).getAmount());
				b++;
			} else if (Math.abs(balances.get(l).getAmount()) < Math.abs(balances.get(b).getAmount())) {
				ledgerEntry = new LedgerEntry(UniqueIdFunction.generateUniqueId(), balances.get(l).getBorrowedUserId(),
						balances.get(b).getBorrowedUserId(), Math.abs(balances.get(l).getAmount()));
				balances.get(b).setAmount(balances.get(l).getAmount()+balances.get(b).getAmount());
				l++;
			} else {
				ledgerEntry = new LedgerEntry(UniqueIdFunction.generateUniqueId(), balances.get(l).getBorrowedUserId(),
						balances.get(b).getBorrowedUserId(), Math.abs(balances.get(l).getAmount()));
				l++;
				b++;
			}

			entries.add(ledgerEntry);
		}
		return entries;
	}

}