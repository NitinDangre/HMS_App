package com.hms.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {

    private String username;
    private String Password;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
