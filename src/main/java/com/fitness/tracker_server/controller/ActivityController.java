package com.fitness.tracker_server.controller;

import com.fitness.tracker_server.dto.ActivityDTO;
import com.fitness.tracker_server.services.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping("/activity")
    public ResponseEntity<ActivityDTO> postActivity(@RequestBody ActivityDTO dto) {
        ActivityDTO createActivity = activityService.postActivity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createActivity);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<ActivityDTO>> getActivities() {
        return ResponseEntity.ok(activityService.getActivities());
    }
}
