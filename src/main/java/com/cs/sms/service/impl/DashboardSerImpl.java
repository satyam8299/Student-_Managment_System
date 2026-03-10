package com.cs.sms.service.impl;

import org.springframework.stereotype.Service;

import com.cs.sms.repo.CourseRepo;
import com.cs.sms.repo.EnrollmentRepo;
import com.cs.sms.repo.StudentRepo;
import com.cs.sms.ser.DashboardSer;
import com.cs.sms.ser.EnrollmentSer;

@Service
public class DashboardSerImpl implements DashboardSer {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final EnrollmentRepo enrollmentRepo;
    private final EnrollmentSer enrollmentSer;

    public DashboardSerImpl(StudentRepo studentRepo,
                            CourseRepo courseRepo,
                            EnrollmentRepo enrollmentRepo,
                            EnrollmentSer enrollmentSer) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.enrollmentSer = enrollmentSer;
    }

    @Override
    public long getTotalStudents() {
        return studentRepo.count();
    }

    @Override
    public long getTotalCourses() {
        return courseRepo.count();
    }

    @Override
    public long getTotalEnrollments() {
        return enrollmentRepo.count();
    }

    @Override
    public long getThisMonthEnrollments() {
        return enrollmentSer.countThisMonth();
    }

    @Override
    public String getTopCourseName() {
        return enrollmentSer.getTopCourseName();
    }

  

}