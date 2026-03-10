package com.cs.sms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sms.dto.CourseDTO;
import com.cs.sms.dto.StudentDTO;
import com.cs.sms.model.Students;
import com.cs.sms.repo.StudentRepo;
import com.cs.sms.ser.StudentSer;

@Service
@Transactional
public class StudentSerImpl implements StudentSer {
	
	 private static final Logger log =
	            LoggerFactory.getLogger(StudentSerImpl.class);
  private final StudentRepo studentRepo;
  
  private final ModelMapper mapper;
  
  public StudentSerImpl(StudentRepo studentRepo, ModelMapper mapper) {
	  this.studentRepo=studentRepo;
	  this.mapper=mapper;
  }
	@Override
	public boolean existsByEmailIgnoreCase(String email) {
	 log.info("email from create student");
		return studentRepo.existsByEmailIgnoreCase(email);
	}
	@Override
	public StudentDTO createStudent(StudentDTO studentDTO) {
		 log.info(" student saving data");
		Students students = mapper.map(studentDTO, Students.class);
		Students saved = studentRepo.save(students);
		return mapper.map(saved,StudentDTO.class);
	}
//	@Override
//	public List<StudentDTO> getAllStudents() {
//	    List<Students> list = studentRepo.findAll();
//
//	    return list.stream()
//	            .map(student -> mapper.map(student, StudentDTO.class))
//	            .toList();
//	}
	@Override
	public Page<StudentDTO> getAllStudents(int page, int size) {

	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

	    Page<Students> studentPage = studentRepo.findAll(pageable);

	    return studentPage.map(student ->
	            mapper.map(student, StudentDTO.class));
	}
	@Override
	public StudentDTO getStudentById(Long id) {

	    log.info("Fetching student by id: {}", id);

	    Students student = studentRepo.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Student not found with id: " + id));

	    return mapper.map(student, StudentDTO.class);
	}
	@Override
	public void updateStudent(Long id, StudentDTO dto) {

	    log.info("Updating student with id: {}", id);

	    Students student = studentRepo.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Student not found with id: " + id));

	    student.setFirstName(dto.getFirstName());
	    student.setLastName(dto.getLastName());
	    student.setEmail(dto.getEmail());
	    student.setPhoneNumber(dto.getPhoneNumber());
	    student.setAddress(dto.getAddress());
	    student.setActive(dto.isActive());

	    studentRepo.save(student);
	}
	@Override
	public void deleteStudentById(Long id) {

	    Students student = studentRepo.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Student not found with id: " + id));

	    studentRepo.delete(student);
	}
	@Override
	public List<StudentDTO> getAllStudents() {
	
		return studentRepo.findByActiveTrue().stream()
				.map(student -> mapper.map(student, StudentDTO.class))
				.collect(Collectors.toList());
	}
	@Override
	public long countStudents() {
	    log.info("Counting total students");
	    return studentRepo.count();
	}
}
