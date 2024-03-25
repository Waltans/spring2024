package com.example.demo.extern.assemblers;

import com.example.demo.domain.Question;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import com.example.demo.extern.DTO.TutorDTO;
import com.example.demo.app.service.ScheduleService;
import com.example.demo.app.service.StudentService;
import com.example.demo.app.service.QuestionService;
import com.example.demo.extern.controllers.TutorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TutorAssembler extends RepresentationModelAssemblerSupport<Tutor, TutorDTO> {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private QuestionService questionService;

    public TutorAssembler() {
        super(TutorController.class, TutorDTO.class);
    }

    @Override
    public TutorDTO toModel(Tutor tutor) {
        TutorDTO tutorDTO = instantiateModel(tutor);
        tutorDTO.setId(tutor.getId());
        tutorDTO.setName(tutor.getName());
        tutorDTO.setStudents(tutor.getStudents().stream()
                .map(Student::getId).collect(Collectors.toList()));
        tutorDTO.setRole(tutor.getRole());
        tutorDTO.setSubject(tutor.getSubject());
        tutorDTO.setEmail(tutor.getEmail());
        tutorDTO.setSchedule(tutor.getSchedule().stream()
                .map(Schedule::getId).collect(Collectors.toList()));
        tutorDTO.setQuestions(tutor.getQuestions().stream()
                .map(Question::getId).collect(Collectors.toList()));
        return tutorDTO;
    }

    public Tutor toEntity(TutorDTO tutorDTO) {
        Tutor tutor = new Tutor();
        tutor.setName(tutorDTO.getName());
        tutor.setSubject(tutorDTO.getSubject());
        tutor.setEmail(tutorDTO.getEmail());

        tutorDTO.getStudents().forEach(studentId -> {
            Student student = studentService.getStudentById(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("Неверное ID ученика: " + studentId));
            tutor.getStudents().add(student);
        });

        tutorDTO.getSchedule().forEach(scheduleId -> {
            Schedule schedule = scheduleService.getById(scheduleId)
                    .orElseThrow(() -> new IllegalArgumentException("Неверный ID расписания: " + scheduleId));
            tutor.getSchedule().add(schedule);
        });

        tutorDTO.getQuestions().forEach(questionId -> {
            Question question = questionService.getQuestionById(questionId)
                    .orElseThrow(() -> new IllegalArgumentException("Неверный ID вопроса: " + questionId));
            tutor.getQuestions().add(question);
        });

        return tutor;
    }
}
