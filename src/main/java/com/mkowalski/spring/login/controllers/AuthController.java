//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mkowalski.spring.login.controllers;

import com.mkowalski.spring.login.models.ERole;
import com.mkowalski.spring.login.models.Role;
import com.mkowalski.spring.login.models.User;
import com.mkowalski.spring.login.payload.request.LoginRequest;
import com.mkowalski.spring.login.payload.request.SignupRequest;
import com.mkowalski.spring.login.payload.response.JwtResponse;
import com.mkowalski.spring.login.payload.response.MessageResponse;
import com.mkowalski.spring.login.repository.RoleRepository;
import com.mkowalski.spring.login.repository.UserRepository;
import com.mkowalski.spring.login.security.jwt.JwtUtils;
import com.mkowalski.spring.login.security.services.UserDetailsImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
        origins = {"*"},
        maxAge = 3600L
)
@RestController
@RequestMapping({"/api/auth"})
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    public AuthController() {
    }

    @PostMapping({"/signin"})
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        List<String> roles = (List)userDetails.getAuthorities().stream().map((item) -> {
            return item.getAuthority();
        }).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping({"/signup"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (this.userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        } else if (this.userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        } else {
            User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), this.encoder.encode(signUpRequest.getPassword()));
            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet();
            if (strRoles == null) {
                Role userRole = (Role)this.roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> {
                    return new RuntimeException("Error: Role is not found.");
                });
                roles.add(userRole);
            } else {
                strRoles.forEach((role) -> {
                    byte var4 = -1;
                    switch(role.hashCode()) {
                        case 92668751:
                            if (role.equals("admin")) {
                                var4 = 0;
                            }
                        default:
                            switch(var4) {
                                case 0:
                                    Role adminRole = (Role)this.roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> {
                                        return new RuntimeException("Error: Role is not found.");
                                    });
                                    roles.add(adminRole);
                                    break;
                                default:
                                    Role userRole = (Role)this.roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> {
                                        return new RuntimeException("Error: Role is not found.");
                                    });
                                    roles.add(userRole);
                            }

                    }
                });
            }

            user.setRoles(roles);
            this.userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
    }
}
