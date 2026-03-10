package com.cs.sms.cntrl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.cs.sms.dto.EnrollmentDTO;
import com.cs.sms.ser.CourseSer;
import com.cs.sms.ser.EnrollmentSer;
import com.cs.sms.ser.StudentSer;

@Controller
@RequestMapping("/enrollments")
public class EnrollCntrl {
	

	  private static final Logger log =
	            LoggerFactory.getLogger(EnrollCntrl.class);
	  private final CourseSer courseSer;
	  private final StudentSer studentSer;
	  private final EnrollmentSer enrollmentSer;

	  public EnrollCntrl(CourseSer courseSer,
	                     StudentSer studentSer,
	                     EnrollmentSer enrollmentSer) {
	      this.courseSer = courseSer;
	      this.studentSer = studentSer;
	      this.enrollmentSer = enrollmentSer;
	  }
	  
		@GetMapping("/showEnroll")
		   public String showEnroll(Model model) {
				log.info("Get /enrollments/showEnroll - showing enrollment  page");
				
				model.addAttribute("enrollmentDTO",new EnrollmentDTO());
				model.addAttribute("courseList", courseSer.getAllCourses());
				model.addAttribute("studentList", studentSer.getAllStudents());
			   return "enroll-course";

}@PostMapping("/save")
public String saveEnrollment(@ModelAttribute EnrollmentDTO dto) {

    enrollmentSer.saveEnrollment(dto);

    return "redirect:/dashboard";
}
		
		@GetMapping("/course/{courseId}")
		public String viewEnrolledStudents(@PathVariable Long courseId, Model model) {

		    List<EnrollmentDTO> enrollments =
		            enrollmentSer.getEnrollmentsByCourse(courseId);

		    model.addAttribute("enrollments", enrollments);

		    return "enrolled-students";
		}
}
