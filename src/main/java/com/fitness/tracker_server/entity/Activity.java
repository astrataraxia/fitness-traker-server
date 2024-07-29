package com.fitness.tracker_server.entity;

import com.fitness.tracker_server.dto.ActivityDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private int steps;

    private double distance;

    private int caloriesBurned;

    @Builder
    private Activity(int caloriesBurned, LocalDateTime date, double distance, int steps) {
        this.caloriesBurned = caloriesBurned;
        this.date = date;
        this.distance = distance;
        this.steps = steps;
    }
}
