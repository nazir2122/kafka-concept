package com.kafka.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.user.model.DecisionResponse;
import com.kafka.user.model.UserInfo;
import com.kafka.user.model.UserResponse;
import com.kafka.user.service.UserHandlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserHandlerService userHandlerService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testUserService() throws Exception {
        mockMvc.perform(get("/user/v1/"))
                .andExpect(status().isOk())
                .andExpect(content().string("user-producer service"));
    }

    @Test
    public void testHealth() throws Exception {
        mockMvc.perform(get("/user/v1/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("user producer service up and running..."));
    }

    /*@Test
    public void testProcessData() throws Exception {
        UserInfo userInfo = new UserInfo();
        UserResponse userResponse = new UserResponse();
        when(userHandlerService.decisionResponse(any(UserInfo.class), any(DecisionResponse.class))).thenReturn(new DecisionResponse());

        mockMvc.perform(post("/user/v1/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userInfo)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userResponse)));
    }*/
}