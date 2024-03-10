package com.example.demo.domain.users;

import com.example.demo.domain.Schedule;
import jakarta.persistence.*;import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

@Entity
@Table(name = "Tutor")
public class Tutor extends User {
    public Tutor(long id, String name, String email, String subject, Schedule schedule) {
        super(id, name, email, "Tutor");
        this.subject = subject;
        this.schedule = schedule;
    }

    /**
     * Предмет, который преподает репетитор
     */
    @Column(name = "Subject")
    private String subject;

    /**
     * Расписание репетитора
     */
    @Column(name = "Schedule")
    private Schedule schedule;

}
