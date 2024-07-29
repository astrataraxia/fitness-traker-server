package com.fitness.tracker_server.services.activity;

import com.fitness.tracker_server.dto.ActivityDTO;
import com.fitness.tracker_server.entity.Activity;
import com.fitness.tracker_server.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService{

    private final ActivityRepository activityRepository;

    @Override
    public ActivityDTO postActivity(ActivityDTO dto) {
        Activity save = activityRepository.save(Activity.builder()
                .date(dto.date())
                .steps(dto.steps())
                .distance(dto.distance())
                .caloriesBurned(dto.caloriesBurned())
                .build());

        return ActivityDTO.responseActivity(save);
    }

    @Override
    public List<ActivityDTO> getActivities() {
        List<Activity> activities = activityRepository.findAll();

        return activities.stream()
                .map(ActivityDTO::responseActivity)
                .toList();
    }
}
