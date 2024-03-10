package com.example.demo.domain;

import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter

@Entity
@Table(name = "question")
public class Question {

    /**
     * Уникальный номер вопроса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private long id;

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
    @JoinColumn(name = "Student_ID")
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
    private boolean completed;

    /**
     * Является ли вопрос непонятным(Отдельный сценарий)
     */
    @Setter
    private boolean isUnclear;

    /**
     * Оценка за вопрос
     */
    @Column(name = "grade")
    private int grade;

    public void setGrade(int grade) {
        if (grade >= 1 && grade <= 5){
            this.grade = grade;
        }
    }
}
