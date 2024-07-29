package com.fitness.tracker_server.dto;

import com.fitness.tracker_server.entity.Activity;

import java.time.LocalDateTime;

public record ActivityDTO(
        Long id,
        LocalDateTime date,
        int steps,
        double distance,
        int caloriesBurned) {

    public static ActivityDTO responseActivity(Activity activity) {
        return new ActivityDTO(activity.getId(),
                activity.getDate(),
                activity.getSteps(),
                activity.getDistance(),
                activity.getCaloriesBurned());
    }
}
