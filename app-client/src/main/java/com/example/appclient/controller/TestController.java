package com.example.appclient.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    private static final String INDEX = "<a href='/userInfo'>Get User Info</a> | <a href='/getAdminResource'>Get Admin Resource</a>";
    private static final String HEADER = "<a href='/'>Home</a> | <a href='/logout'>Logout</a><br/><br/>";
    private static final String USER_INFO = "OAuth User Name: %userName%<br/>Client Name: %clientName%<br/>User Attributes: %attributes%";

    @Autowired
    private WebClient webClient;

    @GetMapping("/")
    public String index() {
        return INDEX;
    }

    @GetMapping("/userInfo")
    public String getUserInfo(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient, @AuthenticationPrincipal OAuth2User oauth2User) {
        String userInfo = USER_INFO.replace("%userName%", oauth2User.getName())
            .replace("%clientName%", authorizedClient.getClientRegistration().getClientName())
            .replace("%attributes%", JSONObject.toJSONString(oauth2User.getAttributes()));
        return HEADER + userInfo;
    }

    @GetMapping("/getAdminResource")
    public String getAdminResource() {
        return webClient
            .get()
            .uri("/getAdminResource")
            .retrieve()
            .bodyToMono(String.class)
            .onErrorReturn("Fail to access resource!")
            .block();
    }

    @GetMapping("/callback")
    public String callback(String code) {
        return "This is Redirect URL.";
    }
}
