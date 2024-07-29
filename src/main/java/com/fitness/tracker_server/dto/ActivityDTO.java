package com.fitness.tracker_server.dto;

import java.time.LocalDateTime;

public record ActivityDTO(
        Long id,
        LocalDateTime date,
        int step,
        double distance,
        int caloriesBurned) {
}
