package com.school.sba.entity;

import java.time.LocalDate;
import java.util.List;

import com.school.sba.enums.ProgramType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicProgram {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int programId;
	@Enumerated(EnumType.STRING)
	private ProgramType programType;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	
	@ManyToOne
	@JoinColumn(name = "schoolId")
	private School school;
	
	@ManyToMany(mappedBy = "academicPrograms")
	private List<User> users;
	
	@ManyToMany
	@JoinTable(name = "academicProgram_subject",
	joinColumns = @JoinColumn(name = "programId"),inverseJoinColumns = @JoinColumn(name = "subjectId"))
	private List<Subject> subjects;
	
	@OneToMany(mappedBy = "academicProgram")
	private List<ClassHour> classHours;
	
}
