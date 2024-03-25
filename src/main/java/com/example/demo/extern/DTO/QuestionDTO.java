package com.example.demo.extern.DTO;

import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;


@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionDTO extends RepresentationModel<QuestionDTO> {

    private Long id;

    private Tutor tutor;

    private Student student;

    private String description;

    private String rightAnswer;

    private String answer;

    private boolean completed;

    private boolean unclear;

    private int grade;

}
