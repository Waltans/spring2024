package com.example.demo.app.service;

import com.example.demo.app.repositories.ScheduleRepository;
import com.example.demo.domain.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TutorService tutorService;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, TutorService tutorService) {
        this.scheduleRepository = scheduleRepository;
        this.tutorService = tutorService;
    }

    /**
     * Метод для поиска расписания по ID
     *
     * @param id -id расписания
     * @return - Optional<Schedule>
     */
    public Optional<Schedule> getById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isPresent()) {
            log.info("Расписание с id = {} найдено", id);
        } else {
            schedule = Optional.empty();
            log.info("Расписание не найдено");
        }
        return schedule;
    }
}
