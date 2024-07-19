package com.scm.service;

import java.util.Optional;

import com.scm.entity.User;


public interface UserService {
	User saveUser(User user);
	Optional<User> getUserById(String id);
	Optional<User> updateUser(User user);
	void deleteUser(String id);
	boolean isUserExist(String id);
	boolean isuserExistByEmail(String email);
	java.util.List<User> getAllUsers();
	User getUserByEmail(String email);

}
