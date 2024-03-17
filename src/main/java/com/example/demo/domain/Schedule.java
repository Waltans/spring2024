package com.example.demo.domain;


import com.example.demo.domain.users.Tutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


/**
 * Класс расписания репетитора
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID тьютора, которому принадлежит расписание
     */
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    /**
     * Дата начала возможного занятия
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /**
     * Дата окончания возможного занятия
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;
}
