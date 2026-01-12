package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRequest;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final static int LENGTH = 5;

    public List<Student> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(entity -> mapToDto(entity))
                .collect(Collectors.toList());
    }

    public Student addStudent(StudentRequest request) {

        boolean exists = studentRepository.existsByFullNameAndDob(request.getFullName(),
                request.getDob());

        if (exists) {
            throw new RuntimeException("Data already exists");
        }

        StudentEntity entity = new StudentEntity();
        entity.setNim(generateNIM());
        entity.setFullName(request.getFullName());
        entity.setDob(request.getDob());
        entity.setAddress(request.getAddress());
        entity.setEmail(request.getEmail());

        StudentEntity savedEntity = studentRepository.save(entity);
        return mapToDto(savedEntity);

    }

    private String generateNIM() {
        String maxNim = studentRepository.findMaxNim();
        return (maxNim == null) ? String.format("%0" + LENGTH + "d", 1)
                : String.format("%0" + LENGTH + "d", Integer.parseInt(maxNim) + 1);
    }

    private Student mapToDto(StudentEntity entity) {
        return new Student(entity.getNim(), entity.getFullName(), entity.getDob(), entity.getAddress(),entity.getEmail());
    }

    public void deleteStudent(String nim) {
        StudentEntity entity = getStudentByNim(nim);
        studentRepository.delete(entity);

    }

    public Student updateStudent(String nim, StudentRequest request) {
        StudentEntity entity = getStudentByNim(nim);
        entity.setFullName(request.getFullName());
        entity.setDob(request.getDob());
        entity.setAddress(request.getAddress());
        entity.setEmail(request.getEmail());
        studentRepository.save(entity);
        return mapToDto(entity);

    }

    private StudentEntity getStudentByNim(String nim) {
        return studentRepository.findByNim(nim)
                .orElseThrow(() -> new RuntimeException("Student with NIM " + nim + " not found"));
    }

    public Student findStudent(String nim) {
        StudentEntity entity = getStudentByNim(nim);
        return mapToDto(entity);
    }

    private StudentEntity getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student with email " + email + " not found"));
    }

    public Student findStudentByEmail(String email) {
        StudentEntity entity = getStudentByEmail(email);
        return mapToDto(entity);
    }
}
