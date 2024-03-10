package com.example.demo.extern.repositories;

import com.example.demo.domain.Question;
import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findById(long id);
    Question findByStudent(Student student);
    Question findByTutor(Tutor tutor);

}
