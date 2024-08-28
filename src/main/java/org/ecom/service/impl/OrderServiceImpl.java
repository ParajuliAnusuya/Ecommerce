package org.ecom.service.impl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.ecom.dto.OrderDTO;
import org.ecom.entity.Order;
import org.ecom.entity.User;
import org.ecom.exception.OrderNotFoundException;
import org.ecom.repository.OrderRepo;
import org.ecom.repository.UserRepository;
import org.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepo repo;
	
	@Autowired
	private UserRepository userRepo;

	public OrderDTO convertToDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setPaymentId(order.getPayment_id());
		orderDTO.setPrice(order.getPrice());
		orderDTO.setOrderStatus(order.getOrder_status());

		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findAllOrders(Pageable pageable) {
		return repo.findAllActiveOrders(pageable).map(this::convertToDTO);
	}

	@Override
	public OrderDTO findOrderById(Long id) {
		Optional<Order> orderCat = repo.findActiveOrderById(id);
		Order order = orderCat.orElseThrow(() -> new OrderNotFoundException("Order does not exist"));
		return convertToDTO(order);
	}

	@Override
	public OrderDTO createOrder(OrderDTO orderDTO) {
		System.out.println("-------------------------");
		System.out.println(orderDTO.getUserId());
		Order order = new Order();
		order.setPayment_id(orderDTO.getPaymentId());
		order.setPrice(orderDTO.getPrice());
		order.setOrder_status(orderDTO.getOrderStatus());

		
		User user = userRepo.findById(orderDTO.getUserId()); 
		order.setUser(user);
		Order savedOrder = repo.save(order);
		
		return convertToDTO(savedOrder);// Convert the saved Order entity back to OrderDTO and return it
	}

	@Override
	public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
		Order order = new Order();

		order.setPayment_id(orderDTO.getPaymentId());
		order.setPrice(orderDTO.getPrice());
		order.setOrder_status(orderDTO.getOrderStatus());
		
		User user = userRepo.findById(orderDTO.getUserId()); 
		order.setUser(user);


		Order updatedOrder = repo.save(order);
		return convertToDTO(updatedOrder);

	}

	@Override
	public String deleteOrder(Long id) {
		//String msg = "Order deleted successfully";
		repo.findActiveOrderById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order does not exist"));

		repo.softDeleteOrderById(id);

		return "Order deleted successfully";
	}

}
