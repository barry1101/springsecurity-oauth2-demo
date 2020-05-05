package com.example.resourceserver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class TestController {

    @GetMapping("/")
    @ApiOperation(value = "Index", httpMethod = "GET")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return "Hello, " + jwt.getSubject();
    }

    @PreAuthorize("hasAuthority('SCOPE_message.read')")
    @GetMapping("/userInfo")
    @ApiOperation(value = "User Info", httpMethod = "GET", notes = "Get User Info")
    public Map<String, String> getAuthenticationInfo(Authentication authentication) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", authentication.getName());
        return map;
    }
}
