package com.cs.sms.ser;

public interface DashboardSer {

    long getTotalStudents();

    long getTotalCourses();

    long getTotalEnrollments();

    long getThisMonthEnrollments();

    String getTopCourseName();

   
}