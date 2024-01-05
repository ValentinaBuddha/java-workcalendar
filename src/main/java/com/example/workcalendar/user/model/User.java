package com.example.workcalendar.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String surname;

    @Column(nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String position;

    @Column(nullable = false)
    String department;
}
