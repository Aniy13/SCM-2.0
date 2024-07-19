package com.scm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entity.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
	@Autowired
	private UserService userService;

    @GetMapping("/test")
	public String test(Model model) {
    	model.addAttribute("name","Aniket");
		return "test";
	}
    @RequestMapping("/about")
	public String about(){
		return "about";
	}
    @RequestMapping("/home")
	public String home(){
		return "home";
	}
    @RequestMapping("/")
   	public String homeSlash(){
   		return "redirect:/home";
   	}
	@RequestMapping("/services")
	public String services(){
		return "services";
	}
	@RequestMapping("/contact")
	public String contacts(){
		return "contact";
	}
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	@RequestMapping("/signup")
	public String register(Model model){
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		// userForm.setName("Aniket");
		// userForm.setEmail("aghgfd");
		// userForm.setPhone("9876");
		// userForm.setAbout("aasdfsbfdn");
		// userForm.setPassword("iuio");
		return "register";
	}
	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm ,BindingResult result,HttpSession session) {
		System.out.println("processing register");
		//fetch form data , validata save into repo
		// User user=User.builder()
		// .name(userForm.getName())
		// 	.email(userForm.getEmail())
		// 	.password(userForm.getPassword())
		// 	.phone(userForm.getPhone())
		// .build();
		System.out.println(userForm);

		if(result.hasErrors()){
			return "register";
		}

		User user=new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhone(userForm.getPhone());
		User savedUser= userService.saveUser(user);

		  // add message
		  Message message = new Message();
		  message.setContents("Registration successful!");
		  message.setType(MessageType.green); 
		session.setAttribute("message",message);
		System.out.println(userForm);
		return "redirect:/signup";
	}
	
	
	
  

}
