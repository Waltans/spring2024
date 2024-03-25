package com.example.demo.domain;

import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 Класс, который представляет собой вопрос, заданный ученику
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "questions")
public class Question {

    /**
     * Уникальный номер вопроса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Репетитор, задавший вопрос
     */
    @ManyToOne
    @JoinColumn(name = "tutor_ID")
    @Setter
    private Tutor tutor;

    /**
     * Ученик, выполнявший задание
     **/
    @ManyToOne
    @JoinColumn(name = "student_id")
    @Setter
    private Student student;

    /**
     * Описание вопроса
     */
    @Column(name = "description")
    @Setter
    private String description;

    /**
     * Ответ на вопрос заданный репетитором
     */
    @Column(name = "right_answer")
    @Setter
    private String rightAnswer;

    /**
     * Ответ ученика на вопрос
     */
    @Column(name = "answer")
    @Setter
    private String answer;

    /**
     * Статус выполнения вопроса
     */
    @Setter
    @Column(name = "completed")
    private boolean completed;

    /**
     * Является ли вопрос непонятным
     */
    @Setter
    @Column(name = "unclear")
    private boolean unclear;

    /**
     * Оценка за вопрос, допустимо от 1 до 5
     */
    @Column(name = "grade")
    private int grade;

    /**
     * Оценка за вопрос от 1 до 5, при этом целое число
     * @param grade - оценка
     */
    public void setGrade(int grade) {
        if (grade >= 1 && grade <= 5){
            this.grade = grade;
        }
    }
}
