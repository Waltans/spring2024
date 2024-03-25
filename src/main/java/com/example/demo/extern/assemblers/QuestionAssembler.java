package com.example.demo.extern.assemblers;

import com.example.demo.domain.Question;
import com.example.demo.domain.users.Tutor;
import com.example.demo.extern.DTO.QuestionDTO;
import com.example.demo.app.service.TutorService;
import com.example.demo.extern.controllers.QuestionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class QuestionAssembler extends RepresentationModelAssemblerSupport<Question, QuestionDTO> {

    @Autowired
    private TutorService tutorService;

    public QuestionAssembler() {
        super(QuestionController.class, QuestionDTO.class);
    }

    @Override
    public QuestionDTO toModel(Question question) {
        QuestionDTO questionDTO = instantiateModel(question);
        questionDTO.setId(question.getId());
        questionDTO.setTutor(question.getTutor());
        questionDTO.setStudent(question.getStudent());
        questionDTO.setDescription(question.getDescription());
        questionDTO.setRightAnswer(question.getRightAnswer());
        questionDTO.setAnswer(question.getAnswer());
        questionDTO.setCompleted(question.isCompleted());
        questionDTO.setUnclear(question.isUnclear());
        questionDTO.setGrade(question.getGrade());
        return questionDTO;
    }

    public Question toEntity(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setTutor(getTutorFromDTO(questionDTO));
        question.setStudent(questionDTO.getStudent());
        question.setDescription(questionDTO.getDescription());
        question.setRightAnswer(questionDTO.getRightAnswer());
        question.setAnswer(questionDTO.getAnswer());
        question.setCompleted(questionDTO.isCompleted());
        question.setUnclear(questionDTO.isUnclear());
        question.setGrade(questionDTO.getGrade());
        return question;
    }

    private Tutor getTutorFromDTO(QuestionDTO questionDTO) {
        if (questionDTO.getTutor() != null) {
            return tutorService.getTutorById(questionDTO.getTutor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Неверный репетитор с  ID: " + questionDTO.getTutor().getId()));
        }
        return null;
    }
}
