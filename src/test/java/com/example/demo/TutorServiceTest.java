package com.example.demo;


import com.example.demo.app.repositories.QuestionRepository;
import com.example.demo.app.repositories.TutorRepository;
import com.example.demo.app.service.QuestionService;
import com.example.demo.app.service.TutorService;
import com.example.demo.domain.Question;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.users.Tutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TutorServiceTest {

    @Autowired
    TutorService tutorService;

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

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
        tutor.setEmail("someEmail@example.com");
        String newEmail = "pasha123@example.com";
        tutorService.addTutor(tutor);
        tutorService.updateEmailTutor(tutor.getId(), newEmail);
        Tutor tutorWithNewEmail = tutorRepository.findById(tutor.getId()).get();
        assertEquals(tutorWithNewEmail.getEmail(), newEmail);
    }

    @Test
    void getTutorByIdTest() {
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        Optional<Tutor> result = tutorService.getTutorById(tutor.getId());
        assertTrue(result.isPresent());
        assertEquals(tutor.getId(), result.get().getId());
    }

    @Test
    void getTutorByIdNullTest() {
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        Optional<Tutor> result = tutorService.getTutorById(tutor.getId() + 1);
        assertTrue(result.isEmpty());
    }

    @Test
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
        tutorService.assignScheduleToTutor(tutorRepository.findById(tutor.getId()).get().getId(), scheduleList);

        Optional<Tutor> savedTutor = tutorRepository.findById(tutor.getId());

        assertNotNull(savedTutor);
        assertEquals(scheduleList.size(), savedTutor.get().getSchedule().size());
    }

    @Test
    public void MakeQuestionTest() {
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        tutorService.MakeQuestion(tutor.getId(), "123", "test");
        assertEquals(1, tutor.getQuestions().size());
    }
}
