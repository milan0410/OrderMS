package com.infosys.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infosys.order.entity.OrderDetails;
import com.infosys.order.entity.ProductsOrdered;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {

	List<OrderDetails> findAllByBuyerId(int buyerId);
	Optional<OrderDetails> findByOrderId(int orderId);
	List<ProductsOrdered> findAllByOrderId(int orderId);
}

