package com.example.demo;

public interface UserService {
	void save(User user);
	
	User findByUsername(String username);
}
