package com.spliwise.spliwiseapp.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LedgerEntry {
	private String ledgerEntryId;
	private String payerUserId;
	private String payeeUserId;
	private double amount;
}
