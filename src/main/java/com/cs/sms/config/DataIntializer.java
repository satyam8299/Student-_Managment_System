package com.cs.sms.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cs.sms.model.Users;
import com.cs.sms.repo.UserRepo;

@Configuration
public class DataIntializer {
	@Bean
  CommandLineRunner loadSampleData(UserRepo userrepo, PasswordEncoder passwordEncoder) {
		 return args ->{
			 if(!userrepo.existsByUsername("Admin")) {
			 Users users = new Users();
			 users.setUsername("Admin");
			 users.setPassword( passwordEncoder.encode("admin@123"));
			 users.setActive(true);
			 userrepo.save(users);
			 }
		 };
	}
}
