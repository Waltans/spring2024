package com.example.demo.app.service;

import com.example.demo.domain.Question;
import com.example.demo.domain.users.Tutor;
import com.example.demo.extern.repositories.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private QuestionService questionService;

    @Test
    public void testSaveQuestion() {
        Question question = new Question();
        question.setTutor(new Tutor());

        questionService.saveQuestion(question);

        verify(questionRepository, times(1)).save(question);
    }
}
