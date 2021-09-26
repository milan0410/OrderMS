package com.myproject.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.order.entity.OrderDetails;
import com.myproject.order.entity.ProductsOrdered;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

	List<OrderDetails> findAllByBuyerId(int buyerId);
	List<ProductsOrdered> findAllByOrderId(int orderId);
}

