package com.example.demo.app.service;

import com.example.demo.domain.Schedule;
import com.example.demo.domain.users.Tutor;
import com.example.demo.app.repositories.ScheduleRepository;
import com.example.demo.app.repositories.TutorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;


@Service
@Slf4j
public class TutorService {
    private final TutorRepository tutorRepository;

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository, ScheduleRepository scheduleRepository) {
        this.tutorRepository = tutorRepository;
        this.scheduleRepository = scheduleRepository;
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
        tutorRepository.delete(getTutorById(tutorId));
        log.info("Из базы данных удален репетитор с id = {}", tutorId);
    }

    /**
     * Метод для поиска репетитора по id
     *
     * @param id - id репетитора по которому мы будем искать
     * @return репетитора с переданным id
     */
    public Tutor getTutorById(long id) {
        Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
            log.error("репетитор с id = {} не найден", id);
            return new RuntimeException(MessageFormat.format("репетитор с id = {0} не найден", id));
        });
        log.info("репетитор с id = {} найден", id);
        return tutor;
    }

    /**
     * Метод позволяет обновить почту репетитора
     *
     * @param tutorId - id репетитора
     * @param email   - почта, на которую студент меняет существующую
     */
    public void updateEmailTutor(Long tutorId, String email) {
        Tutor tutor = getTutorById(tutorId);
        tutor.setEmail(email);
        addTutor(tutor);
        log.info("репетитор с id = {} изменил посту с {} на {}", tutor.getId(), tutor.getEmail(), email);
    }

    /**
     * Метод для обновления расписания
     *
     * @param tutorId      - id репетитора
     * @param scheduleList - новое расписание репетитора
     */
    public void assignScheduleToTutor(Long tutorId, List<Schedule> scheduleList) {
        Tutor tutor = getTutorById(tutorId);
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
