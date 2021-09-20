package com.example.webmvc.ipStuff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IPDisplay.class)
class IPDisplayTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IPAddressServiceImpl service;

    @Test
    public void shouldReturnIPAddress() throws Exception {
        var data = UUID.randomUUID().toString();

        when(service.getRemoteIP(any())).thenReturn(data);
        when(service.getIPVersion(any())).thenReturn("IPv4");

        this.mvc.perform(get("/ip"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(data)))
                .andExpect(content().string(containsString("IPv4")));
    }
}