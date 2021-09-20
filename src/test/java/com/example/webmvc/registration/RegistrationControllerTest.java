package com.example.webmvc.registration;

import com.example.webmvc.favouriteNumber.FavouriteNumberController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    RegistrationController controller;

    @MockBean
    UserService userService;

    @Test
    void registrationForm() throws Exception {
        mvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    void registerUser_valid() throws Exception {
       when(userService.registerNewUserAccount(any(UserDto.class)))
               .thenThrow(new DataIntegrityViolationException("AAAA"));/*
                .thenAnswer(input -> {
                    UserDto dto = input.getArgument(0);
                    var user = new User();
                    user.setId(1);
                    user.setUsername(dto.getUsername());
                    user.setPassword(dto.getPassword());
                    user.setEmail(dto.getEmail());
                    return user;
                });*/

       var validUser = new UserDto("user1","a@b.c", "abc123456", "abc123456");
       mvc.perform(post("/user/register").flashAttr("user", validUser))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/"));
    }
}