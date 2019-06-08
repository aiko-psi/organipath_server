package de.aklingauf.organipath.controller;

import de.aklingauf.organipath.exception.AppException;
import de.aklingauf.organipath.model.Role;
import de.aklingauf.organipath.model.User;
import de.aklingauf.organipath.payload.ApiResponse;
import de.aklingauf.organipath.payload.JwtAuthenticationResponse;
import de.aklingauf.organipath.payload.LoginRequest;
import de.aklingauf.organipath.payload.SignUpRequest;
import de.aklingauf.organipath.repository.UserRepository;
import de.aklingauf.organipath.security.CustomUserDetailsService;
import de.aklingauf.organipath.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

// From https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-2/

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // some authentication magic here
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token generation and getting the user details
        String jwt = tokenProvider.generateToken(authentication);
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found ")
        );

        // return token and username as a JwtAuthenticationResponse format
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        // first check if the username and email are not already taken
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // hard coded voucher for invitation (because not everybody should use the app)
        if(signUpRequest.getVoucher().equals("0264/V!947#")) {
            return new ResponseEntity<>(new ApiResponse(false, "Voucher invalid"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Client Role hardcoded. Who needs admins?
        user.setRoles(Arrays.asList(Role.ROLE_CLIENT));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
