package com.teameth.moviebooking.models;

import java.util.List;

public class AuthenticationRequest {
    private String username;
    private String password;

    private List<String> authoritites;

    public List<String> getAuthoritites() {
        return authoritites;
    }

    public void setAuthoritites(List<String> authoritites) {
        this.authoritites = authoritites;
    }

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
