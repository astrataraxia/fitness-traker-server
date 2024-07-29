package com.fitness.tracker_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.tracker_server.dto.ActivityDTO;
import com.fitness.tracker_server.services.activity.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActivityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    ActivityService activityService;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @DisplayName("PostActivity: 성공해서 Activity 정보를 반환한다.")
    @Test
    public void successPostActivity() throws Exception {
        //given
        ActivityDTO inputDTO = new ActivityDTO(null,
                LocalDateTime.now(),
                1,
                1.0,
                100);

        String json = objectMapper.writeValueAsString(inputDTO);

        ActivityDTO outputDTO = new ActivityDTO(1L,
                inputDTO.date(),
                inputDTO.steps(),
                inputDTO.distance(),
                inputDTO.caloriesBurned());

        when(activityService.postActivity(any(ActivityDTO.class))).thenReturn(outputDTO);


        // When
        MvcResult result = mockMvc.perform(post("/api/activity")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();


        // Then
        ActivityDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), ActivityDTO.class);

        assertThat(response.id()).isNotNull();
        assertThat(response.steps()).isEqualTo(inputDTO.steps());
        assertThat(response.date()).isEqualTo(inputDTO.date());
        assertThat(response.distance()).isEqualTo(inputDTO.distance());
        assertThat(response.caloriesBurned()).isEqualTo(inputDTO.caloriesBurned());
    }

    @DisplayName("PostActivity: 실패 메시지를 반환한다.")
    @Test
    public void failPostActivity() throws Exception {
        ActivityDTO inputDTO = new ActivityDTO(null,
                LocalDateTime.now(),
                1000,
                1.5,
                100);
        String json = objectMapper.writeValueAsString(inputDTO);

        // ActivityService가 null을 반환하도록 설정
        when(activityService.postActivity(any(ActivityDTO.class))).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/api/activity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("some thing went wrong"));
    }
}