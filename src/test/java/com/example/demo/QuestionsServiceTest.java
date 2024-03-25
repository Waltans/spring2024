package com.example.demo;

import com.example.demo.app.repositories.QuestionRepository;
import com.example.demo.app.repositories.TutorRepository;
import com.example.demo.app.service.QuestionService;
import com.example.demo.app.service.TutorService;
import com.example.demo.domain.Question;
import com.example.demo.domain.users.Tutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class QuestionsServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Test
    void addQuestionTest() {
        Question question = new Question();
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        questionService.addQuestion(question, tutor.getId());

        assertTrue(questionRepository.existsById(question.getId()));
    }

    @Test
    void findByIdTest() {
        Question question = new Question();
        question = questionRepository.save(question);
        assertEquals(question.getId(), questionService.getQuestionById(question.getId()).get().getId());
    }

    @Test
    void deleteQuestionByIdTest() {
        Question question = new Question();
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        questionService.addQuestion(question, tutor.getId());
        questionService.deleteQuestion(question.getId());
        assertFalse(questionRepository.existsById(question.getId()));
    }

    @Test
    void deleteQuestionTest() {
        Question question = new Question();
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        questionService.addQuestion(question, tutor.getId());
        questionService.deleteQuestion(question);
        assertFalse(questionRepository.existsById(question.getId()));
    }
}
