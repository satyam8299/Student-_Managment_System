package com.cs.sms.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cs.sms.model.Users;
import com.cs.sms.repo.UserRepo;

@Service
public class UserServiceImpl implements UserDetailsService {
	
	private UserRepo userRepo;

	public UserServiceImpl(UserRepo userRepo) {
		this.userRepo= userRepo;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = userRepo.findByUsername(username)
		.orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
		
		return User.withUsername(username)
				.password(users.getPassword())
				.disabled(!users.isActive())
				.build();	
		 }
	
	

}
