package org.ecom.controller;

import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;
import org.ecom.dto.OrderDTO;
import org.ecom.exception.OrderNotFoundException;
import org.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/order")
public class OrderController {
	@Autowired
	private OrderService service;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {

		try {
			service.createOrder(orderDTO);
			Map<String, String> response = new HashMap<>();
			response.put("message", "Order created successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("message", "Order could not be created");
			return ResponseEntity.status(500).body(response);
		}
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
		Page<OrderDTO> orderDTOs = service.findAllOrders(pageable);
		return ResponseEntity.ok(orderDTOs);
	}

	@GetMapping("/{id}/details")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
		try {
			OrderDTO orderDTO = service.findOrderById(id);
			return ResponseEntity.ok(orderDTO);
		} catch (OrderNotFoundException e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("message", "Order does not exist");
			return ResponseEntity.status(404).body(response);
		}
	}

	@PutMapping("/{id}/update")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<?> updateOrder(@PathVariable("id") Long id,@Valid @RequestBody OrderDTO orderDTO) {
		try {
			service.updateOrder(id, orderDTO);
			return ResponseEntity.ok("Order updated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("message", "Order could not be updated");
			return ResponseEntity.status(500).body(response);
		}
	}

	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
		try {
			String msg = service.deleteOrder(id);
			return ResponseEntity.ok(msg);
		} catch (OrderNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(404).body("Order does not exist");
		}
	}

}
