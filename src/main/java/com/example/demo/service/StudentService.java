package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRequest;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;

import jakarta.transaction.Transactional;
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

    @Transactional
    public Student addStudent(StudentRequest request) {
        StudentEntity entity = new StudentEntity();
        entity.setNim(generateNIM());
        entity.setFullName(request.getFullName());
        entity.setDob(request.getDob());
        entity.setAddress(request.getAddress());

        StudentEntity savedEntity = studentRepository.save(entity);
        return mapToDto(savedEntity);
    }

    private String generateNIM() {
        String maxNim = studentRepository.findMaxNim();
        return (maxNim == null) ? String.format("%0" + LENGTH + "d", 1)
                : String.format("%0" + LENGTH + "d", Integer.parseInt(maxNim) + 1);
    }

    private Student mapToDto(StudentEntity entity) {
        Student student = new Student();
        student.setNim(entity.getNim());
        student.setFullName(entity.getFullName());
        student.setAddress(entity.getAddress());
        student.setDob(entity.getDob());
        return student;
    }

    public void deleteStudent(String nim) {
        Optional<StudentEntity> studentOptional = studentRepository.findByNim(nim);

        if (studentOptional.isPresent()) {
            StudentEntity studentToBeDeleted = studentOptional.get();
            studentRepository.delete(studentToBeDeleted);
        } else {
            throw new RuntimeException("Student with nim " + nim + " not found");

        }

    }

    public Student updateStudent(String nim, StudentRequest request) {
        Optional<StudentEntity> studentOptional = studentRepository.findByNim(nim);
        if (studentOptional.isPresent()) {
            StudentEntity updatedStudent = studentOptional.get();
            updatedStudent.setFullName(request.getFullName());
            updatedStudent.setDob(request.getDob());
            updatedStudent.setAddress(request.getAddress());
            studentRepository.save(updatedStudent);
            return mapToDto(updatedStudent);
        } else {
            throw new RuntimeException("Student with nim " + nim + " not found");

        }

    }

    public Student findStudent(String nim) {
        Optional<StudentEntity> studentOptional = studentRepository.findByNim(nim);

        if (studentOptional.isPresent()) {
            return mapToDto(studentOptional.get());
        } else {
            throw new RuntimeException("Student with nim " + nim + " not found");
        }
    }

}
