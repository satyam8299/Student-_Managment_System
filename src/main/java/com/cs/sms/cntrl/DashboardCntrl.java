package com.cs.sms.cntrl;

import com.cs.sms.model.Enrollment;
import com.cs.sms.repo.CourseRepo;
import com.cs.sms.repo.StudentRepo;
import com.cs.sms.ser.EnrollmentSer;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardCntrl {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final EnrollmentSer enrollmentSer;

    // ✅ Proper Constructor Injection
    public DashboardCntrl(StudentRepo studentRepo,
                           CourseRepo courseRepo,
                           EnrollmentSer enrollmentSer) {

        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentSer = enrollmentSer;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(name = "page", defaultValue = "0") int page,
                            Model model) {

        model.addAttribute("totalStudents", studentRepo.count());
        model.addAttribute("totalCourses", courseRepo.count());
        model.addAttribute("topCourse", enrollmentSer.getTopCourseName());
        model.addAttribute("enrollThisMonth", enrollmentSer.countThisMonth());

        Page<Enrollment> enrollmentPage = enrollmentSer.getEnrollments(page);

        model.addAttribute("enrollmentPage", enrollmentPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", enrollmentPage.getTotalPages());

        return "dashboard";
    }
}