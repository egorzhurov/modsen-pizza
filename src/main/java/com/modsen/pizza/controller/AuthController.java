package com.modsen.pizza.controller;

import com.modsen.pizza.dto.UserDTO;
import com.modsen.pizza.model.User;
import com.modsen.pizza.security.JWTRequest;
import com.modsen.pizza.security.JWTResponse;
import com.modsen.pizza.security.RefreshJWTRequest;
import com.modsen.pizza.service.AuthService;
import com.modsen.pizza.service.RegistrationService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final RegistrationService registrationService;

    @PostMapping("login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest authRequest) throws AuthException {
        final JWTResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JWTResponse> getNewAccessToken(@RequestBody RefreshJWTRequest request) {
        final JWTResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JWTResponse> getNewRefreshToken(@RequestBody RefreshJWTRequest request) throws AuthException {
        final JWTResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("registration")
    public  ResponseEntity<JWTResponse> registration(@RequestBody UserDTO userDTO){
       return ResponseEntity.ok(registrationService.register(modelMapper.map(userDTO, User.class)));
    }

}