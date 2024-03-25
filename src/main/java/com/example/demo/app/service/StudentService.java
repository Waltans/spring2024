package com.example.demo.app.service;


import com.example.demo.app.repositories.QuestionRepository;
import com.example.demo.app.repositories.StudentRepository;
import com.example.demo.domain.Question;
import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final TutorService tutorService;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, @Lazy TutorService tutorService,
                          QuestionService questionService, QuestionRepository questionRepository) {
        this.studentRepository = studentRepository;
        this.tutorService = tutorService;
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    /**
     * Создание ученика и сохраняет его в БД
     *
     * @param student - объект студента для создания
     */
    public void addStudent(Student student) {
        studentRepository.save(student);
        log.info("В базу данных добавлен новый ученик с id = {}", student.getId());
    }

    /**
     * Удаляет ученика из БД по объекту ученика
     *
     * @param student - объект студента для удаления
     */
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
        log.info("Из базы данных удален ученик с id = {}", student.getId());
    }

    /**
     * Метод для удаления ученика из БД по id
     *
     * @param studentId - id по которому происходит удаление
     */
    public void deleteStudent(long studentId) {
        if (getStudentById(studentId).isPresent()) {
            studentRepository.delete(getStudentById(studentId).get());
            log.info("Из базы данных удален ученик с id = {}", studentId);
        }
    }

    /**
     * Метод для поиска ученика по id
     *
     * @param id - id студента по которому мы будем искать
     * @return Студента с переданным id
     */
    public Optional<Student> getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent() && student.get().getRole().equals("Student")) {
            log.info("Ученик с id = {} найден", id);
        } else {
            student = Optional.empty();
            log.info("Ученик с id = {} не найден", id);
        }
        return student;
    }

    /**
     * Метод позволяет обновить почту ученика
     *
     * @param studentId - id студента
     * @param email     - почта, на которую студент меняет существующую
     */
    public void updateStudentEmail(Long studentId, String email) {
        if (getStudentById(studentId).isPresent()) {
            Student student = getStudentById(studentId).get();
            student.setEmail(email);
            studentRepository.save(student);
            log.info("Ученик с id = {} изменил посту с {} на {}", student.getId(), student.getEmail(), email);
        }
    }

    /**
     * Метод позволяет добавить по id ученика репетитора c помощью его id
     *
     * @param studentId - id ученика
     * @param tutorId   - id репетитора, которого мы будем прикреплять
     */
    public void addTutorToStudent(long studentId, long tutorId) {
        if (getStudentById(studentId).isPresent() && tutorService.getTutorById(tutorId).isPresent()) {
            Student student = getStudentById(studentId).get();
            Tutor tutor = tutorService.getTutorById(tutorId).get();
            student.setAttachedTutor(tutor);
            studentRepository.save(student);
            log.info("Репетитор c id = {} успешно добавлен к ученику с id = {}", tutor.getId(), studentId);
        }
    }

    /**
     * Метод позволяет добавить ученику репетитора
     *
     * @param student - объект ученика
     * @param tutor   - объект репетитора, которого мы будем прикреплять
     */
    public void addTutorToStudent(Student student, Tutor tutor) {
        if (student != null && tutor != null) {
            student.setAttachedTutor(tutor);
            studentRepository.save(student);
            log.info("Репетитор {} с id = {} успешно добавлен к ученику с id = {}",
                    tutor.getName(), tutor.getId(), student.getId());
        } else {
            log.info("Репетитор не был добавлен к ученику");
        }
    }

    /**
     * Ученик дает ответ на вопрос
     *
     * @param questionId - id вопроса
     * @param studentId  - id ученика
     * @param answer     - Ответ ученика на вопрос
     */
    public void setAnswerForQuestion(Long questionId, Long studentId, String answer) {
        Optional<Question> questionOpt = questionService.getQuestionById(questionId);
        if (questionOpt.isPresent() && questionOpt.get().getStudent().getId().equals(studentId)) {
            Question question = questionOpt.get();
            question.setAnswer(answer);
            question.setCompleted(true);
            questionRepository.save(question);
            log.info("На вопрос под id = {}, был дан ответ учеником с id {}", questionId, studentId);
        } else {
            log.info("Ученик с id = {} не может установить ответ на вопрос с id = {}", studentId, questionId);
        }
    }

    /**
     * Установить вопросу статус непонятно
     *
     * @param questionId    - id вопроса
     * @param studentId     - id студента
     * @param unclearStatus - true(если нужен статус "непонятно"), false (если статус не нужен)
     */
    public void IsQuestionUnclear(Long questionId, Long studentId, boolean unclearStatus) {
        Optional<Question> questionOpt = questionService.getQuestionById(questionId);
        if (questionOpt.isPresent() && questionOpt.get().getStudent().getId().equals(studentId)) {
            questionService.ChangeUnclearStatus(questionOpt.get(), unclearStatus);
            log.info("Вопрос с id = {} стал непонятен ученику с id = {}", questionId, studentId);
        }
    }

    /**
     * Показывает ученику все вопросы, которые ещё не были выполнены
     *
     * @param studentId - id ученика
     * @return List<Question> - список вопросов, если найден ученик. Null, если ученик не найден
     */
    public List<Question> showAllQuestionToComplete(Long studentId) {
        Optional<Student> studentOpt = getStudentById(studentId);
        if (studentOpt.isPresent()) {
            log.info("Ученику с id = {} предоставлены все вопросы", studentId);
            return studentOpt.get().getAssignedTasks();
        }
        return null;
    }
}
