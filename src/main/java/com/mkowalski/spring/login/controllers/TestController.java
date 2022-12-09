//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mkowalski.spring.login.controllers;

import com.mkowalski.spring.login.models.ERole;
import com.mkowalski.spring.login.models.Role;
import com.mkowalski.spring.login.models.User;
import com.mkowalski.spring.login.payload.response.MessageResponse;
import com.mkowalski.spring.login.repository.RoleRepository;
import com.mkowalski.spring.login.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
        origins = {"*"},
        maxAge = 3600L
)
@RestController
@RequestMapping({"/api/test"})
public class TestController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;

    public TestController() {
    }

    @GetMapping({"/all"})
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping({"/user"})
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping({"/admin"})
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping({"/addAdmin"})
    public ResponseEntity<?> registerAdmin() {
        String username = "admin";
        String password = "password";
        String email = "admin@gmail.com";
        if (this.userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        } else if (this.userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        } else {
            User user = new User(username, email, this.encoder.encode(password));
            Set<String> strRoles = null;
            Set<Role> roles = new HashSet();
            Role userRole = (Role)this.roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> {
                return new RuntimeException("Error: Role is not found.");
            });
            roles.add(userRole);
            Role adminRole = (Role)this.roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> {
                return new RuntimeException("Error: Role is not found.");
            });
            roles.add(adminRole);
            user.setRoles(roles);
            this.userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Admin registered successfully!"));
        }
    }

    @PostMapping({"/addUser"})
    public ResponseEntity<?> registerUser() {
        String username = "user";
        String password = "password";
        String email = "user@gmail.com";
        if (this.userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        } else if (this.userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        } else {
            User user = new User(username, email, this.encoder.encode(password));
            Set<String> strRoles = null;
            Set<Role> roles = new HashSet();
            Role userRole = (Role)this.roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> {
                return new RuntimeException("Error: Role is not found.");
            });
            roles.add(userRole);
            user.setRoles(roles);
            this.userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
    }
}
