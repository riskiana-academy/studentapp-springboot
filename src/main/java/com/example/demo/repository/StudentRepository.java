package com.example.demo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    @Query("SELECT MAX(s.nim) FROM StudentEntity s")
    String findMaxNim();

    Optional<StudentEntity> findByNim(@Param("nim") String nim);

    boolean existsByFullNameAndDob(@Param("fullName") String fullName, @Param("dob") LocalDate dob);



}
