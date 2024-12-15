package com.upwork.urlshortener.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.TimeUnit;

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
public class URLControllerTest {
    private static final Logger log = LoggerFactory.getLogger(URLControllerTest.class);

    @Autowired
    private UrlController urlController;

     
    @Autowired
    private MockMvc mockMvc;
 

    @Test
    @Order(1)
    public void contextLoads() throws Exception {
        assertThat(urlController).isNotNull();
    }

    @Test
    @Order(2)
    public void testShortenHappy() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .post("/url").with(csrf())
        .param("url", "http://example.com")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("shortName"));
    }

    @Test
    @Order(3)
    public void testShortenWithExistingUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .post("/url").with(csrf())
        .param("url", "http://example.com")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(matcher -> {
            String content = matcher.getResponse().getContentAsString();
            assertThat(content).contains("URL already exists in database. Please use the short link generated");
        });
      
    }

    @Test
    @Order(4)
    public void testShortenWithInvalidUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .post("/url").with(csrf())
        .param("url", "aba ds sdfsadf")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(matcher -> {
            String content = matcher.getResponse().getContentAsString();
            assertThat(content).contains("Invalid character string entered. Does not match to an a valid website address.");
        });
    }

    @Test
    @Order(5)
    public void testRedirectHappy() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        .post("/url").with(csrf())
        .param("url", "http://msn.com")
        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String shortName = result.getModelAndView().getModel().get("shortName").toString();

        mockMvc.perform(MockMvcRequestBuilders
        .get("/url/" + shortName)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection());
    }

    @Test
    @Order(6)
    public void testRedirectURLExpired() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        .post("/url").with(csrf())
        .param("url", "http://youtube.com")
        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String shortName = result.getModelAndView().getModel().get("shortName").toString();
        
        log.info("Sleeping for 5 seconds to expire the URL");
        TimeUnit.SECONDS.sleep(5);
        
        mockMvc.perform(MockMvcRequestBuilders
        .get("/url/" + shortName)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(matcher -> {
            String content = matcher.getResponse().getContentAsString();
            assertThat(content).contains("URL has expired");
        });
    }
}
