package com.cameron.fakestore.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.cameron.fakestore.models.Item;
import com.cameron.fakestore.models.Order;
import com.cameron.fakestore.models.User;
import com.cameron.fakestore.services.ItemService;
import com.cameron.fakestore.services.OrderService;
import com.cameron.fakestore.services.UserService;

@Controller
public class AdminController {
	@Autowired
	UserService userService;

	@Autowired
	ItemService itemService;

	@Autowired
	OrderService orderService;

	@GetMapping("/admin")
	public String admin(Model model, HttpSession session) {
			if (userService.isAdmin(session)) {
				model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			
				model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
				model.addAttribute("allUsers", userService.find());
				model.addAttribute("unShippedOrders", orderService.findUnshippedOrders());
				model.addAttribute("shippedOrders", orderService.findShippedOrders());
				model.addAttribute("allItems", itemService.find());

				return "admin/adminDashboard.jsp";
			}
			return "redirect:/error";
	}

	@GetMapping("/admin/user/{userId}")
	public String adminEditUser(@PathVariable("userId") Long editId, Model model, HttpSession session) {
		
			if (userService.isAdmin(session)) {
				User foundUser = userService.find(editId);
				model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			
				model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
				model.addAttribute("user", foundUser);
				
				return "admin/adminEditUser.jsp";
			}
			return "redirect:/error";
	}

	@PutMapping("/admin/user/{id}")
	public String adminEditUserProcess(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			HttpSession session, @PathVariable("id") Long id) {

			if (userService.isAdmin(session)) {
				
				if (result.hasErrors()) {
					model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
					model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			

					return "admin/adminEditUser.jsp";
				}
				
				User editUser = userService.find(id);
				
				
				user.setPassword(editUser.getPassword());
				
				userService.save(user);
				return "redirect:/admin";
			}
			return "redirect:/error";
	}


	@GetMapping("/admin/order/{orderId}")
	public String adminEditOrder(@PathVariable("orderId") Long id, Model model, HttpSession session) {
		
			if (userService.isAdmin(session)) {
				model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
				model.addAttribute("order", orderService.find(id));
				model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			

				return "admin/adminEditOrder.jsp";
			}
			return "redirect:/error";
	}

	@PutMapping("/admin/order/{id}")
	public String adminEditOrderProcess(@Valid @ModelAttribute("order") Order order, BindingResult result,
			HttpSession session, Model model, @PathVariable("id") Long id) {
			if (userService.isAdmin(session)) {
				if (result.hasErrors()) {
					model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
					model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			

					return "admin/adminEditOrder.jsp";
				}
				Order foundOrder = orderService.find(id);
				
				foundOrder.setIsShipped(order.getIsShipped());
				
				orderService.save(foundOrder);
				return "redirect:/admin";
			}
			return "redirect:/error";
	}
	
	@GetMapping("/admin/item/{itemId}")
	public String adminEditItem(@PathVariable("itemId") Long id, Model model, HttpSession session) {
			if (userService.isAdmin(session)) {
				model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
				model.addAttribute("item", itemService.find(id));
				model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			

				return "admin/adminEditItem.jsp";
			}
			return "redirect:/error";
	}
	
	@PutMapping("/admin/item/{id}")
	public String adminEditItemProcess(@Valid @ModelAttribute("item") Item item, BindingResult result, Model model, HttpSession session, @PathVariable("id") Long id) {
		if(userService.isAdmin(session)) {
			if(result.hasErrors()) {
				model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
				model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			

				return "admin/adminEditItem.jsp";
			}
			Item foundItem = itemService.find(id);
			item.setRating(foundItem.getRating());
			item.setRatingCount(foundItem.getRatingCount());
			item.setOrders(foundItem.getOrders());
			
			itemService.save(item);
			return "redirect:/admin";
		}
		return "redirect:/error";
	}
	
	
	@GetMapping("/admin/item")
	public String adminNewItem(Model model, HttpSession session) {
		if(userService.isAdmin(session)) {
			model.addAttribute("item", new Item());
			model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
			model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			

			return "admin/adminNewItem.jsp";
		}
		return "redirect:/error";
	}
	
	@PostMapping("/admin/item")
	public String adminNewItemProcess(@Valid @ModelAttribute("item") Item item, BindingResult result, HttpSession session, Model model) {
		if(userService.isAdmin(session)) {
			if(result.hasErrors()) {
				model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
				model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			

				return "admin/adminNewItem.jsp";
			}
			itemService.save(item);
			return "redirect:/admin";
		}
		return "redirect:/error";
	}
	
	@GetMapping("/aboutus")
	public String aboutus() {
		return "admin/aboutus.jsp";
	}
}
