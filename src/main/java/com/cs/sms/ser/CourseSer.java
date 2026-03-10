package com.cs.sms.ser;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cs.sms.dto.CourseDTO;

public interface CourseSer{
	CourseDTO createCourse(CourseDTO cousreDTO);
   
	boolean existByCourseCode(String code);
	Page<CourseDTO>getCourses(int page, int size);

	CourseDTO getCourseById(Long id);

	void updateCourse(CourseDTO courseDTO);

	CourseDTO getById(Long id);
	List<CourseDTO> getAllCourses();
	long countCourses();
}
