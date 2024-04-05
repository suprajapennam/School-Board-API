package com.school.sba.entity;

import java.util.List;

import com.school.sba.enums.UserRole;

import jakarta.persistence.Column;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "users", uniqueConstraints = {
       @UniqueConstraint(columnNames = {"username", "contactNo", "email"})
											})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(unique = true)
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private long contactNo;
	@Column(unique = true)
	private String email;
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	private boolean isDeleted;
	
	@ManyToOne
	@JoinColumn(name = "schoolId")
	private School school;
	
	@ManyToMany
	@JoinTable(name = "user_academicProgram",
	joinColumns = @JoinColumn(name = "userId"),
	inverseJoinColumns = @JoinColumn(name = "programId"))
	private List<AcademicProgram> academicPrograms;

	@ManyToOne
	private Subject subject;
	
	@OneToMany(mappedBy = "user")
	private List<ClassHour> classHours;
}
