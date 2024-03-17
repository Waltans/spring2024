//package com.example.demo.app.controller;
//
//import com.example.demo.domain.users.Administrator;
//import com.example.demo.domain.users.Student;
//import com.example.demo.domain.users.Tutor;
//import com.example.demo.app.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Controller
//public class UserController {
//
//    private final StudentService userService;
//
//    @Autowired
//    public UserController(StudentService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/users/student")
//    public void createStudent(@RequestBody Student student) {
//
//        userService.createStudent(student);
//    }
//
//    @PostMapping("/users/tutor")
//    public void createTutor(@RequestBody Tutor tutor) {
//        userService.createTutor(tutor);
//    }
//
//    @PostMapping("/users/administrator")
//    public void createAdministrator(@RequestBody Administrator administrator) {
//        userService.createAdministrator(administrator);
//    }
//}
