package com.example.demo.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class StudentRequest {
  @NotBlank(message= " Full name is required")
  private String fullName;

  @NotNull(message = "Date of Birth is required")
  private LocalDate dob;
  
  private String address;

  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  public LocalDate getDob() {
    return dob;
  }
  public void setDob(LocalDate dob) {
    this.dob = dob;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }

  @NotBlank(message= " Email is required")
  private String email;

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

}
