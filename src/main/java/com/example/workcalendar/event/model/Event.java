package com.example.workcalendar.event.model;

import com.example.workcalendar.user.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "start_date", nullable = false, columnDefinition = "TIMESTAMP")
    LocalDateTime start;

    @Column(name = "end_date", nullable = false, columnDefinition = "TIMESTAMP")
    LocalDateTime end;

    @JoinColumn(nullable = false)
    String location;

    @Column(nullable = false)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    User initiator;
}
