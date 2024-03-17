package com.example.demo.app.service;


import com.example.demo.domain.Question;
import com.example.demo.app.repositories.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Сервис для взаимодействия с вопросами
 */
@Service
@Slf4j
public class QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Метод для сохранения вопроса в базу данных
     * @param question - объект вопроса
     */
    public void addQuestion(Question question){
        questionRepository.save(question);
        log.info("Вопрос с id = {} сохранен в БД", question.getId());
    }

    /**
     * Нахождение вопроса по его ID
     * @param questionId - id вопроса
     * @return - вопрос
     */
    public Question findById(Long questionId){
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            log.info("Вопрос id = {} не найден", questionId);
            return new RuntimeException("Вопрос с таким ID не найден");
        });
        log.info("Вопрос с id = {} найден", questionId);
        return question;
    }

    /**
     * Удаление вопрос по id
     * @param questionId - id вопроса
     */
    public void deleteQuestion (Long questionId){
        Question question = findById(questionId);
        questionRepository.delete(question);
        log.info("Вопрос с id = {} удален", questionId);
    }

    /**
     * Удаление вопроса с помощью его объекта
     * @param question - объект вопроса
     */
    public void deleteQuestion(Question question){
        questionRepository.delete(question);
        log.info("Вопрос с id = {} удален", question.getId());
    }
}
