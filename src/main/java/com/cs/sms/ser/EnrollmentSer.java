package com.cs.sms.ser;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cs.sms.dto.EnrollmentDTO;
import com.cs.sms.model.Enrollment;

public interface EnrollmentSer {
	long countThisMonth();
	String getTopCourseName();
	
	List<EnrollmentDTO> getEnrollmentsByCourse(Long courseId);
	void saveEnrollment(EnrollmentDTO dto);
	Page<Enrollment> getEnrollments(int page);
}
