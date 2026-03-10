package com.cs.sms.cntrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cs.sms.dto.CourseDTO;
import com.cs.sms.ser.CourseSer;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseCntrl {

    private static final Logger log =
            LoggerFactory.getLogger(CourseCntrl.class);

    private final CourseSer courseSer;

    public CourseCntrl(CourseSer courseSer) {
        this.courseSer = courseSer;
    }

    // ================= CREATE PAGE =================
    @GetMapping("/new")
    public String showCreateCourse(Model model) {
        log.info("GET /courses/new - Showing create course page");
        model.addAttribute("courseDTO", new CourseDTO());
        model.addAttribute("activePage", "courses");
        return "add-course";
    }

    // ================= LIST PAGE =================
    @GetMapping("/list")
    public String listCourse(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model) {

        Page<CourseDTO> courses = courseSer.getCourses(page, size);
        model.addAttribute("courses", courses);
        model.addAttribute("activePage", "courses");

        return "courses";  // Make sure courses.html exists
    }

    // ================= VIEW =================
    @GetMapping("/view/{id}")
    public String viewCourse(@PathVariable("id") Long id, Model model) {

        CourseDTO course = courseSer.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("activePage", "courses");

        return "view-course";
    }

    // ================= EDIT =================
    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable("id") Long id, Model model) {

        CourseDTO courseDTO = courseSer.getCourseById(id);
        model.addAttribute("course", courseDTO);
        model.addAttribute("activePage", "courses");

        return "edit-course";
    }

    // ================= UPDATE =================
    @PostMapping("/update")
    public String updateCourse(
            @ModelAttribute("course") CourseDTO courseDTO,
            RedirectAttributes redirectAttributes) {

        courseSer.updateCourse(courseDTO);
        redirectAttributes.addFlashAttribute("successMessage",
                "Course updated successfully");

        return "redirect:/course/list";
    }

    // ================= SAVE =================
    @PostMapping
    public String createCourse(
            @Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.info("POST /courses - Creating course");

        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "courses");
            return "add-course";
        }

        if (courseSer.existByCourseCode(courseDTO.getCourseCode())) {
            bindingResult.rejectValue("courseCode",
                    null,
                    "Code must be unique");
            model.addAttribute("activePage", "courses");
            return "add-course";
        }

        courseSer.createCourse(courseDTO);

        redirectAttributes.addFlashAttribute("successMessage",
                "Course created successfully");

        return "redirect:/course/list";
    }
}