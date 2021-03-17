package com.infosys.order.dto;

import java.util.Date;
import java.util.List;

import com.infosys.order.entity.OrderDetails;


public class OrderDetailsDTO {


	int orderId;

	int buyerId;
	
	double amount;

	Date date;

	String address;
	
	String status;
	
	List<ProductsOrderedDTO> productsOrdered;



		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public int getBuyerId() {
			return buyerId;
		}

		public void setBuyerId(int buyerId) {
			this.buyerId = buyerId;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public List<ProductsOrderedDTO> getProductsOrdered() {
			return productsOrdered;
		}

		public void setProductsOrdered(List<ProductsOrderedDTO> productsOrdered) {
			this.productsOrdered = productsOrdered;
		}

		public OrderDetails createEntity() {
			OrderDetails order= new OrderDetails();
			order.setOrderId(this.getOrderId());
			order.setBuyerId(this.getBuyerId());
			order.setAmount(this.getAmount());
			order.setDate(this.getDate());
			order.setAddress(this.getAddress());
			order.setStatus(this.getStatus());
			return order;
		}
		
		// Converts Entity into DTO
		public static OrderDetailsDTO valueOf(OrderDetails orderDetails) {
			OrderDetailsDTO ordersDTO = new OrderDetailsDTO();
			ordersDTO.setBuyerId(orderDetails.getBuyerId());
			ordersDTO.setOrderId(orderDetails.getOrderId());
			ordersDTO.setAddress(orderDetails.getAddress());
			ordersDTO.setDate(orderDetails.getDate());
			ordersDTO.setStatus(orderDetails.getStatus());
			ordersDTO.setAmount(orderDetails.getAmount());
			return ordersDTO;
		}
}
