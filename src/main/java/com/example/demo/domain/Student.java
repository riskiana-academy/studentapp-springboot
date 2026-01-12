package com.example.demo.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Student {
    private String nim;
    private String fullName;
    private LocalDate dob;
    private String address;
    private String email;
    
        
    
}
