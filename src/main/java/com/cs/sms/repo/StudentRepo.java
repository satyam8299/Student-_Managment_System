package com.cs.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sms.model.Students;

public interface StudentRepo extends JpaRepository<Students, Long >{
	
	boolean existsByEmailIgnoreCase(String email);
	List<Students> findByActiveTrue();

}
