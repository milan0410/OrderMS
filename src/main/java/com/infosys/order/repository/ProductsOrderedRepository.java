package com.infosys.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.order.entity.CompositePK;
import com.infosys.order.entity.ProductsOrdered;

public interface ProductsOrderedRepository extends JpaRepository<ProductsOrdered,CompositePK> {
List<ProductsOrdered> findAllByOrderId(int orderId);
}
