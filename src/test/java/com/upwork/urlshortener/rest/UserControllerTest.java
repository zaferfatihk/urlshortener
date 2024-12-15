package com.upwork.urlshortener.rest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ActiveProfiles("test")
@WithMockUser(username = "Test", authorities = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

     @Test
    @Order(1)
    public void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    @Order(2)
    public void testRegisterHappy() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        .post("/register").with(csrf())
        .param("username", "Test")
        .param("password", "Test")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("savedUser")).andReturn();

        String savedUser = result.getModelAndView().getModel().get("savedUser").toString();
        String emptyUser = result.getModelAndView().getModel().get("emptyUser").toString();

        assertThat(savedUser).isEqualTo("true");
        assertThat(emptyUser).isEqualTo("false");
        
    }

    @Test
    @Order(3)
    public void testRegisterEmpty() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        .post("/register").with(csrf())
        .param("username", "")
        .param("password", "")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("emptyUser")).andReturn();

        String emptyUser = result.getModelAndView().getModel().get("emptyUser").toString();

        assertThat(emptyUser).isEqualTo("true");
    }

    @Test
    @Order(4)
    public void testRegisterPasswordEmpty() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        .post("/register").with(csrf())
        .param("username", "admin")
        .param("password", "")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("emptyUser")).andReturn();

        String emptyUser = result.getModelAndView().getModel().get("emptyUser").toString();

        assertThat(emptyUser).isEqualTo("true");
    }

    @Test
    @Order(5)
    public void testRegisterUserEmpty() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        .post("/register").with(csrf())
        .param("username", "")
        .param("password", "password")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("emptyUser")).andReturn();

        String emptyUser = result.getModelAndView().getModel().get("emptyUser").toString();

        assertThat(emptyUser).isEqualTo("true");
    }

    @Test
    @Order(6)
    public void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .get("/login").with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
}
