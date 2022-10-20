package com.cameron.fakestore.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cameron.fakestore.models.LoginUser;
import com.cameron.fakestore.models.Order;
import com.cameron.fakestore.models.User;
import com.cameron.fakestore.respositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	public Boolean isLoggedIn(HttpSession session) {
		if(session.getAttribute("userId") != null) {
			return true;
		}
		return false;
	}

	public User register(User newUser, BindingResult result) {
		Optional<User> foundUser = userRepo.findByEmail(newUser.getEmail());

		if (foundUser.isPresent()) {
			result.rejectValue("email", "inUse", "Email already in use");
		}
		if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
			result.rejectValue("password", "Match", "Passwords must match");
		}

		if (result.hasErrors()) {
			return null;
		}

		String hashedPw = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
		newUser.setPassword(hashedPw);
		return userRepo.save(newUser);
	}

	public User login(LoginUser login, BindingResult result) {
		Optional<User> foundUser = userRepo.findByEmail(login.getEmail());

		if (!foundUser.isPresent()) {
			result.rejectValue("email", "Unique", "The email is not registered");
			return null;
		}

		User user = foundUser.get();

		if (!BCrypt.checkpw(login.getPassword(), user.getPassword())) {
			result.rejectValue("password", "incorrect", "Incorrect password");
		}

		if (result.hasErrors()) {
			return null;
		}

		return user;
	}

	public Boolean isAdmin(HttpSession session) { // pass id from logged in user (session)
		Long id = (Long) session.getAttribute("userId");
		Optional<User> foundUser = userRepo.findById(id);

		if (foundUser.isPresent()) {
			return foundUser.get().getIsAdmin();
		}

		return false;
	}

	public User find(Long id) {
		Optional<User> foundUser = userRepo.findById(id);

		if (foundUser.isPresent()) {
			return foundUser.get();
		}

		return null;
	}
	
	public List<User> find(){
		return userRepo.findAll();
	}
	
	public List<Order> findOrders(Long id){
		Optional<User> foundUser = userRepo.findById(id);
		
		if(foundUser.isPresent()) {
			return foundUser.get().getOrders();
		}
		return null;
	}

	public void delete(Long id) { // should only be accessed from admin users
		userRepo.deleteById(id);
	}

	public User save(User user) { // should only be accessed from admin users
		return userRepo.save(user);
	}
}
