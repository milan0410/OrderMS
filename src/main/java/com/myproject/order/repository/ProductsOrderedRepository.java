package com.myproject.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.order.entity.CompositePK;
import com.myproject.order.entity.ProductsOrdered;

public interface ProductsOrderedRepository extends JpaRepository<ProductsOrdered,CompositePK> {
List<ProductsOrdered> findAllByOrderId(int orderId);
}
