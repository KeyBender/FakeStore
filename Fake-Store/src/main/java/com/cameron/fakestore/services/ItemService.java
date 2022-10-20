package com.cameron.fakestore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cameron.fakestore.models.Item;
import com.cameron.fakestore.respositories.ItemRepository;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepo;
	
	public Item find(Long id) {
		Optional<Item> foundItem = itemRepo.findById(id);
		
		if(foundItem.isPresent()) {
			return foundItem.get();
		}
		
		return null;
	}
	
	public List<Item> find(){
		return itemRepo.findAll();
	}
	
	public List<Item> findByCategory(String category){
		return itemRepo.findByCategory(category);
	}
	
	public List<Item> findByOnSale(){
		return itemRepo.findByOnSale(true);
	}
	
	public Item save(Item item) {
		return itemRepo.save(item);
	}
	
	public void delete(Long id) {
		itemRepo.deleteById(id);
	}
	
	public List<Item> search(String searchKey){
		List<Item> allItems = itemRepo.findAll();
		List<Item> searchResults = new ArrayList<>();
		
		for(Item item : allItems) {
			if(item.getTitle().toLowerCase().contains(searchKey.toLowerCase())) {
				searchResults.add(item);
			}
		}
		return searchResults;
	}
}
