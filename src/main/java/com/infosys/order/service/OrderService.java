package com.infosys.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.order.dto.OrderDetailsDTO;
import com.infosys.order.dto.ProductsOrderedDTO;
import com.infosys.order.entity.OrderDetails;
import com.infosys.order.entity.ProductsOrdered;
import com.infosys.order.repository.OrderRepository;


@Service
public class OrderService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderRepository orderDetailsRepo;

	// Fetches call details of a specific buyer
	public List<OrderDetailsDTO> getBuyerOrderDetails(@PathVariable int buyerId) {

		logger.info("Orderdetails request for buyer {}", buyerId);

		List<OrderDetails> orderDetails = orderDetailsRepo.findAllByBuyerId(buyerId);
		List<OrderDetailsDTO> ordersDTO = new ArrayList<>();

		for (OrderDetails order : orderDetails) {
			ordersDTO.add(OrderDetailsDTO.valueOf(order));
		}
		logger.info("Orderdetails for buyer : {}", orderDetails);

		return ordersDTO;
	}
	
	  public void placeOrder(int buyerId,int orderId,OrderDetailsDTO orderDTO)
	    {
	    	orderDTO.setBuyerId(buyerId);
	    	orderDTO.setOrderId(orderId);
	    	    	OrderDetails order= orderDTO.createOrder();
	    	    	orderDetailsRepo.save(order);
	    }
	   
	    public void updateOrderStatus(int orderId)
	    {
	    	Optional<OrderDetails> order = orderDetailsRepo.findByOrderId(orderId);
	    	OrderDetailsDTO orderDTO= OrderDetailsDTO.valueOf(order.get());
	    	orderDTO.updateStatus(order.get());
	    }  
	  
	  public OrderDetailsDTO getSpecificOrderDetails(@PathVariable int orderId) {
		  OrderDetailsDTO orderDTO=null;
			logger.info("Orderdetails request for order {}", orderId);

			Optional<OrderDetails> optOrder= orderDetailsRepo.findByOrderId(orderId);
		
			if(optOrder.isPresent())
			{	OrderDetails order  = optOrder.get();
			orderDTO= OrderDetailsDTO.valueOf(order);}

			return orderDTO;
		}
	
	  public List<ProductsOrderedDTO> getSpecificOrderProducts(@PathVariable int orderId) {

			logger.info("Orderdetails request for buyer {}", orderId);

			List<ProductsOrdered> productsOrdered = orderDetailsRepo.findAllByOrderId(orderId);
			List<ProductsOrderedDTO> productsOrderedDTO = new ArrayList<>();

			for (ProductsOrdered product : productsOrdered) {
				productsOrderedDTO.add(ProductsOrderedDTO.valueOf(product));
			}
			logger.info("Productdetails for order : {}", productsOrderedDTO);

			return productsOrderedDTO;
		}
	  public  boolean reOrder(int orderId, OrderDetailsDTO orderDTO) {
		  Optional<OrderDetails> optOrder= orderDetailsRepo.findByOrderId(orderId);
		  if(optOrder.isPresent() && optOrder.get().getDate().before(orderDTO.getDate()) )
		  {	
			OrderDetails order= orderDTO.createOrder();
	    	orderDetailsRepo.save(order);
	    	return true;
		  }
		  else 
			  return false;
	  }
	  
	  

    
}

