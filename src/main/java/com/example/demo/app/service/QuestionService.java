package com.example.demo.app.service;


import com.example.demo.app.repositories.QuestionRepository;
import com.example.demo.domain.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TutorService tutorService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, @Lazy TutorService tutorService) {
        this.questionRepository = questionRepository;
        this.tutorService = tutorService;
    }

    /**
     * Метод для сохранения вопроса в базу данных
     *
     * @param question - объект вопроса
     */
    public void addQuestion(Question question, Long tutorId) {
        if (tutorService.getTutorById(tutorId).isPresent()) {
            question.setTutor(tutorService.getTutorById(tutorId).get());
            questionRepository.save(question);
            log.info("Вопрос с id = {} и репетитором под id = {} сохранен в БД", question.getId(), tutorId);
        }
    }

    /**
     * Нахождение вопроса по его ID
     *
     * @param questionId - id вопроса
     * @return - вопрос
     */
    public Optional<Question> getQuestionById(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()) {
            log.info("Вопрос с id = {} найден", questionId);
        } else {
            log.info("Вопрос с id = {} не найден", questionId);
        }
        return question;
    }

    /**
     * Удаление вопроса по id
     *
     * @param questionId - id вопроса
     */
    public void deleteQuestion(Long questionId) {
        Optional<Question> question = getQuestionById(questionId);
        if (question.isPresent()) {
            questionRepository.delete(question.get());
            log.info("Вопрос с id = {} удален", questionId);
        }
    }

    /**
     * Удаление вопроса с помощью его объекта
     *
     * @param question - объект вопроса
     */
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
        log.info("Вопрос с id = {} удален", question.getId());
    }

    /**
     * Изменения статуса "Непонятно" на вопросе
     *
     * @param question      - вопрос, переданный от ученика
     * @param unclearStatus - статус, переданный от ученика
     */
    public void ChangeUnclearStatus(Question question, boolean unclearStatus) {
        if (question != null) {
            question.setUnclear(unclearStatus);
            questionRepository.save(question);
            log.info("Cтатус вопроса с id = {} сменился на 'Непонятный'", question.getId());
        }
    }
}
