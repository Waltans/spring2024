package com.example.demo.app.repositories;

import com.example.demo.domain.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

}
