package org.ecom.service.impl;

import java.util.Optional;
import org.ecom.dto.OrderDetailsDTO;
import org.ecom.entity.Category;
import org.ecom.entity.Order;
import org.ecom.entity.OrderDetails;
import org.ecom.entity.Products;
import org.ecom.exception.CategoryNotFoundException;
import org.ecom.exception.OrderDetailsNotFoundException;
import org.ecom.exception.OrderNotFoundException;
import org.ecom.exception.ProductNotFoundException;
import org.ecom.repository.OrderDetailsRepo;
import org.ecom.repository.OrderRepo;
import org.ecom.repository.ProductRepo;
import org.ecom.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsImpl implements OrderDetailsService {
	@Autowired
	private OrderDetailsRepo repo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private ProductRepo productRepo;

	public OrderDetailsDTO convertToDTO(OrderDetails orderDetails) {
		OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();

		orderDetailsDTO.setId(orderDetails.getId());
		orderDetailsDTO.setQuantity(orderDetails.getQuantity());
		orderDetailsDTO.setTotalAmount(orderDetails.getTotal_amount());

		orderDetailsDTO.setOrderId(orderDetails.getOrder().getId());
		orderDetailsDTO.setProductId(orderDetails.getProduct().getId());

		return orderDetailsDTO;
	}

	@Override
	public Page<OrderDetailsDTO> findAllOrderDetails(Pageable pageable) {
		return repo.findAllActiveOrderDetails(pageable).map(this::convertToDTO);
	}

	@Override
	public OrderDetailsDTO findOrderDetailsById(Long id) {
		Optional<OrderDetails> orderDetailsOpt = repo.findActiveOrderDetailsById(id);
		OrderDetails orderDetails = orderDetailsOpt
				.orElseThrow(() -> new OrderDetailsNotFoundException("Order details not found"));
		return convertToDTO(orderDetails);
	}

	@Override
	public OrderDetailsDTO createOrderDetails(OrderDetailsDTO orderDetailsDTO) {

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setQuantity(orderDetailsDTO.getQuantity());
		orderDetails.setTotal_amount(orderDetailsDTO.getTotalAmount());

		Order order = orderRepo.findById(orderDetailsDTO.getOrderId()).orElseThrow(
				() -> new OrderNotFoundException("Order not found with id " + orderDetailsDTO.getOrderId()));
		Products product = productRepo.findById(orderDetailsDTO.getProductId()).orElseThrow(
				() -> new ProductNotFoundException("Product not found with id " + orderDetailsDTO.getProductId()));

		orderDetails.setOrder(order);
		orderDetails.setProduct(product);

		OrderDetails savedOrderDetails = repo.save(orderDetails);
		return convertToDTO(savedOrderDetails);
	}

	@Override
	public OrderDetailsDTO updateOrderDetails(Long id, OrderDetailsDTO orderDetailsDTO) {
		Optional<OrderDetails> orderDetails = repo.findActiveOrderDetailsById(id);
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("order details not found or is deleted");
        }
		orderDetails.get().setQuantity(orderDetailsDTO.getQuantity());
		orderDetails.get().setTotal_amount(orderDetailsDTO.getTotalAmount());
	
		
//		Order order = orderRepo.findById(orderDetailsDTO.getOrderId()).orElseThrow(
//				() -> new OrderNotFoundException("Order not found with id " + orderDetailsDTO.getOrderId()));
//		orderDetails.get().setOrder(order);
//		
//		Products product = productRepo.findById(orderDetailsDTO.getProductId()).orElseThrow(
//				() -> new ProductNotFoundException("Product not found with id " + orderDetailsDTO.getProductId()));
//		orderDetails.get().setProduct(product);

		OrderDetails updatedOrderDetails = repo.save(orderDetails.get());
		return convertToDTO(updatedOrderDetails);
	}

	@Override
	public String deleteOrderDetails(Long id) {
		String msg = "Order deleted successfully";
		repo.findActiveOrderDetailsById(id).orElseThrow(() -> new OrderNotFoundException("Order does not exist"));

		repo.softDeleteOrderDetailsById(id);

		return msg;
	}

}
