package com.spliwise.spliwiseapp.service.lendowemap;

import java.util.List;

import com.spliwise.spliwiseapp.entity.transaction.BalanceOwed;
import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;

public interface LendOweMapper {

	public List<LedgerEntry> mapLenderBorrowers(List<BalanceOwed> balance);
}
