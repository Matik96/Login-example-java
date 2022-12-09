//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mkowalski.spring.login.payload.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {
    @NotBlank
    @Size(
            min = 3,
            max = 20
    )
    private String username;
    @NotBlank
    @Size(
            max = 50
    )
    @Email
    private String email;
    private Set<String> role;
    @NotBlank
    @Size(
            min = 6,
            max = 40
    )
    private String password;

    public SignupRequest() {
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setRole(final Set<String> role) {
        this.role = role;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public String getPassword() {
        return this.password;
    }
}
