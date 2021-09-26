package com.myproject.order.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@IdClass(CompositePK.class)
@Table(name = "productsordered")
public class ProductsOrdered implements Serializable{
  
	@Id
	@Column(name="ORDERID")
	int orderId;
	
	@Id 
	@Column(name="PRODID")
	int prodId;
	
	@Column(name="SELLERID",nullable = false)
	int sellerId;
	
	@Column(name="QUANTITY",nullable = false)
	int quantity;
	
	@Column(name="STATUS",nullable = false, length = 60)
	String status;
	
	@Column(nullable = false, length = 10)
	double price;
	
	
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
	
	
}