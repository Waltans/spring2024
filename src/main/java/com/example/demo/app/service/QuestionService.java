package com.example.demo.app.service;

import com.example.demo.domain.Question;
import com.example.demo.extern.repositories.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Создание вопроса и добавление его в базу данных
     * @param question - объект вопроса
     */
    public void saveQuestion(Question question) {
        questionRepository.save(question);
        log.info("Создан новый вопрос под Id {} Репетитором с ID{}", question.getId(), question.getTutor());

    }
}
