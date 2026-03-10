package com.cs.sms.dto;

import java.util.List;

public class EnrollmentDTO {

    private Long studentId;
    private List<Long> courseIds;

    // Getter Setter for studentId
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // Getter Setter for courseIds
    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }
}