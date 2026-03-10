package com.cs.sms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.sms.dto.CourseDTO;
import com.cs.sms.model.Courses;
import com.cs.sms.repo.CourseRepo;
import com.cs.sms.ser.CourseSer;

@Service
@Transactional
public class CourseServiceimpl implements CourseSer {

    private static final Logger log =
            LoggerFactory.getLogger(CourseServiceimpl.class);

    private final CourseRepo courseRepo;
    private final ModelMapper mapper;

    public CourseServiceimpl(CourseRepo courseRepo, ModelMapper mapper) {
        this.courseRepo = courseRepo;
        this.mapper = mapper;
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {

        Courses courses = mapper.map(courseDTO, Courses.class);
        courses.setActive(true);

        courseRepo.save(courses);

        return mapper.map(courses, CourseDTO.class);
    }

    @Override
    public boolean existByCourseCode(String code) {
        log.info("Checking if code exists: {}", code);
        return courseRepo.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public Page<CourseDTO> getCourses(int page, int size) {

        PageRequest pageRequest =
                PageRequest.of(page, size, Sort.by(Direction.DESC, "id"));

        return courseRepo.findByActiveTrue(pageRequest)
                .map(course -> mapper.map(course, CourseDTO.class));
    }

    @Override
    public CourseDTO getCourseById(Long id) {

        Courses course = courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return mapper.map(course, CourseDTO.class);
    }

    @Override
    public void updateCourse(CourseDTO courseDTO) {

        Courses course = courseRepo.findById(courseDTO.getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCourseName(courseDTO.getCourseName());
        course.setCourseCode(courseDTO.getCourseCode());
        course.setDuration(courseDTO.getDuration());
        course.setFee(courseDTO.getFee());
        course.setDescription(courseDTO.getDescription());
        course.setActive(courseDTO.getActive());

        courseRepo.save(course);
    }

    @Override
    public CourseDTO getById(Long id) {
        return getCourseById(id);
    }

    @Override
    public List<CourseDTO> getAllCourses() {

        return courseRepo.findByActiveTrue(Sort.by("courseName"))
                .stream()
                .map(course -> mapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }

    // ✅ IMPORTANT – Dashboard ke liye
    @Override
    public long countCourses() {
        return courseRepo.count();
    }
}