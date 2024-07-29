package com.fitness.tracker_server.repository;

import com.fitness.tracker_server.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
