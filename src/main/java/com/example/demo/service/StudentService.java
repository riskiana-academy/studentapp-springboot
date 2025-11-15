package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRequest;

@Service
public class StudentService {

    private final static int LENGTH = 5;
    List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        /*
         * Student s1 = new Student();
         * s1.setNim("11111");
         * s1.setDob(LocalDate.of(1992, 10, 19));
         * s1.setFullName("Monkey D.Luffy");
         * s1.setAddress("Jakarta");
         * 
         * Student s2 = new Student();
         * s2.setNim("22222");
         * s2.setDob(LocalDate.of(1993, 10, 19));
         * s2.setFullName("Roronoa Zoro");
         * s2.setAddress("Bandung");
         * 
         * Student s3 = new Student();
         * s3.setNim("33333");
         * s3.setDob(LocalDate.of(1993, 10, 19));
         * s3.setFullName("Dr.Chopper");
         * s3.setAddress("Surabaya");
         * 
         * return List.of(s1, s2, s3);
         */
        return students;
    }

    public Student addStudent(StudentRequest request) {
        Student savedStudent = new Student();
        savedStudent.setNim(generateNIM());
        savedStudent.setFullName(request.getFullName());
        savedStudent.setDob(request.getDob());
        savedStudent.setAddress(request.getAddress());

        students.add(savedStudent);
        return savedStudent;
    }

    private String generateNIM() {
        int maxId = students.size() + 1;
        return String.format("%0" + LENGTH + "d", maxId);
    }

    public void deleteStudent(String nim) {
        Optional<Student> studentOptional = students.stream()
                .filter(student -> student.getNim().equals(nim))
                .findFirst();

        if (studentOptional.isPresent()) {
            Student studentToBeDeleted = studentOptional.get();
            students.remove(studentToBeDeleted);
        } else {
            throw new RuntimeException("Student with nim " + nim + " not found");

        }

    }

    public Student updateStudent(String nim, StudentRequest request) {
        Optional<Student> studentOptional = students.stream()
                .filter(student -> student.getNim().equals(nim))
                .findFirst();
        if (studentOptional.isPresent()) {
            Student updatedStudent = studentOptional.get();
            updatedStudent.setFullName(request.getFullName());
            updatedStudent.setDob(request.getDob());
            updatedStudent.setAddress(request.getAddress());
            return updatedStudent;
        } else {
            throw new RuntimeException("Student with nim " + nim + " not found");

        }

    }

    public Student findStudent(String nim) {
         Optional<Student> studentOptional = students.stream()
                .filter(student -> student.getNim().equals(nim))
                .findFirst();
            
        if(studentOptional.isPresent()){
            return studentOptional.get();
        } else{
            throw new RuntimeException("Student with nim " + nim + " not found");
        }
    }

}
