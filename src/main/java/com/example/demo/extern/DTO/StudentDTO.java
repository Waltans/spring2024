package com.example.demo.extern.DTO;

import com.example.demo.domain.users.Tutor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class StudentDTO extends RepresentationModel<StudentDTO> {

    private Long id;
    private String name;
    private String email;
    private String role;
    private Tutor attachedTutor;
    private List<Long> assignedTasks;
    private List<Long> completedTasks;
}
