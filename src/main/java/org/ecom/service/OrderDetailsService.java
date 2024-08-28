package org.ecom.service;

import org.ecom.dto.OrderDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderDetailsService {

	Page<OrderDetailsDTO> findAllOrderDetails(Pageable pageable);

	OrderDetailsDTO findOrderDetailsById(Long id);

	OrderDetailsDTO createOrderDetails(OrderDetailsDTO orderDetailsDTO);

	OrderDetailsDTO updateOrderDetails(Long id, OrderDetailsDTO orderDetailsDTO);

	String deleteOrderDetails(Long id);

}
