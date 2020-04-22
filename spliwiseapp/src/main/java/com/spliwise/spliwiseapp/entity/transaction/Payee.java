package com.spliwise.spliwiseapp.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Payee {
	private String payeeUserId;
	private double amount;
}
