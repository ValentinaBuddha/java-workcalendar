package com.example.workcalendar.calendar.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Entity
@Table(name = "calendar")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Day {

    @Id
    @Column(nullable = false, columnDefinition = "DATE")
    LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    DayStatus status;

    String description;
}
