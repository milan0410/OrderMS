package com.myproject.order.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.myproject.order.dto.OrderDetailsDTO;
import com.myproject.order.dto.ProductsDTO;
import com.myproject.order.dto.ProductsOrderedDTO;
import com.myproject.order.service.OrderService;


@RestController
@CrossOrigin
@RequestMapping(value="/api/orders")
public class OrderController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderService orderService;
	
	@Value("${product.uri}")
	String productUri;
	@Value("${user.uri}")
	String userUri;

	// Fetches call details of a specific customer
	@GetMapping(value = "/{buyerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrderDetailsDTO> getBuyerOrderDetails(@PathVariable int buyerId) {
      try
      {
		logger.info("Orderdetails request for buyer {}", buyerId);

		return orderService.getBuyerOrderDetails(buyerId);
      }catch(Exception e)
      {
    	  throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
      }
	}
	
	@GetMapping(value = "/specificOrder/{orderId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderDetailsDTO getSpecificOrderDetails(@PathVariable int orderId) {
      try
      {
		logger.info("Orderdetails request for an order {}", orderId);
		OrderDetailsDTO orderDTO= orderService.getSpecificOrderDetails(orderId);
		return orderDTO;
      }catch(Exception e)
      {
    	  throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
      }
	}

	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/placeOrder" ,  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> placeOrder(@RequestBody OrderDetailsDTO orderDTO) {
		try
		{
		logger.info("Create an order {}",orderDTO);
		int rewardPoints=new RestTemplate().getForObject(userUri+"/rewardPoints/"+orderDTO.getBuyerId(),Integer.class);
		
		List<Integer> productList=new RestTemplate().getForObject(userUri+"/cart/product/"+orderDTO.getBuyerId(),List.class);
		
		List<ProductsOrderedDTO> products=new ArrayList<>();
	
		for(Integer productId:productList)
		{ 
		  ProductsDTO product=new RestTemplate().getForObject(productUri+"Id/"+productId,ProductsDTO.class);
		  
		  Integer quantity=new RestTemplate().getForObject(userUri+"/cart/product/quantity/"+orderDTO.getBuyerId()+"/"+productId, Integer.class);
		  
		  if(product!=null)
		 {
		  ProductsOrderedDTO productOrdered=new ProductsOrderedDTO();
		  productOrdered.setProdId(product.getProdId());
          productOrdered.setPrice(product.getPrice());
          productOrdered.setQuantity(quantity);
          productOrdered.setSellerId(product.getSellerId());
		  products.add(productOrdered);
		 }
		}
		orderDTO.setProductsOrdered(products);
		
		orderService.placeOrder(orderDTO,products,rewardPoints);
		
		new RestTemplate().postForObject(userUri+"user/orderUpdate",orderDTO,Boolean.class);
	
		return new ResponseEntity<String>("Order Placed Successfully!!",HttpStatus.OK);
		}catch(Exception e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/update/status")
	public  ResponseEntity<String> updateStatus(@RequestBody ProductsOrderedDTO product)
	{   try {
		Integer orderId=product.getOrderId();
		Integer prodId=product.getProdId();
		String status=product.getStatus();
		orderService.updateOrderStatus(orderId,prodId,status);
		return new ResponseEntity<String>("Status updated successfully!",HttpStatus.OK);
	     }catch(Exception e)
	{
	    	 return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	} 
	
	@PostMapping(value = "/reOrder/{buyerId}/{orderId}")
	public ResponseEntity<String> reOrder(@PathVariable int buyerId,@PathVariable int orderId) {
		try
		{ int rewardPoints=new RestTemplate().getForObject(userUri+"/rewardPoints/"+buyerId,Integer.class);
		 
		 OrderDetailsDTO orderDTO=orderService.reOrder(orderId, buyerId,rewardPoints);
		new RestTemplate().postForObject(userUri+"user/orderUpdate",orderDTO,Boolean.class);
		   return new ResponseEntity<String>("Reorder is successful!",HttpStatus.OK);

	}catch(Exception e)
		{
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}

