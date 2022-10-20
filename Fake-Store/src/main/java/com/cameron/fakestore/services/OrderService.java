package com.cameron.fakestore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cameron.fakestore.models.Order;
import com.cameron.fakestore.respositories.OrderRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepo;
	
	public List<Order> find(){
		return orderRepo.findAll();
	}
	
	public Order find(Long id) {
		Optional<Order> foundOrder = orderRepo.findById(id);
		
		if(foundOrder.isPresent()) {
			return foundOrder.get();
		}
		
		return null;
	}
	
	public Order save(Order order) {
		return orderRepo.save(order);
	}
	
	public void delete(Long id) {
		orderRepo.deleteById(id);
	}
	
	public List<Order> findUnshippedOrders(){
		List<Order> allOrders = this.find();
		List<Order> filteredOrders = new ArrayList<>();
		
		for(Order order : allOrders) {
			if(!order.getIsShipped()) {
				filteredOrders.add(order);
			}
		}
		
		return filteredOrders;
	}
	
	public List<Order> findShippedOrders(){
		List<Order> allOrders = this.find();
		List<Order> filteredOrders = new ArrayList<>();
		
		for(Order order : allOrders) {
			if(order.getIsShipped()) {
				filteredOrders.add(order);
			}
		}
		
		return filteredOrders;
	}
}
