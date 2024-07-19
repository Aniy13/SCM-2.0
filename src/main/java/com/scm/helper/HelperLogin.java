package com.scm.helper;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class HelperLogin {
   public static String getEmailOfLoggedInUser( Authentication authentication) {
	   
	   
	 
	  
	  
	   
	   if(authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
		   OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		   String registrationId = token.getAuthorizedClientRegistrationId();
		   
		   OAuth2User user=(OAuth2User)authentication.getPrincipal();
		   String email ="";
		 //LoginType is Google
		   if(registrationId.equalsIgnoreCase("google")) {
			   System.out.println("Login with Google");
			    email = user.getAttribute("email");
			   
		   }else if(registrationId.equalsIgnoreCase("github")){
			   //LoginType is Github
			   System.out.println("Login with Github");
			
			   email= user.getAttribute("email")!=null?user.getAttribute("email").toString():user.getAttribute("login").toString()+"@github.com";
		       
		   }
		   
		   return email;
	   }else {
		 //if loginType is self
		System.out.println("Self-Login");
		return authentication.getName();
	}
	   
	   
		 
   }
}
