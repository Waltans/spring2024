package com.example.demo.app.service;

import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import com.example.demo.app.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;


/**
 * Сервис для взаимодействия с учеником
 */
@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    public void deleteStudentById(long studentId) {
        studentRepository.delete(getStudentById(studentId));
        log.info("Из базы данных удален ученик с id = {}", studentId);
    }

    /**
     * Метод для поиска ученика по id
     *
     * @param id - id студента по которому мы будем искать
     * @return Студента с переданным id
     */
    public Student getStudentById(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> {
            log.error("Ученик с id = {} не найден", id);
            return new RuntimeException(MessageFormat.format("Ученик с id = {0} не найден", id));
        });
        log.info("Ученик с id = {} найден", id);
        return student;
    }

    /**
     * Метод позволяет обновить почту ученика
     *
     * @param studentId - id студента
     * @param email     - почта, на которую студент меняет существующую
     */
    public void updateEmailStudent(Long studentId, String email) {
        Student student = getStudentById(studentId);
        student.setEmail(email);
        studentRepository.save(student);
        log.info("Ученик с id = {} изменил посту с {} на {}", student.getId(), student.getEmail(), email);
    }

    /**
     * @param studentId - id ученика
     * @param tutor     - репетитор, которого мы будем прикреплять
     */
    public void addTutorToStudent(long studentId, Tutor tutor) {
        Student student = getStudentById(studentId);
        student.setAttachedTutor(tutor);
        studentRepository.save(student);
        log.info("Репетитор {} успешно добавлен к ученику с id = {}", tutor.getName(), studentId);
    }


}
