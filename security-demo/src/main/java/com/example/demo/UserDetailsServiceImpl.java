package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		
		User user = userRepository.findByUsername(username); //we define this method in userRepository
		if(user == null) throw new UsernameNotFoundException(username); //if no such user is found
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); //authority granted to an authentication object
		for(Role role: user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
