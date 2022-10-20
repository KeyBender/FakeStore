package com.cameron.fakestore.respositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cameron.fakestore.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findAll();
	List<Order> findByUserId(Long id);
}
