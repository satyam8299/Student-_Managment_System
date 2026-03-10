package com.cs.sms.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sms.model.Enrollment;

public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {


    Page<Enrollment> findAllByOrderByEnrolledDateDesc(Pageable pageable);

	List<Enrollment> findByCourseId(Long courseId);

	long countByEnrolledDateAfter(LocalDateTime firstDay);
}