package com.cameron.fakestore.controllers;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cameron.fakestore.models.Item;
import com.cameron.fakestore.services.ItemService;
import com.cameron.fakestore.services.UserService;

@Controller
public class ItemController {

	@Autowired
	ItemService itemService;
	
	@Autowired
	UserService userService;
	
	/*@PostMapping("/api/items")
	public void input(@RequestBody Item item) {
		itemService.save(item);
	}*/
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		if(userService.isLoggedIn(session)) {
			model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			
		}
		model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
		model.addAttribute("allItems", itemService.find());
		model.addAttribute("onSale", itemService.findByOnSale());
		
		return "item/index.jsp";
	}
	
	@GetMapping("/item/{categoryName}")
	public String categoryFilter(@PathVariable("categoryName") String category, Model model, HttpSession session) {
		
		if(userService.isLoggedIn(session)) {
			model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			
		}
		model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
		model.addAttribute("items", itemService.findByCategory(category));
		
		return "item/categoryFilter.jsp";
	}
	
	@GetMapping("/item/view/{itemId}")
	public String viewItem(@PathVariable("itemId") Long id, Model model, HttpSession session) {
		
		Item item = itemService.find(id);
		
		if(userService.isLoggedIn(session)) {
			model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			
		}
		model.addAttribute("item", item);
		model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
		model.addAttribute("categoryItems", itemService.findByCategory(item.getCategory()));
		
		return "item/viewItem.jsp";
	}
	
	@GetMapping("/item/search")
	public String searchItems(@RequestParam(value="name", required = true) String searchParam, Model model, HttpSession session) {
		
		if(userService.isLoggedIn(session)) {
			model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			
		}
		model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
		model.addAttribute("searchedItems", itemService.search(searchParam));
		model.addAttribute("searchedKeyword", searchParam);
		
		return "item/searchItems.jsp";
	}
}
