package com.example.workcalendar.request;

import com.example.workcalendar.event.model.Event;
import com.example.workcalendar.event.repository.EventRepository;
import com.example.workcalendar.request.model.Request;
import com.example.workcalendar.request.model.RequestStatus;
import com.example.workcalendar.request.repository.RequestRepository;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class RequestRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RequestRepository requestRepository;

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
    private final LocalDate date = LocalDate.of(2024, 12, 9);
    private final LocalTime start = LocalTime.of(13, 0, 0);
    private final Event event = new Event(
            null,
            "consultation",
            date,
            start,
            start.plusHours(1),
            "office 25",
            "consultation about Spring Framework",
            initiator);
    private final Request request = new Request(1L, initiator, event, participant, RequestStatus.PENDING);

    @BeforeEach
    void setUp() {
        userRepository.save(initiator);
        userRepository.save(participant);
        eventRepository.save(event);
        requestRepository.save(request);
    }

    @Test
    @DirtiesContext
    void findAllByEventId() {
        List<Request> requests = requestRepository.findAllByEventId(1L, Pageable.ofSize(10));

        assertThat(requests.get(0).getId(), equalTo(request.getId()));
        assertThat(requests.size(), equalTo(1));
    }
}