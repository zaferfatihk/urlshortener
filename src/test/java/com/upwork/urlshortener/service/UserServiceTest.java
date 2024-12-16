package com.upwork.urlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;

    @Test
    public void testGetLoggedInUserHappy() throws Exception {
        UserDetails mockUserDetails = Mockito.mock(UserDetails.class);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        
        SecurityContextHolder.setContext(securityContext);

        String loggedInUser = userService.getLoggedInUser();
        assertThat(loggedInUser).isEqualTo("testuser");

        String username = userService.getLoggedInUser();
        assertEquals("testuser", username);
    }
    @Test
    public void testGetLoggedInUserIfAnonymous() throws Exception {
        UserDetails mockUserDetails = Mockito.mock(UserDetails.class);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("anonymousUser");
        
        SecurityContextHolder.setContext(securityContext);
        
        Exception exception = assertThrows(Exception.class, () -> {
            userService.getLoggedInUser();
        });

        assertThat(exception.getMessage()).isEqualTo("User is not logged in");
    }
}
