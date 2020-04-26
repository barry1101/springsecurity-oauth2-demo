package com.example.resourceserver.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @GetMapping("/userInfo")
    public Map<String, String> getAuthenticationInfo(Authentication authentication) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", authentication.getName());
        return map;
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/getAdminResource")
    public String getAdminResource() {
        return "This is admin resource.";
    }
}
