package org.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
    private long id;
    private int quantity;
    private double totalAmount;
    private long orderId;   
    private long productId; 
    
}
