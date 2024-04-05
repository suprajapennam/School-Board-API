package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.request_dto.UserRequest;
import com.school.sba.response_dto.UserResponse;
import com.school.sba.service.UserService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@RequestBody @Valid UserRequest userRequest)
	{
		return userService.registerAdmin(userRequest);
	}
	
	@PostMapping("/users")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody @Valid UserRequest userRequest)
	{
		return userService.registerUser(userRequest);
	}
	
	@DeleteMapping("/users/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId)
	{
		return userService.deleteUser(userId);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int userId)
	{
		return userService.findUserById(userId);
	}

}
