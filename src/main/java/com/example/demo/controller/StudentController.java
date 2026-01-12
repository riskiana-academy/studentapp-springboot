package com.example.demo.controller;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRequest;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @PostMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequest studentRequest,
            BindingResult bindingResult) {
        ResponseEntity<?> errorResponse = validateRequest(bindingResult);
        if (errorResponse != null)
            return errorResponse;
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.addStudent(studentRequest));

    }

    @DeleteMapping("/{nim}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeStudent(@PathVariable String nim) {
        studentService.deleteStudent(nim);
        return ResponseEntity.ok("Successfully deleted"); 

    }

    @PutMapping("/{nim}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStudent(@PathVariable String nim, @RequestBody StudentRequest studentRequest) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.updateStudent(nim, studentRequest));

    }

    @GetMapping("/{nim}")
    public ResponseEntity<?> findStudent(@PathVariable String nim) {

        return ResponseEntity.ok(studentService.findStudent(nim));

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findStudentByEmail(@PathVariable String email) {

        return ResponseEntity.ok(studentService.findStudentByEmail(email));

    }

    private ResponseEntity<?> validateRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors()
                    .forEach(error -> {
                        errors.put(error.getField(), error.getDefaultMessage());
                    });
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Validation failed");
            response.put("errors", errors);
            return ResponseEntity.badRequest().body(response);

        }
        return null;

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}