package com.magicpostapi.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AuthenticationResponse {

    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("location")
    private String location;
    @JsonProperty("email")
    private String email;
    @JsonProperty("token")
    private String accessToken;
    @JsonProperty("role")
    private String role;

    public AuthenticationResponse(String accessToken, String refreshToken, String role) {
        this.accessToken = accessToken;
        this.role = role;
    }

}
