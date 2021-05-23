package com.leaderboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leaderboard.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void read() throws Exception {
        Player player = new Player(Long.valueOf(0), 1000);
        String content = mapper.writeValueAsString(player);
        mockMvc.perform(post("/player/").content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        mockMvc.perform(get("/player/0")).andExpect(status().isOk());
    }

    @Test
    void top() throws Exception {
        mockMvc.perform(get("/player/top")).andExpect(status().isOk());
    }

    @Test
    void near() throws Exception {
        Player player = new Player(Long.valueOf(1), 1000);
        String content = mapper.writeValueAsString(player);
        mockMvc.perform(post("/player/").content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        mockMvc.perform(get("/player/nearby/1")).andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        Player player = new Player(Long.valueOf(2), 1000);
        String content = mapper.writeValueAsString(player);
        mockMvc.perform(post("/player/").content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/player/2")).andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        Player player = new Player(Long.valueOf(3), 1000);
        String content = mapper.writeValueAsString(player);
        mockMvc.perform(post("/player/").content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        mockMvc.perform(put("/player/3").content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        Player player = new Player(Long.valueOf(4), 1000);
        String content = mapper.writeValueAsString(player);
        mockMvc.perform(post("/player/").content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}