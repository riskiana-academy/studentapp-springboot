package com.example.demo.controller;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequest studentRequest,
            BindingResult bindingResult) {
        ResponseEntity<?> errorResponse = validateRequest(bindingResult);
        if (errorResponse != null)
            return errorResponse;
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.addStudent(studentRequest));

    }

    @DeleteMapping("/{nim}")
    public String removeStudent(@PathVariable String nim) {
        try {
            studentService.deleteStudent(nim);
            return "Successfully deleted";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @PutMapping("/{nim}")
    public Object updateStudent(@PathVariable String nim, @RequestBody StudentRequest studentRequest) {
        try {
            return studentService.updateStudent(nim, studentRequest);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @GetMapping("/{nim}")
    public Object findStudent(@PathVariable String nim) {
        try {
            return studentService.findStudent(nim);

        } catch (Exception e) {
            return e.getMessage();

        }

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