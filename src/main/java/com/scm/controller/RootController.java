package com.scm.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entity.User;
import com.scm.helper.HelperLogin;
import com.scm.service.UserService;

@ControllerAdvice
public class RootController {
	
	Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@Autowired
    private UserService userService;
	@ModelAttribute
	public void addLoggedInUserInfo(Model model, Authentication authentication) {
		if(authentication==null) {
			return;
		}
		String username = HelperLogin.getEmailOfLoggedInUser(authentication);
    	logger.info("User Logged in : "+ username);
    	User userByEmail = userService.getUserByEmail(username);
    	System.out.println(userByEmail);
    	System.out.println(userByEmail.getName());
    	System.out.println(userByEmail.getEmail());
    	model.addAttribute("loggedInuser", userByEmail);
	}

}
