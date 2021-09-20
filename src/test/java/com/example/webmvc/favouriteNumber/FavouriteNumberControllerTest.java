package com.example.webmvc.favouriteNumber;

import com.example.webmvc.registration.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        properties = {"spring.datasource.url=jdbc:h2:mem:testdb", "spring.jpa.hibernate.ddl-auto=create-drop"})
@WebMvcTest(FavouriteNumberController.class)
class FavouriteNumberControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private FavouriteNumberService service;

    @MockBean
    private UserService userService;

    final String loginURL = "http{s?}://*/login";

    @Test
    @WithMockUser
    void saveNumber() throws Exception {
        this.mvc.perform(post("/saveNumber", new FavouriteNumber(5, 60L)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void saveNumber_redirect() throws Exception {
        this.mvc.perform(post("/saveNumber", new FavouriteNumber(48, 90L)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(loginURL));
    }

    @Test
    @WithMockUser
    void addNumber() throws Exception {
        this.mvc.perform(get("/addNumber"))
                .andExpect(status().isOk())
                .andExpect(view().name("addNumber"));

    }

    @Test
    void addNumber_redirect() throws Exception {
        this.mvc.perform(get("/addNumber"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(loginURL));
    }

    @Test
    @WithMockUser
    void listNumbers_withOneEntry() throws Exception {
        when(service.getPaginated(anyInt(), anyInt())).thenReturn(Arrays.asList(
                new FavouriteNumber(10, 1L)
        ));

        when(service.getNumberCount()).thenReturn(1L);

        this.mvc.perform(get("/listNumbers"))
                .andExpect(status().isOk())
                // Table contains the number
                .andExpect(content().string(containsString("<tr id=\"number_1\">")))
                .andExpect(content().string(containsString("<td>10</td>")))
                // Back and Next buttons are disabled
                .andExpect(content().string(containsString("disabled\">Back</a>")))
                .andExpect(content().string(containsString("disabled\">Next</a>")));
    }

    @Test
    @WithMockUser
    void listNumbers_withMultipleEntries() throws Exception {
        var numbers = new ArrayList<FavouriteNumber>();
        for (int i = 0; i < 5; i++) {
            numbers.add(new FavouriteNumber(i * 10, (long) i));
        }
        when(service.getPaginated(anyInt(), anyInt())).thenReturn(numbers);

        when(service.getNumberCount()).thenReturn(10L);

        this.mvc.perform(get("/listNumbers"))
                .andExpect(status().isOk())
                // Back button disabled, Next enabled
                .andExpect(content().string(containsString("disabled\">Back</a>")))
                .andExpect(content().string(not(containsString("disabled\">Next</a>"))));
    }

    @Test
    void listNumbers_redirect() throws Exception {
        this.mvc.perform(get("/listNumbers"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(loginURL));
    }
}