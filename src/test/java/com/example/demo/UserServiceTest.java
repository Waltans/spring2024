package com.example.demo;

import com.example.demo.app.service.UserService;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.users.Administrator;
import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import com.example.demo.extern.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;


    @Test
    void createStudent() {
        Student student = new Student();
        student.setName("vasya");
        student.setEmail("test@123.ru");
        student.setAttachedTutor(new Tutor());

        userService.createStudent(student);

        verify(userRepository, times(1)).save(student);
    }

    @Test
    void createTutor() {
        Tutor tutor = new Tutor();
        tutor.setSubject("algebra");
        tutor.setName("kolya");
        tutor.setSchedule(new Schedule());

        userService.createTutor(tutor);

        verify(userRepository, times(1)).save(tutor);
    }

    @Test
    void createAdministrator() {
        Administrator administrator = new Administrator();
        administrator.setName("admin");
        administrator.setEmail("admin@admin.ru");

        userService.createAdministrator(administrator);

        verify(userRepository, times(1)).save(administrator);
    }
}
