package com.example.careplane.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("Admin")
@Table(name = "admin")
public class Admin extends User {

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
