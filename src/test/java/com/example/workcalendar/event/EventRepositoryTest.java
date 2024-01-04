package com.example.workcalendar.event;

import com.example.workcalendar.event.model.Event;
import com.example.workcalendar.event.repository.EventRepository;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    private final User initiator = new User(
            null,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT");
    private final User participant = new User(
            null,
            "Petrov",
            "Petr",
            "user2@mail.ru",
            "dev",
            "IT");
    private final Event event = new Event(
            null,
            "consultation",
            LocalDateTime.of(2024, 1, 25, 13, 0, 0),
            LocalDateTime.of(2024, 1, 25, 14, 0, 0),
            "office 25",
            "consultation about Spring Framework",
            initiator);

    @BeforeEach
    void setUp() {
        userRepository.save(initiator);
        userRepository.save(participant);
        eventRepository.save(event);
    }

    @Test
    @DirtiesContext
    void findAllByInitiatorId() {
        List<Event> events = eventRepository.findAllByInitiatorId(1L, Pageable.ofSize(10));

        assertThat(events.get(0).getId(), equalTo(event.getId()));
        assertThat(events.size(), equalTo(1));
    }
}