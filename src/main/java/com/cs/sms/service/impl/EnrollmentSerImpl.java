package com.cs.sms.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sms.dto.EnrollmentDTO;
import com.cs.sms.model.Courses;
import com.cs.sms.model.Enrollment;
import com.cs.sms.model.Students;
import com.cs.sms.repo.CourseRepo;
import com.cs.sms.repo.EnrollmentRepo;
import com.cs.sms.repo.StudentRepo;
import com.cs.sms.ser.EnrollmentSer;

@Service
@Transactional
public class EnrollmentSerImpl implements EnrollmentSer {

    private final EnrollmentRepo enrollmentRepo;
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    public EnrollmentSerImpl(EnrollmentRepo enrollmentRepo,
                             StudentRepo studentRepo,
                             CourseRepo courseRepo) {
        this.enrollmentRepo = enrollmentRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    // 🔹 Students enrolled this month
 
 
    @Override
    public long countThisMonth() {

        LocalDateTime firstDay = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);

        return enrollmentRepo.countByEnrolledDateAfter(firstDay);
    }
    // 🔹 Top Course Name (Most enrolled course)
    @Override
    public String getTopCourseName() {

        List<Enrollment> list = enrollmentRepo.findAll();

        if (list.isEmpty()) return "N/A";

        return list.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        e -> e.getCourse().getCourseName(),
                        java.util.stream.Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse("N/A");
    }


    @Override
    public List<EnrollmentDTO> getEnrollmentsByCourse(Long courseId) {

        List<Enrollment> enrollments = enrollmentRepo.findByCourseId(courseId);

        return enrollments.stream().map(e -> {
            EnrollmentDTO dto = new EnrollmentDTO();
            dto.setStudentId(e.getStudent().getId());
            dto.setCourseIds(List.of(e.getCourse().getId()));
            return dto;
        }).toList();
    }
    @Override
    public void saveEnrollment(EnrollmentDTO dto) {

        Students student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        for (Long courseId : dto.getCourseIds()) {

            Courses course = courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrolledDate(LocalDateTime.now());

            enrollmentRepo.save(enrollment);
        }
    }

    @Override
    public Page<Enrollment> getEnrollments(int page) {

        Pageable pageable = PageRequest.of(page, 5); // 5 records per page
        return enrollmentRepo.findAllByOrderByEnrolledDateDesc(pageable);
    }
    }



