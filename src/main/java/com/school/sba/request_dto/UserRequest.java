package com.school.sba.request_dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	@NotBlank(message = "Username is required")
	@Column(unique = true)
	@Pattern(regexp = "^[a-zA-Z]+$")
	private String username;
	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
			+ " contain at least one uppercase, one lowercase, one number and one special character")
	private String password;
	@NotBlank(message = "FirstName is required")
	private String firstName;
	@NotBlank(message = "LastName is required")
	private String lastName;
	@Min(value = 6000000000l, message = " phone number must be valid")
	@Max(value = 9999999999l, message = " phone number must be valid")
	@Column(unique = true)
	private long contactNo;
	@NotBlank(message = "Email is required")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	@Column(unique = true)
	private String email;
	@NotBlank(message = "UserRole is required")
	private String userRole;
}
