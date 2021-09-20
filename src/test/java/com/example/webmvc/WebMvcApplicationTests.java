package com.example.webmvc;

import com.example.webmvc.favouriteNumber.FavouriteNumberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@TestConfiguration
class WebMvcApplicationTests {

    @Test
    void contextLoads() {
    }

    /*@Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User testUser = new User("test", "123", Collections.emptyList());
        return new InMemoryUserDetailsManager(List.of(testUser));
    }*/
}
