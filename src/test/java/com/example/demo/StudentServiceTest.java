package com.example.demo;

import com.example.demo.app.repositories.StudentRepository;
import com.example.demo.app.repositories.TutorRepository;
import com.example.demo.app.service.QuestionService;
import com.example.demo.app.service.StudentService;
import com.example.demo.app.service.TutorService;
import com.example.demo.domain.Question;
import com.example.demo.domain.users.Student;
import com.example.demo.domain.users.Tutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class StudentServiceTest {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TutorRepository tutorRepository;


    @Test
    void addStudentTest() {
        Student student = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        studentService.addStudent(student);
        studentService.addStudent(student2);
        studentService.addStudent(student3);
        assertTrue(studentRepository.existsById(student.getId()));
        assertTrue(studentRepository.existsById(student2.getId()));
        assertTrue(studentRepository.existsById(student3.getId()));
    }

    @Test
    void deleteStudentByIdTest() {
        Student student = new Student();
        studentService.addStudent(student);
        studentService.deleteStudent(student.getId());
        assertFalse(studentRepository.existsById(student.getId()));
    }

    @Test
    void deleteStudentTest() {
        Student student = new Student();
        studentService.addStudent(student);
        studentService.deleteStudent(student);
        assertFalse(studentRepository.existsById(student.getId()));
    }

    @Test
    void updateEmailStudentTest() {
        Student student = new Student();
        String newEmail = "pashas@example.com";
        studentService.addStudent(student);
        studentService.updateStudentEmail(student.getId(), newEmail);
        Student studentWithNewEmail = studentService.getStudentById(student.getId()).get();
        assertEquals(studentWithNewEmail.getEmail(), newEmail);
    }

    @Test
    void getStudentByIdTest() {
        Student student = new Student();

        studentService.addStudent(student);
        assertEquals(student.getId(), studentService.getStudentById(student.getId()).get().getId());
    }

    @Test
    void addTutorToStudentTest() {
        Student student = new Student();
        studentRepository.save(student);

        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);

        studentService.addTutorToStudent(student.getId(), tutor.getId());

        student = studentService.getStudentById(student.getId()).get();

        assertEquals(tutor.getEmail(), student.getAttachedTutor().getEmail());
    }

    @Test
    void testShowAllQuestionToComplete() {
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        Question question1 = new Question();
        question1.setDescription("Question 1");
        questionService.addQuestion(question1, tutor.getId());


        Question question2 = new Question();
        question2.setDescription("Question 2");
        questionService.addQuestion(question2, tutor.getId());

        Student student = new Student();
        student.setAssignedTasks(Arrays.asList(question1, question2));
        studentService.addStudent(student);

        List<Question> result = studentService.showAllQuestionToComplete(student.getId());

        assertNotNull(result);
        assertEquals(student.getAssignedTasks().size(), result.size());
        assertTrue(result.contains(question1));
        assertTrue(result.contains(question2));
    }

    @Test
    void testShowAllQuestionToCompleteWhenStudentNotFound() {
        Student student = new Student();
        studentService.addStudent(student);
        Long studentId = student.getId() + 1;

        List<Question> result = studentService.showAllQuestionToComplete(studentId);

        assertNull(result);
    }

    @Test
    public void testSetAnswerForQuestion() {
        Student student = new Student();
        studentService.addStudent(student);

        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        log.info(tutor.getRole());
        tutorRepository.save(tutor);
        log.info(tutor.getRole());
        Question question = new Question();
        question.setDescription("Test question");
        question.setStudent(student);
        log.info(tutor.getRole());
        questionService.addQuestion(question, tutor.getId());

        Long questionId = question.getId();
        Long studentId = student.getId();
        String answer = "Test answer";

        studentService.setAnswerForQuestion(questionId, studentId, answer);

        Question updatedQuestion = questionService.getQuestionById(questionId).orElse(null);
        assertNotNull(updatedQuestion);
        assertEquals(answer, updatedQuestion.getAnswer());
        assertTrue(updatedQuestion.isCompleted());
    }

    @Test
    public void testSetAnswerForQuestionWithWrongStudentId() {
        Student student = new Student();
        studentService.addStudent(student);

        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);

        Question question = new Question();
        question.setDescription("Test question");
        question.setStudent(student);
        questionService.addQuestion(question, tutorRepository.findById(tutor.getId()).get().getId());

        Long questionId = question.getId();
        Long wrongStudentId = student.getId() + 1;
        String answer = "Test answer";

        studentService.setAnswerForQuestion(questionId, wrongStudentId, answer);

        Question updatedQuestion = questionService.getQuestionById(questionId).orElse(null);
        assertNotNull(updatedQuestion);
        assertNull(updatedQuestion.getAnswer());
        assertFalse(updatedQuestion.isCompleted());
    }


    @Test
    public void IsQuestionUnclearTest() {
        Student student = new Student();
        studentService.addStudent(student);
        Question question = new Question();
        Tutor tutor = new Tutor();
        tutorService.addTutor(tutor);
        questionService.addQuestion(question, tutor.getId());

        studentService.IsQuestionUnclear(question.getId(), student.getId(), true);
        log.info("{}", student.getId());

        assertTrue(questionService.getQuestionById(question.getId()).get().isUnclear());
    }
}
