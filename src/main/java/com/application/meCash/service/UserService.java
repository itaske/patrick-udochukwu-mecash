package com.application.meCash.service;

import com.application.meCash.dto.request.LoginRequest;
import com.application.meCash.dto.request.UserRegistrationRequest;
import com.application.meCash.dto.response.UserDetailsResponse;
import com.application.meCash.dto.response.UserRegistrationResponse;

public interface UserService {




    UserRegistrationResponse registerUser(UserRegistrationRequest request);

    String loginUser(LoginRequest request);

    UserDetailsResponse getUserDetails();
}
