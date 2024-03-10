package com.example.demo.domain.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@Entity
@Table(name = "users")

public abstract class User {

    /**
     * Уникальный номер пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    /**
     * Имя пользователя
     */
    @Setter
    @Column(name = "name")
    private String name;

    /**
     * Адрес эл. почты
     */
    @Setter
    @Column(name = "email")
    private String email;

    @Column(name = "Role")
    private String role = "Student";
}
