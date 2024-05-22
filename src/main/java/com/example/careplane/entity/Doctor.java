package com.example.careplane.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
    private String login;
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
}
