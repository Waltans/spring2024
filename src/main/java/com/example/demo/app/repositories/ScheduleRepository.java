package com.example.demo.app.repositories;

import com.example.demo.domain.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

}
