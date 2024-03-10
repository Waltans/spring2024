package com.example.demo.extern.repositories;

import com.example.demo.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User deleteById(long id);
    User findByEmail(String email);
}
