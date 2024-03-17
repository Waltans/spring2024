package com.example.demo;

import com.example.demo.app.service.QuestionService;
import com.example.demo.domain.Question;
import com.example.demo.app.repositories.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuestionsServiceTest {


    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void addQuestionTest() {
        Question question = new Question();
        questionService.addQuestion(question);
        assertTrue(questionRepository.existsById(question.getId()));
    }

    @Test
    void findByIdTest() {
        Question question = new Question();
        question = questionRepository.save(question);
        assertEquals(question.getId(), questionService.findById(question.getId()).getId());
    }

    @Test
    void deleteQuestionByIdTest(){
        Question question = new Question();
        questionService.addQuestion(question);
        questionService.deleteQuestion(question.getId());
        assertFalse(questionRepository.existsById(question.getId()));
    }

    @Test
    void deleteQuestionTest(){
        Question question = new Question();
        questionService.addQuestion(question);
        questionService.deleteQuestion(question);
        assertFalse(questionRepository.existsById(question.getId()));
    }
}
