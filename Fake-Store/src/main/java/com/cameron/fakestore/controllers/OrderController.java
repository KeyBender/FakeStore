package com.cameron.fakestore.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cameron.fakestore.models.Item;
import com.cameron.fakestore.models.Order;
import com.cameron.fakestore.models.User;
import com.cameron.fakestore.services.ItemService;
import com.cameron.fakestore.services.OrderService;
import com.cameron.fakestore.services.UserService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	ItemService itemService;

	@Autowired
	UserService userService;

	@GetMapping("/cart/{itemId}")
	public String addToCart(@PathVariable("itemId") Long itemId, HttpSession session, Model model) {
		if (userService.isLoggedIn(session)) {

			List<Item> itemsInCart = (List<Item>) session.getAttribute("itemsInCart");
			if(itemsInCart == null) {
				itemsInCart = new ArrayList<>();
			}
			Item addedItem = itemService.find(itemId);

			itemsInCart.add(addedItem);
			session.setAttribute("itemsInCart", itemsInCart);

			return "redirect:/cart";
		}

		return "redirect:/signup";
	}

	@GetMapping("/cart")
	public String cart(HttpSession session, Model model) {
		
		if (userService.isLoggedIn(session)) {
			User loggedInUser = userService.find((Long) session.getAttribute("userId"));
			List<Item> itemsInCart = (List<Item>) session.getAttribute("itemsInCart");
			Double totalPrice = 0.0;

			if(itemsInCart != null) {
				if(itemsInCart.size()>0) {
					model.addAttribute("emptyCart", false);
					for (Item item : itemsInCart) {
						if (item.getOnSale()) {
							totalPrice = totalPrice + item.getSalePrice();
						}
						else {
							totalPrice = totalPrice + item.getPrice();							
						}
					}					
				}
				else {
					model.addAttribute("emptyCart", true);
				}
			}
			else {
				model.addAttribute("emptyCart", true);
			}
			
			Order order = new Order();
			order.setUser(loggedInUser);
			order.setItems(itemsInCart);
			order.setAddress(loggedInUser.getAddress());
			order.setTotalPrice(totalPrice);
			model.addAttribute("currentOrder", order);
			model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
			model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));
			return "order/cart.jsp";
		}

		return "redirect:/signup";
	}

	@GetMapping("/cart/remove/{itemId}")
	public String cartRemoveItem(@PathVariable("itemId") Long id, Model model, HttpSession session) {
		if (userService.isLoggedIn(session)) {
			List<Item> itemsInCart = (List<Item>) session.getAttribute("itemsInCart");

			for (Item item : itemsInCart) {
				if (item.getId() == id) {
					itemsInCart.remove(item);
				}
				return "redirect:/cart";
			}
		}
		return "redirect:/signup";
	}

	@GetMapping("/cart/checkout")
	public String checkout(HttpSession session, Model model) {
		if (userService.isLoggedIn(session)) {
			User loggedInUser = userService.find((Long) session.getAttribute("userId"));
			List<Item> itemsInCart = (List<Item>) session.getAttribute("itemsInCart");
			Double totalPrice = 0.0;

			for (Item item : itemsInCart) {
				if (item.getOnSale()) {
					totalPrice += item.getSalePrice();
				}
				else {
					totalPrice+=item.getPrice();
				}
			}

			Order order = new Order();
			order.setUser(loggedInUser);
			order.setItems(itemsInCart);
			order.setAddress(loggedInUser.getAddress());
			order.setTotalPrice(totalPrice);

			Order savedOrder = orderService.save(order);
			
			model.addAttribute("savedOrder", savedOrder);
			model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
			model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));
			return "order/checkout.jsp";
		}
		
		return "redirect:/signup"; 
	}
}
