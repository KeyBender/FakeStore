package com.cameron.fakestore.respositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cameron.fakestore.models.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

	List<Item> findAll();
	List<Item> findByCategory(String category);
	List<Item> findByOnSale(Boolean onSale);
}
