package com.example.FashionStore.Response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JwtResponse {
        private String token;
        private String type = "Bearer";
        private Integer id;
    private String username;
    private String email;
//        private List<String> roles;
    private String roles;
        private Date tokenExpireTime;

    public JwtResponse(String token, Integer id, String username,String email, String roles, Date tokenExpireTime) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.tokenExpireTime = tokenExpireTime;
    }
}
