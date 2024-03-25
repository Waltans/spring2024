package com.example.demo.extern.controllers;

import com.example.demo.app.service.StudentService;
import com.example.demo.domain.users.Student;
import com.example.demo.extern.DTO.StudentDTO;
import com.example.demo.extern.assemblers.StudentAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentAssembler studentAssembler;


    public StudentController(StudentService studentService, StudentAssembler studentAssembler) {
        this.studentService = studentService;
        this.studentAssembler = studentAssembler;

    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = studentAssembler.toEntity(studentDTO);
        studentService.addStudent(student);
        return new ResponseEntity<>(studentAssembler.toModel(student), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            StudentDTO studentDTO = studentAssembler.toModel(student.get());
            return ResponseEntity.ok(studentDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studentService.getStudentById(id).isPresent()) {
            studentService.deleteStudent(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
