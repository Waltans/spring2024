package com.example.demo.app.service;

import com.example.demo.app.repositories.QuestionRepository;
import com.example.demo.app.repositories.ScheduleRepository;
import com.example.demo.app.repositories.StudentRepository;
import com.example.demo.app.repositories.TutorRepository;
import com.example.demo.domain.Question;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class TutorService {
    private final TutorRepository tutorRepository;
    private final ScheduleRepository scheduleRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final StudentService studentService;

    @Autowired
    public TutorService(TutorRepository tutorRepository, ScheduleRepository scheduleRepository,
                        QuestionService questionService, QuestionRepository questionRepository,
                        StudentService studentService) {
        this.tutorRepository = tutorRepository;
        this.scheduleRepository = scheduleRepository;
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.studentService = studentService;
    }

    /**
     * Создает репетитора и сохраняет его в БД
     *
     * @param tutor - объект студента для создания
     */
    public void addTutor(Tutor tutor) {
        tutorRepository.save(tutor);
        log.info("В базу данных добавлен новый репетитор с id = {}", tutor.getId());
    }

    /**
     * Удаляет репетитора из БД по объекту репетитора
     *
     * @param tutor - объект студента для удаления
     */
    public void deleteTutor(Tutor tutor) {
        tutorRepository.delete(tutor);
        log.info("Из базы данных удален репетитор с id = {}", tutor.getId());
    }

    /**
     * Метод для удаления репетитора из БД по id
     *
     * @param tutorId - id по которому происходит удаление
     */
    public void deleteTutor(long tutorId) {
        if (getTutorById(tutorId).isPresent()) {
            tutorRepository.delete(getTutorById(tutorId).get());
            log.info("Из базы данных удален репетитор с id = {}", tutorId);
        }
    }

    /**
     * Метод для поиска репетитора по id
     *
     * @param id - id репетитора по которому мы будем искать
     * @return репетитора с переданным id
     */
    public Optional<Tutor> getTutorById(long id) {
        Optional<Tutor> tutor = tutorRepository.findById(id);
        if (tutor.isPresent() && tutor.get().getRole().equals("Tutor")) {
            log.info("Репетитор с id = {}  найден", id);
            return tutor;
        }
        if (tutor.isEmpty()) {
            log.info("Репетитор с id = {} не найден", id);
        }
        return tutor;
    }

    /**
     * Метод позволяет обновить почту репетитора
     *
     * @param tutorId - id репетитора
     * @param email   - почта, на которую студент меняет существующую
     */
    public void updateEmailTutor(Long tutorId, String email) {
        Optional<Tutor> optionalTutor = getTutorById(tutorId);
        if (optionalTutor.isPresent()) {
            Tutor tutor = optionalTutor.get();
            tutor.setEmail(email);
            tutorRepository.save(tutor);
            log.info("Репетитор с id = {} изменил почту на {}", tutor.getId(), email);
        }
    }


    /**
     * Метод для обновления расписания
     *
     * @param tutorId      - id репетитора
     * @param scheduleList - новое расписание репетитора
     */
    public void assignScheduleToTutor(Long tutorId, List<Schedule> scheduleList) {
        if (getTutorById(tutorId).isPresent()) {
            Tutor tutor = getTutorById(tutorId).get();
            for (Schedule schedule : scheduleList) {
                schedule.setTutor(tutor);
                scheduleRepository.save(schedule);
                log.info("Репетитору с id = {} добавлено расписание с id = {}", tutor.getId(), schedule.getId());
            }
            tutor.setSchedule(scheduleList);

            tutorRepository.save(tutor);
            log.info("У репетитора с id = {} обновлено расписание", tutor.getId());
        }
    }

    /**
     * Назначение вопроса для ученика
     *
     * @param studentId  - id ученика
     * @param questionId - id вопроса
     * @param tutorId    - id репетитора
     */

    public void setQuestionForStudent(Long studentId, Long questionId, Long tutorId) {
        Optional<Question> questionOpt = questionService.getQuestionById(questionId);
        Optional<Student> studentOpt = studentService.getStudentById(studentId);
        if (questionOpt.isPresent() && studentOpt.isPresent() && questionOpt.get().getTutor().getId().equals(tutorId)) {
            Student student = studentOpt.get();
            Question question = questionOpt.get();
            question.setStudent(student);
            student.getAssignedTasks().add(question);
            log.info("Ученику с id = {} был добавлен вопрос с id = {} репетитором с id = {}",
                    studentId, questionId, tutorId);
        } else {
            log.info("Не удалось добавить вопрос с id = {} ученику с id = {}", questionId, studentId);
        }
    }

    /**
     * Создание вопроса с описанием и правильным ответом и добавляет его к уже существующим вопросам репетитора
     *
     * @param tutorId     - id репетитора
     * @param description - описание к вопросу
     * @param rightAnswer - правильный ответ на вопрос
     */
    public void MakeQuestion(Long tutorId, String description, String rightAnswer) {
        Question question = new Question();
        question.setDescription(description);
        question.setRightAnswer(rightAnswer);
        getTutorById(tutorId).get().getQuestions().add(question);
        questionService.addQuestion(question, tutorId);
    }

    /**
     * Назначение оценки за вопрос ученику
     * Если репетитор посчитал, что ответ корректный, то назначает статус выполнения true, иначе false
     *
     * @param studentId  - id студента
     * @param questionId - id вопроса
     * @param grade      - оценка за вопрос
     */
    public void RateStudentQuestion(Long studentId, Long questionId, int grade, boolean completed) {
        Optional<Student> studentOpt = studentService.getStudentById(studentId);
        Optional<Question> questionOpt = questionService.getQuestionById(questionId);
        if (studentOpt.isPresent() && questionOpt.isPresent()) {
            List<Question> questionList = studentOpt.get().getAssignedTasks();
            for (Question question : questionList) {
                if (question.getId().equals(questionId)) {
                    question.setGrade(grade);
                    question.setCompleted(completed);
                    if (completed) {
                        studentOpt.get().getAssignedTasks().remove(question);
                        studentOpt.get().getCompletedTasks().add(question);
                        log.info("Статус выполнения вопроса c id ={}, изменен на true", questionId);
                    }
                    questionRepository.save(question);
                    log.info("Вопросу с id = {} установлена оценка {}", questionId, grade);
                }
            }
        }
    }
}
