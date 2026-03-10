package com.cs.sms.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sms.model.Courses;

public interface CourseRepo extends JpaRepository<Courses, Long>{ 
	
  boolean existsByCourseCodeIgnoreCase(String code);
  
   Page<Courses> findByActiveTrue(Pageable pageable);
   List<Courses> findByActiveTrue(Sort sort);
   
}
