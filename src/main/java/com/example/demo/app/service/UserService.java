package com.example.demo.app.service;

import com.example.demo.domain.users.Administrator;
import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import com.example.demo.extern.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Создание студента и сохраняет его в БД
     * @param student - объект студента для создания
     */
    public void createStudent(Student student) {
        userRepository.save(student);
        log.info("Создан пользователь Студент c Id {} ", student.getId());
    }

    /**
     * Создание репетитора и сохраняет его в БД
     * @param tutor - объект репетитора для создания
     **/
    public void createTutor(Tutor tutor) {
        userRepository.save(tutor);
        log.info("Создан пользователь Репетитор c Id {} ", tutor.getId());

    }

    /**
     * Создание администратора и сохраняет его в БД
     * @param administrator - объект администратора для создания
     */
    public void createAdministrator(Administrator administrator) {
        userRepository.save(administrator);
        log.info("Создан пользователь Администратор c Id {} ", administrator.getId());

    }
}
