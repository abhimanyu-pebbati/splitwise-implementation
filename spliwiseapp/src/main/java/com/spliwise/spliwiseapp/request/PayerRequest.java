package com.spliwise.spliwiseapp.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PayerRequest {
	private String payerFullName;
	private double amount;
}
