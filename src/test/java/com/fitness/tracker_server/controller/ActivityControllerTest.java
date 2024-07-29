package com.fitness.tracker_server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.tracker_server.dto.ActivityDTO;
import com.fitness.tracker_server.entity.Activity;
import com.fitness.tracker_server.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    ActivityRepository activityRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        activityRepository.deleteAll();
    }

    @DisplayName("PostActivity: 정보를 생성하여,저장후 반환")
    @Test
    public void successPostActivity() throws Exception {
        //given
        ActivityDTO inputDTO = new ActivityDTO(null,
                LocalDateTime.now(),
                1,
                1.0,
                100);

        String json = objectMapper.writeValueAsString(inputDTO);

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

    @DisplayName("getActivities: 저장된 모든 활동 정보를 반환")
    @Test
    public void successGetActivities() throws Exception {
        // Given
        Activity inputActivity1 = Activity.builder()
                .date(LocalDateTime.now())
                .steps(1)
                .distance(1.0)
                .caloriesBurned(100)
                .build();
        Activity inputActivity2 = Activity.builder()
                .date(LocalDateTime.now().plusDays(1))
                .steps(1)
                .distance(1.0)
                .caloriesBurned(100)
                .build();

        activityRepository.save(inputActivity1);
        activityRepository.save(inputActivity2);

        // When
        MvcResult result = mockMvc.perform(get("/api/activities")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<ActivityDTO> responseList = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<ActivityDTO>>() {});

        assertThat(responseList).hasSize(2);
        assertThat(responseList).extracting(ActivityDTO::steps)
                .containsExactlyInAnyOrder(inputActivity1.getSteps(), inputActivity2.getSteps());
        assertThat(responseList).extracting(ActivityDTO::distance)
                .containsExactlyInAnyOrder(inputActivity1.getDistance(), inputActivity2.getDistance());
        assertThat(responseList).extracting(ActivityDTO::caloriesBurned)
                .containsExactlyInAnyOrder(inputActivity1.getCaloriesBurned(), inputActivity2.getCaloriesBurned());
    }

}