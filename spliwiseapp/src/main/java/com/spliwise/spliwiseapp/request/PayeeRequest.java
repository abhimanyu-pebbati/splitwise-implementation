package com.spliwise.spliwiseapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PayeeRequest {
	private String payeeFullName;
	private double amount;
}
