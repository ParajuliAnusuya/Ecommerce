package org.ecom.service;

import org.ecom.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
	Page<OrderDTO> findAllOrders(Pageable pageable);

	OrderDTO findOrderById(Long id);

	OrderDTO createOrder(OrderDTO orderDTO);

	OrderDTO updateOrder(Long id, OrderDTO orderDTO);

	String deleteOrder(Long id);

}
