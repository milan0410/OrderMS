package com.infosys.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infosys.order.entity.OrderDetails;
import com.infosys.order.entity.ProductsOrdered;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

	List<OrderDetails> findAllByBuyerId(int buyerId);
	List<ProductsOrdered> findAllByOrderId(int orderId);
}

