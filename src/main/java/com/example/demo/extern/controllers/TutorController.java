package com.example.demo.extern.controllers;

import com.example.demo.app.service.TutorService;
import com.example.demo.domain.users.Tutor;
import com.example.demo.extern.DTO.TutorDTO;
import com.example.demo.extern.assemblers.TutorAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tutors")
public class TutorController {

    private final TutorService tutorService;
    private final TutorAssembler tutorAssembler;

    @Autowired
    public TutorController(TutorService tutorService, TutorAssembler tutorAssembler) {
        this.tutorService = tutorService;
        this.tutorAssembler = tutorAssembler;
    }

    @PostMapping
    public ResponseEntity<TutorDTO> createStudent(@RequestBody TutorDTO tutorDTO) {
        Tutor tutor = tutorAssembler.toEntity(tutorDTO);
        tutorService.addTutor(tutor);
        return new ResponseEntity<>(tutorAssembler.toModel(tutor), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> getTutorById(@PathVariable Long id) {
        Optional<Tutor> tutor = tutorService.getTutorById(id);
        if (tutor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tutorAssembler.toModel(tutor.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        tutorService.deleteTutor(id);
        return ResponseEntity.noContent().build();
    }
}
