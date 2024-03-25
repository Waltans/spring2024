package com.example.demo.extern.assemblers;

import com.example.demo.app.service.QuestionService;
import com.example.demo.domain.Question;
import com.example.demo.domain.users.Student;
import com.example.demo.extern.DTO.StudentDTO;
import com.example.demo.extern.controllers.StudentController;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentAssembler extends RepresentationModelAssemblerSupport<Student, StudentDTO> {

    @Autowired
    public final QuestionService questionService;
    public StudentAssembler(QuestionService questionService) {
        super(StudentController.class, StudentDTO.class);
        this.questionService = questionService;
    }

    @Override
    public StudentDTO toModel(@NonNull Student student) {
        StudentDTO studentDTO = instantiateModel(student);
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setRole(student.getRole());
        studentDTO.setAttachedTutor(student.getAttachedTutor());
        studentDTO.setAssignedTasks(student.getAssignedTasks().stream()
                .map(Question::getId).collect(Collectors.toList()));
        studentDTO.setCompletedTasks(student.getCompletedTasks().stream()
                .map(Question::getId).collect(Collectors.toList()));
        return studentDTO;
    }

    public Student toEntity(@NonNull StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAttachedTutor(studentDTO.getAttachedTutor());

        List<Question> assignedTasks = studentDTO.getAssignedTasks().stream()
            .map(id -> {
                if (questionService.getQuestionById(id).isPresent()){
                    return questionService.getQuestionById(id).get();
                }
                return null;
            })
            .collect(Collectors.toList());
    student.setAssignedTasks(assignedTasks);

        List<Question> completedTasks = studentDTO.getCompletedTasks().stream()
            .map(id -> {
                if (questionService.getQuestionById(id).isPresent()){
                    return questionService.getQuestionById(id).get();
                }
                return null;
            })
            .collect(Collectors.toList());

        student.setCompletedTasks(completedTasks);
        return student;
    }

}
