package com.scm.service.impl;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entity.User;
import com.scm.helper.AppConstants;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repository.UserReop;
import com.scm.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserReop userReop;
	@Autowired
	private PasswordEncoder encoder;
	private Logger logger=org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
	public User saveUser(User user) {
		String userId= UUID.randomUUID().toString();
		user.setUserId(userId);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRoleList(List.of(AppConstants.USER));
		logger.info(user.getProvider().toString());
		return userReop.save(user);
	}

	@Override
	public Optional<User> getUserById(String id) {
	  
	  return userReop.findById(id);
	}

	@Override
	public Optional<User> updateUser(User user) {
		User user2=userReop.findById(user.getUserId()).orElseThrow(()-> new  ResourceNotFoundException("User not fount"));
		 user2.setName(user.getName());
		 user2.setEmail(user.getEmail());
		 user2.setPassword(user.getPassword());
		 user2.setAbout(user.getAbout());
		 user2.setPhone(user.getPhone());
		 user2.setProfile(user.getProfile());
		 user2.setEnabled(user.getEnabled());
		 user2.setEmailVerified(user.getEmailVerified());
		 user2.setPhoneVerified(user.getPhoneVerified());
		 user2.setProviderUserId(user.getProviderUserId());
		 user2.setProvider(user.getProvider());
		 
		 User save = userReop.save(user2);
		 return Optional.ofNullable(save);
	}

	@Override
	public void deleteUser(String id) {
		User user2=userReop.findById(id).orElseThrow(()-> new  ResourceNotFoundException("User not fount"));
		userReop.delete(user2);
	}

	@Override
	public boolean isUserExist(String id) {
		User user2=userReop.findById(id).orElse(null);
		return user2!=null ? true : false;
	}

	@Override
	public boolean isuserExistByEmail(String email) {
	User user = userReop.findByEmail(email).orElse(null);
	return user!=null ? true : false;
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>) userReop.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userReop.findByEmail(email).orElse(null);
	}
		
	
}
