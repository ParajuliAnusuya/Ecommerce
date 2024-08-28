package org.ecom.controller;

import java.util.HashMap;
import java.util.Map;
import org.ecom.dto.OrderDetailsDTO;
import org.ecom.exception.OrderDetailsNotFoundException;
import org.ecom.service.impl.OrderDetailsImpl;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/orderDetails")
public class OrderDetailsController {

	@Autowired
	private OrderDetailsImpl service;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> createOrderDetails(@Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {
		try {
			service.createOrderDetails(orderDetailsDTO);
			Map<String, String> response = new HashMap<>();
			response.put("message", "Order details created successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("message", "Order details could not be created");
			return ResponseEntity.status(500).body(response);
		}
	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<Page<OrderDetailsDTO>> getAllOrderDetails(Pageable pageable) {
		Page<OrderDetailsDTO> orderDetailsDTOs = service.findAllOrderDetails(pageable);
		return ResponseEntity.ok(orderDetailsDTOs);
	}

	@GetMapping("/{id}/details")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<?> getOrderDetailsById(@PathVariable("id") Long id) {
		try {
			OrderDetailsDTO orderDetailsDTO = service.findOrderDetailsById(id);
			return ResponseEntity.ok(orderDetailsDTO);
		} catch (OrderDetailsNotFoundException e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("message", "Order details not found");
			return ResponseEntity.status(404).body(response);
		}
	}


    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<?> updateOrderDetails(@PathVariable("id") Long id, @Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {
    	System.out.println(orderDetailsDTO);
        try {
            service.updateOrderDetails(id, orderDetailsDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Order details updated successfully");
            return ResponseEntity.ok(response);
        } catch (OrderDetailsNotFoundException e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Order details not found");
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Order details could not be updated");
            return ResponseEntity.status(500).body(response);
        }
    }

	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	public ResponseEntity<String> deleteOrderDetails(@PathVariable("id") Long id) {
		try {
			String msg = service.deleteOrderDetails(id);
			return ResponseEntity.ok(msg);
		} catch (OrderDetailsNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(404).body("Order details not found");
		}
	}
}
