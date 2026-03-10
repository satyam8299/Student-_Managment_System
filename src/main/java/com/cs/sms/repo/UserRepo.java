package com.cs.sms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sms.model.Users;

public interface UserRepo extends JpaRepository<Users, Long>{
 boolean existsByUsername(String username); 
 Optional<Users> findByUsername(String username);
}
