package com.spliwise.spliwiseapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.spliwise.spliwiseapp.entity.transaction.LedgerEntry;

public class LedgerEntryModel {
	private static LedgerEntryModel instance;

	private Map<String, LedgerEntry> ledgerTable;

	private LedgerEntryModel() {
		ledgerTable = new HashMap<String, LedgerEntry>();
	}

	public static LedgerEntryModel getInstance() {
		if (instance == null)
			instance = new LedgerEntryModel();

		return instance;
	}

	public void persistLedgerEntries(List<LedgerEntry> ledgerEntries) {
		ledgerTable.putAll(
				ledgerEntries.stream().collect(Collectors.toMap(LedgerEntry::getLedgerEntryId, Function.identity())));
	}

	public void persistLedgerEntry(LedgerEntry ledgerEntry) {
		ledgerTable.put(ledgerEntry.getLedgerEntryId(), ledgerEntry);
	}
	
	public List<LedgerEntry> getUserLedgers(String userId)
	{
		List<LedgerEntry> ledgerEntries = new ArrayList<LedgerEntry>();
		LedgerEntry ledgerEntry ;
		for (String ledgerId : this.ledgerTable.keySet())
		{
			ledgerEntry = this.ledgerTable.get(ledgerId);
			if (ledgerEntry.getPayeeUserId().equalsIgnoreCase(userId) || ledgerEntry.getPayerUserId().equalsIgnoreCase(userId))
			{
				ledgerEntries.add(ledgerEntry);
			}
		}
		
		return ledgerEntries;
	}
}
