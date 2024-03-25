package com.example.demo.extern.controllers;

import com.example.demo.app.service.QuestionService;
import com.example.demo.domain.Question;
import com.example.demo.extern.DTO.QuestionDTO;
import com.example.demo.extern.assemblers.QuestionAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionAssembler questionAssembler;

    public QuestionController(QuestionService questionService, QuestionAssembler questionAssembler) {
        this.questionService = questionService;
        this.questionAssembler = questionAssembler;
    }


    @PostMapping("/{tutorId}")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable Long tutorId) {
        Question question = questionAssembler.toEntity(questionDTO);
        questionService.addQuestion(question, tutorId);
        return ResponseEntity.ok(questionAssembler.toModel(question));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionService.getQuestionById(id);
        if (question.isPresent())
            return ResponseEntity.ok(questionAssembler.toModel(question.get()));

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

}
