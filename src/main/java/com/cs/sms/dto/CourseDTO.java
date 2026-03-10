package com.cs.sms.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CourseDTO {
	
	private Long id;
	@NotBlank(message="Course name is required")
	@Size(max=150,message="Max of 150 character ")
	private String courseName;
	@NotBlank(message="Course code is required")
	private String courseCode;
	@NotBlank(message="Course duration is required")
	private String duration;
	
	@NotNull(message="Course fee is required")
	private BigDecimal fee;
	@Size(max=300,message="Max of 300 character ")
	private String description;
	private Boolean active;

	public Boolean getActive() {
	    return active;
	}

	public void setActive(Boolean active) {
	    this.active = active;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	

}
