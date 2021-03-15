package com.infosys.order.dto;


import com.infosys.order.entity.ProductsOrdered;

public class ProductsOrderedDTO {
	
	int orderId;
	
	int prodId;
	
	int sellerId;
	
	int quantity;
	
	String status;
	
	double price;
	
	public ProductsOrderedDTO() {
		super();
	}
	
	public ProductsOrderedDTO(int orderId, int prodId, int sellerId, int quantity, String status, double price) {
		this();
		this.orderId = orderId;
		this.prodId =  prodId;
		this.sellerId = sellerId;
		this.quantity = quantity;
		this.status= status;
		this.price= price;
	}


	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

		// Converts Entity into DTO
		public static ProductsOrderedDTO valueOf(ProductsOrdered productsOrdered) {
			ProductsOrderedDTO productsDTO = new ProductsOrderedDTO();
			productsDTO.setOrderId(productsOrdered.getOrderId());
			productsDTO.setProdId(productsOrdered.getProdId());
			productsDTO.setSellerId(productsOrdered.getSellerId());
			productsDTO.setQuantity(productsOrdered.getQuantity());
			productsDTO.setStatus(productsOrdered.getStatus());
			productsDTO.setPrice(productsOrdered.getPrice());
			return productsDTO;
		}
	
	
}
