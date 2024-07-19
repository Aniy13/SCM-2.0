package com.scm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.entity.User;
import com.scm.repository.UserReop;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
	private UserReop userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
		
	}
       
}
