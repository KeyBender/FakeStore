package com.cameron.fakestore.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cameron.fakestore.models.Item;
import com.cameron.fakestore.models.User;
import com.cameron.fakestore.services.ItemService;
import com.cameron.fakestore.services.UserService;

@RestController
public class ApiController {

	@Autowired
	ItemService itemService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/items")
	public void inputItems(@RequestBody Item item) {
		itemService.save(item);
	}
	
	@PostMapping("/api/user")
	public void inputUser(@Valid @RequestBody User user, BindingResult result) {
		userService.register(user, result);
	}
}
