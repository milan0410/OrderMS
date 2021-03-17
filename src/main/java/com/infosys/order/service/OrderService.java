package com.infosys.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.order.dto.OrderDetailsDTO;
import com.infosys.order.dto.ProductsOrderedDTO;
import com.infosys.order.entity.CompositePK;
import com.infosys.order.entity.OrderDetails;
import com.infosys.order.entity.ProductsOrdered;
import com.infosys.order.repository.OrderDetailsRepository;
import com.infosys.order.repository.ProductsOrderedRepository;


@Service
public class OrderService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderDetailsRepository orderDetailsRepo;
	@Autowired
	ProductsOrderedRepository productsOrderedRepo;

	// Fetches call details of a specific buyer
	public List<OrderDetailsDTO> getBuyerOrderDetails(@PathVariable int buyerId) throws Exception {

		logger.info("Orderdetails request for buyer {}", buyerId);

		List<OrderDetails> orderDetails = orderDetailsRepo.findAllByBuyerId(buyerId);
		List<OrderDetailsDTO> ordersDTO = new ArrayList<>();

		for (OrderDetails order : orderDetails) {
			ordersDTO.add(getSpecificOrderDetails(order.getOrderId()));
		}
		logger.info("Orderdetails for buyer : {}", orderDetails);

		return ordersDTO;
	}
	
	  public void placeOrder(OrderDetailsDTO orderDTO,List<ProductsOrderedDTO> products,int rewardPoints) throws Exception
	    {
	    	Date date=new Date();
	    	orderDTO.setDate(date);
	    	double amount=0.0;
	    	for(ProductsOrderedDTO product:products)
	    	{
	    		amount=amount+(product.getPrice()*product.getQuantity());
	    	}
	    	amount=amount-rewardPoints/4.0;
	    	orderDTO.setAmount(amount);
	    	orderDTO.setStatus("ORDER PLACED");
	    	OrderDetails order=orderDTO.createEntity();
	    	orderDetailsRepo.save(order);
	    	for(ProductsOrderedDTO product:products)
	    	{  	product.setOrderId(order.getOrderId());
	    	    product.setStatus(order.getStatus());
	    	    ProductsOrdered productOrdered=product.createEntity();
	    	    productsOrderedRepo.save(productOrdered);
	    	    
	    	}
	  
	    	
	    }
	   
	    public void updateOrderStatus(Integer orderId,Integer prodId,String status) throws Exception
	    {   CompositePK pk=new CompositePK();
	         pk.setOrderId(orderId);
	         pk.setProdId(prodId);
	    	Optional<ProductsOrdered> product = productsOrderedRepo.findById(pk);
	    	if(product.isPresent()==true)
	    	{
	    	product.get().setStatus(status);
	    	productsOrderedRepo.save(product.get());
	    	}
	    	else
	    		throw new Exception("Not found!!");
	    	
	    }  
	  
	  public OrderDetailsDTO getSpecificOrderDetails(@PathVariable int orderId) throws Exception {
		  OrderDetailsDTO orderDTO=null;
			logger.info("Orderdetails request for order {}", orderId);

			Optional<OrderDetails> optOrder= orderDetailsRepo.findById(orderId);
		
			if(optOrder.isPresent())
			{	OrderDetails order  = optOrder.get();
			orderDTO= OrderDetailsDTO.valueOf(order);
			List<ProductsOrdered> productsOrdered=productsOrderedRepo.findAllByOrderId(orderDTO.getOrderId());
			List<ProductsOrderedDTO> productsOrderedDTO = new ArrayList<>();
			for(ProductsOrdered product:productsOrdered)
			{
				productsOrderedDTO.add(ProductsOrderedDTO.valueOf(product));
			}
			orderDTO.setProductsOrdered(productsOrderedDTO);
			}

			return orderDTO;
		}
	
	  public OrderDetailsDTO reOrder(int orderId, int buyerId,int rewardPoints) throws Exception {
		  List<OrderDetailsDTO> buyerOrders=getBuyerOrderDetails(buyerId);
		  if(buyerOrders.isEmpty()!=true)
		  {
			  OrderDetailsDTO order=getSpecificOrderDetails(orderId);
			  placeOrder(order,order.getProductsOrdered(),rewardPoints);
			  return order;
		  }
		  else
			  throw new Exception("ReOrder Unsuccessful!");

	  }
	  
	  

    
}

