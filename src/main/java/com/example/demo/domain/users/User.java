package com.example.demo.domain.users;

import jakarta.persistence.*;
import lombok.*;

/**
 * Абстрактный класс для создания пользователей.
 * Содержит поля Имени, id, email и роли пользователя
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@Entity
@DiscriminatorColumn(name = "role")
public abstract class User {

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

    /**
     * Роль пользователя (Student, Tutor)
     */
    @Column(name = "role",insertable = false, updatable=false)
    private String role;
}
