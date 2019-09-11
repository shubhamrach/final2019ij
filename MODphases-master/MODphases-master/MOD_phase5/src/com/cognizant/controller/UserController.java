package com.cognizant.controller;


import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.entity.User;

import com.cognizant.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User registerUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public 	User blockUser(@RequestBody User user) {
		return userService.save(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody User login) throws ServletException {

		String jwtToken = "";

		if (login.getUsername() == null || login.getPassword() == null) {
			throw new ServletException("Please fill in username and password");
		}

		String username = login.getUsername();
		String password = login.getPassword();

		User user = userService.findUser(username, password);

		if (user == null) {
			throw new ServletException("User email not found.");
		}

		String pwd = user.getPassword();

		if (!password.equals(pwd)) {
			throw new ServletException("Invalid login. Please check your name and password.");
		}

		jwtToken = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date(0))
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact();

		return jwtToken;
	}

	@RequestMapping(value = "/Username", method = RequestMethod.POST)
	public 	User finduser(@RequestBody 	User login) throws ServletException {

		if (login.getUsername() == null) {
			throw new ServletException("There is no such news analyst");
		}

		String username = login.getUsername();

		User user = userService.findByUsername(username);

		return user;
	}

	

	
}
