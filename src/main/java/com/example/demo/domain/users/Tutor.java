package com.example.demo.domain.users;

import com.example.demo.domain.Question;
import com.example.demo.domain.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Репетитор
 * содержит поля id унаследованного от user, предмета преподавания, список прикрепленных студентов,
 * расписание репетитора, вопросы созданные репетитором.
 */
@Getter
@NoArgsConstructor
@Entity
@Setter
@DiscriminatorValue("Tutor")
public class Tutor extends User {
    /**
     * Предмет, который преподает репетитор
     */
    private String subject;
    /**
     * Список прикрепленных студентов
     */

    @OneToMany(mappedBy = "attachedTutor")
    private List<Student> students;
    /**
     * Расписание репетитора
     */
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Schedule> schedule = new ArrayList<>();
    /**
     * Вопросы созданные репетитором
     */
    @OneToMany(mappedBy = "tutor")
    private List<Question> questions;

    public Tutor(String name, String email, String subject, List<Schedule> schedule) {
        super(name, email, "Tutor");
        this.subject = subject;
        this.schedule = schedule;
    }

}
