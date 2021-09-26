package com.myproject.order.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CompositePK implements Serializable {
 
	 private int orderId;
	 private int prodId;
	

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

	@Override
	public boolean equals(Object obj)
	{   if(obj==null)
		return false;
	    if(obj.getClass()!=this.getClass())
	    	return false;
		CompositePK compositePk=(CompositePK)obj;
		if(compositePk.getOrderId()==this.getOrderId()&&compositePk.getProdId()==this.getProdId())
		{
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.orderId+this.prodId;
	}
}
