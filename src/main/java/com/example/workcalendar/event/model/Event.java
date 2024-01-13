package com.example.workcalendar.event.model;

import com.example.workcalendar.user.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(name = "date", nullable = false, columnDefinition = "DATE")
    LocalDate date;

    @Column(name = "start_time", nullable = false, columnDefinition = "TIME")
    LocalTime start;

    @Column(name = "end_time", nullable = false, columnDefinition = "TIME")
    LocalTime end;

    @JoinColumn(nullable = false)
    String location;

    @Column(nullable = false)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    User initiator;
}