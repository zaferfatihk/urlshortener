package com.upwork.urlshortener.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ModelAndView register(@RequestParam("username") String username, @RequestParam("password") String password) {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles("USER")
                .build();
        ((InMemoryUserDetailsManager) userDetailsService).createUser(user);

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("savedUser", true);
        return modelAndView;
    } 

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
