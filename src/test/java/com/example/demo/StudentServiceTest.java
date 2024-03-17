package com.example.demo;

import com.example.demo.app.service.StudentService;
import com.example.demo.app.service.TutorService;
import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import com.example.demo.app.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class StudentServiceTest {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TutorService tutorService;



    @Test
    void addStudentTest() {
        Student student = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        studentService.addStudent(student);
        studentService.addStudent(student2);
        studentService.addStudent(student3);
        assertTrue(studentRepository.existsById(student.getId()));
        assertTrue(studentRepository.existsById(student2.getId()));
        assertTrue(studentRepository.existsById(student3.getId()));
    }

    @Test
    void deleteStudentByIdTest() {
        Student student = new Student();
        studentService.addStudent(student);
        studentService.deleteStudentById(student.getId());
        assertFalse(studentRepository.existsById(student.getId()));
    }

    @Test
    void deleteStudentTest() {
        Student student = new Student();
        studentService.addStudent(student);
        studentService.deleteStudent(student);
        assertFalse(studentRepository.existsById(student.getId()));
    }

    @Test
    void updateEmailStudentTest() {
        Student student = new Student();
        String newEmail = "pashas@example.com";
        studentService.addStudent(student);
        studentService.updateEmailStudent(student.getId(), newEmail);
        Student studentWithNewEmail = studentService.getStudentById(student.getId());
        assertEquals(studentWithNewEmail.getEmail(), newEmail);
    }

    @Test
    void getStudentByIdTest() {
        Student student = new Student();
        student = studentRepository.save(student);
        assertEquals(student.getId(), studentService.getStudentById(student.getId()).getId());
    }

    @Test
    void addTutorToStudentTest() {
        Student student = new Student();
        studentRepository.save(student);

        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);

        studentService.addTutorToStudent(student.getId(), tutor);

        student = studentService.getStudentById(student.getId());

        assertEquals(tutor.getEmail(), student.getAttachedTutor().getEmail());
    }
}
