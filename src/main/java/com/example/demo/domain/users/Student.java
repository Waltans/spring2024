package com.example.demo.domain.users;

import com.example.demo.domain.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * Класс ученика.
 * Имеет поля id, прикрепленного репетитора, список вопросов и выполненные вопросы
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "students")
@ToString
@Setter
public class Student extends User {
    public Student(String name, String email, Tutor attachedTutor)
    {
        super(name, email,"Student");
        this.attachedTutor = attachedTutor;
    }

    /**
     * Прикрепленный репетитор к ученику
     */
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor attachedTutor;

    /**
     * Список вопросов, которые необходимо выполнить ученику
     */
    @OneToMany(mappedBy = "student")
    private List<Question> assignedTasks;


    /**
     * Вопросы, которые ученик уже сделал
     */
    @OneToMany(mappedBy = "student")
    private List<Question> completedTasks;
}




