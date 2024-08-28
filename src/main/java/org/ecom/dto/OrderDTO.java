package org.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	private long id;
	private String paymentId;
	private double price;
	private String orderStatus;
	private long userId;

	

}
