package com.infosys.order.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CompositePK implements Serializable {
 
	 int orderId;
	 int prodId;
	
	public CompositePK() {}
	
	public CompositePK(int orderId,int prodId) {
		this.orderId= orderId;
		this.prodId= prodId;
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

	@Override
	public boolean equals(Object obj)
	{
		CompositePK compositePk=(CompositePK)obj;
		if(compositePk.getOrderId()==this.getOrderId()&&compositePk.getProdId()==this.getProdId())
		{
			return true;
		}
		return false;
	}
	
}
