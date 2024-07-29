package com.fitness.tracker_server.services.activity;

import com.fitness.tracker_server.dto.ActivityDTO;
import com.fitness.tracker_server.entity.Activity;
import com.fitness.tracker_server.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService{

    private final ActivityRepository activityRepository;

    @Override
    public ActivityDTO postActivity(ActivityDTO dto) {
        Activity activity = Activity.builder()
                .date(dto.date())
                .steps(dto.steps())
                .distance(dto.distance())
                .caloriesBurned(dto.caloriesBurned())
                .build();

        return ActivityDTO.responseActivity(activity);
    }

}
