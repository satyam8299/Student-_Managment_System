package com.cs.sms.cntrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cs.sms.dto.StudentDTO;
import com.cs.sms.ser.StudentSer;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentCntrl {
	 private static final Logger log =
	            LoggerFactory.getLogger(StudentCntrl.class);
	 
	 private final StudentSer studentSer;
	 
	 public StudentCntrl(StudentSer studentSer) {
		 this.studentSer=studentSer;
	 }
	 @GetMapping("/new")
	 public String showCreateStudent(Model model) {
		 log.info("Get / new - showing create student page");
		 model.addAttribute("studentDTO", new StudentDTO());
		 return "add-student";
		 
	 }
	 @GetMapping("/list")
	 public String listStudent(
	         @RequestParam(name = "page", defaultValue = "0") int page,
	         @RequestParam(name = "size", defaultValue = "5") int size,
	         Model model) {

	     Page<StudentDTO> studentPage = studentSer.getAllStudents(page, size);

	     model.addAttribute("studentPage", studentPage);
	     model.addAttribute("currentPage", page);

	     return "students";
	 }
	 
	 
	 @GetMapping("/view/{id}")
	 public String viewStudent(@PathVariable("id") Long id, Model model) {

	     StudentDTO studentDTO = studentSer.getStudentById(id);

	     model.addAttribute("student", studentDTO);

	     return "view-student";   // view-student.html
	 }
	 
	 @GetMapping("/edit/{id}")
	 public String editStudent(@PathVariable("id") Long id, Model model) {

	     StudentDTO studentDTO = studentSer.getStudentById(id);

	     model.addAttribute("studentDTO", studentDTO);

	     return "edit-student";
	 }
		 
	 
	 @PostMapping("/save")
	 public String createStudent(@Valid @ModelAttribute ("studentDTO") StudentDTO studentDTO,
			 BindingResult bindingResult,
			 Model model,
			 RedirectAttributes redirectAttributes) {
		 
		 log.info("Post /save - create student request received");
		 if(bindingResult.hasErrors()) {
			 return "add-student";
		 }
		 
		 if(studentSer.existsByEmailIgnoreCase(studentDTO.getEmail())) {
			 log.info("Post / save -  email must be unique");
			 bindingResult.rejectValue("email", null, "email must be unique");
			 return "add-student";
		 }
		 
		 studentSer.createStudent(studentDTO);
		 redirectAttributes.addAttribute("message", "student is added successfully");
		 
		 return "redirect:/students/list";
		 
	 }
	 
	 @PostMapping("/update/{id}")
	 public String updateStudent(
	         @PathVariable("id") Long id,
	         @Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
	         BindingResult bindingResult,
	         RedirectAttributes redirectAttributes) {

	     if (bindingResult.hasErrors()) {
	         return "edit-student";
	     }

	     studentSer.updateStudent(id, studentDTO);

	     redirectAttributes.addAttribute("message", "Student updated successfully");

	     return "redirect:/students/list";
	 }
	 
	 @PostMapping("/delete/{id}")
	 public String deleteStudent(
	         @PathVariable("id") Long id,
	         RedirectAttributes redirectAttributes) {

	     studentSer.deleteStudentById(id);

	     redirectAttributes.addFlashAttribute("message",
	             "Student deleted successfully!");

	     return "redirect:/students/list";
	 }
	 
}
