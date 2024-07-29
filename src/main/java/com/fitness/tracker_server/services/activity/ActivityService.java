package com.fitness.tracker_server.services.activity;

import com.fitness.tracker_server.dto.ActivityDTO;

import java.util.List;

public interface ActivityService {

    ActivityDTO postActivity(ActivityDTO activityDTO);

    List<ActivityDTO> getActivities();
}
