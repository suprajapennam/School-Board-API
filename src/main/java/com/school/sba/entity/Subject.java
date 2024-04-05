package com.school.sba.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import lombok.Setter;

@Entity
@Getter
@Setter
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int subjectId;
	private String subjectName;
	
	@ManyToMany(mappedBy = "subjects")
	private List<AcademicProgram> programs;
	
	@OneToMany(mappedBy = "subject")
	private List<User> users;
	
	@OneToMany(mappedBy = "subject")
	private List<ClassHour> classHours;
}
