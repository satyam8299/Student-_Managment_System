package com.cs.sms.ser;

import java.util.List;


import org.springframework.data.domain.Page;

import com.cs.sms.dto.StudentDTO;

import jakarta.validation.Valid;

public interface StudentSer {
	boolean existsByEmailIgnoreCase(String email);
	
	StudentDTO createStudent(StudentDTO studentDTO);


	List<StudentDTO> getAllStudents();

	Page<StudentDTO> getAllStudents(int page, int size);

	StudentDTO getStudentById(Long id);

	void updateStudent(Long id, @Valid StudentDTO studentDTO);

	void deleteStudentById(Long id);

	long countStudents();
}
