package com.school.sba.service;

import org.springframework.http.ResponseEntity;

import com.school.sba.request_dto.UserRequest;
import com.school.sba.response_dto.UserResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> registerUser(@Valid UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@Valid UserRequest userRequest);

}
