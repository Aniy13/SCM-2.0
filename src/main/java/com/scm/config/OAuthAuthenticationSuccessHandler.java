package com.scm.config;

import java.awt.RenderingHints.Key;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.antlr.v4.runtime.atn.LookaheadEventInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.scm.entity.Provider;
import com.scm.entity.User;
import com.scm.helper.AppConstants;
import com.scm.repository.UserReop;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserReop userRepo;
	
    Logger logger =LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		logger.info("OAuthAuthenticationSuccessHandler");
//		response.sendRedirect("/home");
		//identify login is google or github(Provder)
		 OAuth2AuthenticationToken token= (OAuth2AuthenticationToken) authentication;
		 String id = token.getAuthorizedClientRegistrationId();
		 logger.info(id);
		 
		 
		 DefaultOAuth2User dUser =(DefaultOAuth2User) authentication.getPrincipal();
		 
		 dUser.getAttributes().forEach((key,value)->{
			 logger.info(key+" : "+value);
		 });
		 
		 User user = new User();
		 user.setUserId(UUID.randomUUID().toString());
		 user.setRoleList(List.of(AppConstants.USER));
		 user.setEmailVerified(true);
		 user.setEnabled(true);
		 
		  
		 
		 
		 if (id.equalsIgnoreCase("google")) {
			 //google atributes
			 user.setEmail(dUser.getAttribute("email").toString());
			 user.setProfile(dUser.getAttribute("picture").toString());
			 user.setName(dUser.getAttribute("name").toString());
			 user.setProviderUserId(dUser.getName());
			 user.setProvider(Provider.GOOGLE);
			 user.setAbout("This account is created using Google");
			 user.setPassword("Google_password");
			
		}else if (id.equalsIgnoreCase("github")) {
			// github  attributes
			String email=dUser.getAttribute("email")!=null?dUser.getAttribute("email").toString() :dUser.getAttribute("login").toString()+"@github.com";
			user.setEmail(email);
			String picture=dUser.getAttribute("avatar_url").toString();
			user.setProfile(picture);
			String name=dUser.getAttribute("login").toString();
			String providerId=dUser.getName();
			user.setName(name);
			user.setProviderUserId(providerId);
			user.setProvider(Provider.GITHUB);
			user.setAbout("This account is created using Github");
			user.setPassword("Github_password");
			
			
		}else {
			//other
			logger.info("OAuth2AythenticationSuccessHandler : Unknown Provider");
		}
   
		 
			User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
			
			if(user2==null) {
				userRepo.save(user);
				logger.info("User saved "+user.getEmail());
			}
			
		
		//data save
		
		// DefaultOAuth2User user= (DefaultOAuth2User)authentication.getPrincipal();
//		 logger.info(user.getName());
//		 user.getAttributes().forEach((key,value)->{
//			logger.info("{} -> {}",key,value);
//		 });
//		logger.info(user.getAuthorities().toString());
		
//		String email= user.getAttribute("email").toString();
//		String name= user.getAttribute("name").toString();
//		String picture= user.getAttribute("picture").toString();
//		
//		User user1 = new User();
//		user1.setEmail(email);
//		user1.setName(name);
//		user1.setProfile(picture);
//		user1.setPassword("password");
//		user1.setUserId(UUID.randomUUID().toString());
//		user1.setProvider(Provider.GOOGLE);
//		user1.setEnabled(true);
//		user1.setEmailVerified(true);
//		user1.setProviderUserId(user.getName());
//		user1.setRoleList(List.of(AppConstants.USER));
//		user1.setAbout("This account is created using Google");
//		
//		
//		User user2 = userRepo.findByEmail(email).orElse(null);
//		
//		if(user2==null) {
//			userRepo.save(user1);
//			logger.info("User saved "+email);
//		}
		
		
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
	}

	
}
