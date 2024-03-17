package com.example.demo;

import com.example.demo.app.repositories.TutorRepository;
import com.example.demo.app.service.TutorService;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.users.Tutor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TutorServiceTest {

    @Autowired
    TutorService tutorService;

    @Autowired
    TutorRepository tutorRepository;

    @Test
    void addStudentTest() {
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        assertTrue(tutorRepository.existsById(tutor.getId()));
    }

    @Test
    void deleteTutorByIdTest() {
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        tutorService.deleteTutor(tutor.getId());
        assertFalse(tutorRepository.existsById(tutor.getId()));
    }

    @Test
    void deleteTutorTest() {
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        tutorService.deleteTutor(tutor);
        assertFalse(tutorRepository.existsById(tutor.getId()));
    }

    @Test
    void updateEmailTutorTest() {
        Tutor tutor = new Tutor();
        String newEmail = "pasha123@example.com";
        tutorService.addTutor(tutor);
        tutorService.updateEmailTutor(tutor.getId(), newEmail);
        Tutor tutorWithNewEmail = tutorService.getTutorById(tutor.getId());
        assertEquals(tutorWithNewEmail.getEmail(), newEmail);
    }

    @Test
    void getTutorByIdTest() {
        Tutor tutor = new Tutor();
        tutor = tutorRepository.save(tutor);
        assertEquals(tutor.getId(), tutorService.getTutorById(tutor.getId()).getId());
    }

    @Test
    @Transactional
    public void addScheduleToTutorTest() {
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);

        List<Schedule> scheduleList = new ArrayList<>();

        Schedule schedule = new Schedule();
        schedule.setStartTime(LocalDateTime.now());
        schedule.setEndTime(LocalDateTime.now().plusHours(1));
        scheduleList.add(schedule);

        Schedule schedule1 = new Schedule();
        schedule.setStartTime(LocalDateTime.now().minusDays(2));
        schedule.setEndTime(LocalDateTime.now().minusDays(2).plusHours(1));
        scheduleList.add(schedule1);

        tutorService.assignScheduleToTutor(tutor.getId(), scheduleList);

        Tutor savedTutor = tutorService.getTutorById(tutor.getId());

        assertNotNull(savedTutor);
        assertEquals(scheduleList.size(), savedTutor.getSchedule().size());
    }

}
