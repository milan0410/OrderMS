package com.infosys.order.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.order.dto.OrderDetailsDTO;
import com.infosys.order.dto.ProductsOrderedDTO;
import com.infosys.order.service.OrderService;


@RestController
@CrossOrigin
public class OrderController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderService orderService;

	// Fetches call details of a specific customer
	@GetMapping(value = "/buyer/{buyerId}/orders",  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrderDetailsDTO> getBuyerOrderDetails(@PathVariable int buyerId) {

		logger.info("Orderdetails request for buyer {}", buyerId);

		return orderService.getBuyerOrderDetails(buyerId);
	}
	
	@GetMapping(value = "/buyer/{buyerId}/orders/{orderId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderDetailsDTO getSpecificOrderDetails(@PathVariable int orderId) {

		logger.info("Orderdetails request for an order {}", orderId);
		OrderDetailsDTO orderDTO= orderService.getSpecificOrderDetails(orderId);
		List<ProductsOrderedDTO> products= orderService.getSpecificOrderProducts(orderId);
		orderDTO.setProductsOrdered(products);
		return orderDTO ;
	}

	@PostMapping(value = "/{buyerId}/order/{orderId}" ,  consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createOrder(@PathVariable int buyerId,@PathVariable int orderId, @RequestBody OrderDetailsDTO orderDTO) {
		logger.info("Create an order {}", buyerId,orderId,orderDTO);
		orderService.placeOrder(buyerId,orderId,orderDTO);
	}
	
	@PutMapping(value="/update/{orderId}/status")
	public  void updateStatus(@PathVariable int orderId)
	{
		logger.info("Update status of an order {}", orderId);
		orderService.updateOrderStatus(orderId);
		
	} 
	@PostMapping(value = "/orders/reOrders/{orderId}/{orderId2}")
	public boolean reOrder(@PathVariable int orderId,@RequestBody OrderDetailsDTO orderDTO) {
		if(orderService.reOrder(orderId, orderDTO))
		{
			return true;
		}
		else 
			return false;
	}
	
	
}

