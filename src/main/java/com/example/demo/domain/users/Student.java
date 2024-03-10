package com.example.demo.domain.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Student")
public class Student extends User {
    public Student(long id, String name, String email, Tutor attachedTutor)
    {
        super(id, name, email,"Student");
        this.attachedTutor = attachedTutor;
    }

    /**
     * Прикрепленный репетитор к ученику
     */
    @ManyToOne
    @JoinColumn(name = "User_ID")
    private Tutor attachedTutor;
}


//    /**
//     * Список вопросов, которые необходимо выполнить ученику
//     */
//    @OneToMany
//    @JoinColumn(name = "question_id")
//    private List<Question> assignedTasks;
//
//    /**
//     * Вопросы, которые ученик уже сделал
//     */
//    @OneToMany
//    @JoinColumn(name = "question_id")
//    private List<Question> completedTasks;

