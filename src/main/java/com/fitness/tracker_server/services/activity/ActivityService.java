package com.fitness.tracker_server.services.activity;

import com.fitness.tracker_server.dto.ActivityDTO;

public interface ActivityService {

    ActivityDTO postActivity(ActivityDTO activityDTO);
}
