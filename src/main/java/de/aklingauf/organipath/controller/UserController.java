package de.aklingauf.organipath.controller;

import de.aklingauf.organipath.checks.AuthChecker;
import de.aklingauf.organipath.exception.ResourceNotFoundException;
import de.aklingauf.organipath.model.Project;
import de.aklingauf.organipath.model.User;
import de.aklingauf.organipath.repository.ProjRepository;
import de.aklingauf.organipath.repository.ProjectSpecifications;
import de.aklingauf.organipath.repository.UserRepository;
import de.aklingauf.organipath.security.CurrentUser;
import de.aklingauf.organipath.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    public User getUser(@CurrentUser UserPrincipal currentUser){
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User not found ")
        );
        return user;
    }

}


