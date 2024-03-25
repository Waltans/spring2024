package com.example.demo.extern.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class TutorDTO extends RepresentationModel<TutorDTO> {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String subject;
    private List<Long> students;
    private List<Long> schedule;
    private List<Long> questions;
}
