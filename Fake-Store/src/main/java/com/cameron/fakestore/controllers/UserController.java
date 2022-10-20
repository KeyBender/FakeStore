package com.cameron.fakestore.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.cameron.fakestore.models.LoginUser;
import com.cameron.fakestore.models.User;
import com.cameron.fakestore.services.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/signup")
	public String signup(Model model, HttpSession session) {
		
		if (userService.isLoggedIn(session)) {
			return "redirect:/";
		}
		
		User user = new User();
		LoginUser loginUser = new LoginUser();
		
		model.addAttribute("newUser", user);
		model.addAttribute("loginUser", loginUser);

		return "user/signup.jsp";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginUser") LoginUser user, BindingResult result, HttpSession session,
			Model model) {

		User loginUser = userService.login(user, result);

		if (result.hasErrors()) {
			
			model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
			model.addAttribute("newUser", new User());
			
			return "user/signup.jsp";
		}

		session.setAttribute("userId", loginUser.getId());

		return "redirect:/";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User user, BindingResult result, Model model,
			HttpSession session) {

		User regUser = userService.register(user, result);

		if (result.hasErrors()) {
			model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
			model.addAttribute("loginUser", new LoginUser());
			
			return "user/signup.jsp";
		}

		session.setAttribute("userId", regUser.getId());
		
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}

	@GetMapping("/user/{userId}")
	public String editUser(@PathVariable("userId") Long editId, Model model, HttpSession session) {
		
		if (userService.isLoggedIn(session)) {
			if (editId == (Long) session.getAttribute("userId")) {
				
				User foundUser = userService.find(editId);
				
				model.addAttribute("isAdmin", userService.isAdmin(session));
				model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
				model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));			
				model.addAttribute("user", foundUser);
				System.out.print(userService.isAdmin(session));
				return "user/editUser.jsp";
			}
		}
		return "redirect:/signup";
	}
	
	@PutMapping("/user/{id}")
	public String editUserProcess(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		if(userService.isLoggedIn(session)) {
			
			if(result.hasErrors()) {
				model.addAttribute("isLoggedIn", userService.isLoggedIn(session));
				model.addAttribute("navBarUserId", (Long) session.getAttribute("userId"));
				return "user/editUser.jsp";
			}
			
			//get password and admin status from database so saving wont 'null' those fields
			User infoUser = userService.find((Long) session.getAttribute("userId"));
			
			user.setPassword(infoUser.getPassword());
			
			userService.save(user);
			return "redirect:/";
		}
		return "redirect:/signup";
		
	}

}
