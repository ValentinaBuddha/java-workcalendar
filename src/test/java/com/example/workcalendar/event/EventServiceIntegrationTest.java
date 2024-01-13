package com.example.workcalendar.event;

import com.example.workcalendar.event.dto.NewEventDto;
import com.example.workcalendar.event.model.Event;
import com.example.workcalendar.event.service.EventService;
import com.example.workcalendar.user.dto.UserDto;
import com.example.workcalendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(
        properties = "spring.datasource.username=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EventServiceIntegrationTest {

    private final EntityManager em;
    private final EventService eventService;
    private final UserService userService;

    @Test
    void addEvent() {
        UserDto userDto = new UserDto(
                1L,
                "Ivanov",
                "Ivan",
                "user@mail.ru",
                "admin",
                "IT");
        LocalDate date = LocalDate.of(2024, 12, 9);
        LocalTime start = LocalTime.of(13, 0, 0);
        NewEventDto newEventDto = new NewEventDto(
                "consultation",
                date,
                start,
                start.plusHours(1),
                "office 25",
                "consultation about Spring Framework");

        UserDto user = userService.addUser(userDto);
        eventService.addEvent(user.getId(), newEventDto);

        TypedQuery<Event> query = em.createQuery("Select e from Event e where e.title like :titleEvent", Event.class);
        Event event = query.setParameter("titleEvent", newEventDto.getTitle()).getSingleResult();

        assertThat(event.getId(), notNullValue());
        assertThat(event.getTitle(), equalTo(newEventDto.getTitle()));
        assertThat(event.getStart(), equalTo(newEventDto.getStart()));
    }
}