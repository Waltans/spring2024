package com.example.demo.domain.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Абстрактный класс для создания пользователей.
 * Содержит поля Имени, id, email и роли пользователя(дефолтное значение: "ученик")
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter


@MappedSuperclass
public class User {

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /**
     * Уникальный номер пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "role")
    private String role;
}
