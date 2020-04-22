package com.spliwise.spliwiseapp.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceOwed {
	private String borrowedUserId;
	private double amount;
}
