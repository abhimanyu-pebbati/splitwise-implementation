package com.spliwise.spliwiseapp.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Payer {
	private String payerUserId;
	private double amount;
}
