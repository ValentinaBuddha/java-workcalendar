package com.example.workcalendar.event;

import com.example.workcalendar.event.dto.EventFullDto;
import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.event.dto.NewEventDto;
import com.example.workcalendar.event.dto.UpdateEventDto;
import com.example.workcalendar.event.model.Event;
import com.example.workcalendar.event.model.EventMapper;
import com.example.workcalendar.event.repository.EventRepository;
import com.example.workcalendar.event.service.EventServiceImpl;
import com.example.workcalendar.exception.EntityNotFoundException;
import com.example.workcalendar.exception.WrongDatesException;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.repository.UserRepository;
import com.example.workcalendar.user.dto.UserShortDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EventServiceImpl eventService;

    private final Long id = 1L;
    private final LocalDateTime start = LocalDateTime.of(2024, 1, 25, 13, 0, 0);
    private final User user = new User(
            id,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT");
    private final UserShortDto userDto = new UserShortDto(id, "Ivanov Ivan");
    private final Event event = new Event(
            id,
            "consultation",
            start,
            start.plusHours(1),
            "office 25",
            "consultation about Spring Framework",
            user);
    private final NewEventDto newEventDto = new NewEventDto(
            "consultation",
            start,
            start.plusHours(1),
            "office 25",
            "consultation about Spring Framework");
    private final UpdateEventDto updateEventDto = new UpdateEventDto(
            "meeting",
            null,
            null,
            null,
            null);
    private final NewEventDto newEventDtoNoName = new NewEventDto(
            "",
            start,
            start.plusHours(1),
            "office 25",
            "consultation about Spring Framework");
    private final NewEventDto newEventDtoWrongDates = new NewEventDto(
            "consultation",
            start,
            start.minusHours(1),
            "office 25",
            "consultation about Spring Framework");
    private final EventFullDto eventFullDto = new EventFullDto(
            id,
            "consultation",
            start,
            start.plusHours(1),
            "office 25",
            "consultation about Spring Framework",
            userDto);

    @Test
    void addEvent_whenFieldsValid_thenEventSaved() {
        when(eventRepository.save(any())).thenReturn(event);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        EventFullDto actualEvent = eventService.addEvent(id, newEventDto);

        Assertions.assertEquals(eventFullDto, actualEvent);
    }

    @Test
    void addEvent_whenIncorrectDates_thenThrownException() {
        Assertions.assertThrows(WrongDatesException.class, () ->
                eventService.addEvent(1L, newEventDtoWrongDates));
    }

    @Test
    void addEvent_whenNoTitle_thenThrownException() {
        doThrow(DataIntegrityViolationException.class).when(eventRepository).save(any(Event.class));

        Assertions.assertThrows(DataIntegrityViolationException.class, () ->
                eventService.addEvent(1L, newEventDtoNoName));
    }

    @Test
    void updateEvent_whenEventFound_thenUpdatedOnlyAvailableFields() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        EventFullDto actualEvent = eventService.updateEvent(id, id, updateEventDto);

        Assertions.assertEquals(EventMapper.toEventFullDto(event), actualEvent);
        verify(eventRepository, times(1))
                .findById(id);
    }

    @Test
    void updateEvent_whenEventNotFound_thenThrownException() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> eventService.updateEvent(id, 2L, updateEventDto));
    }

    @Test
    void deleteEvent() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        eventService.deleteEvent(id, id);

        verify(eventRepository, times(1))
                .deleteById(id);
    }

    @Test
    void getAllEventsByInitiator() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(eventRepository.findAllByInitiatorId(any(), any())).thenReturn(List.of(event));

        List<EventShortDto> targetEvents = eventService.getAllEventsByInitiator(id, 0, 10);

        Assertions.assertNotNull(targetEvents);
        Assertions.assertEquals(1, targetEvents.size());
        verify(eventRepository, times(1))
                .findAllByInitiatorId(any(), any());
    }

    @Test
    void getEventById_whenUserFound_thenReturnEvent() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        EventFullDto actualEvent = eventService.getEventById(id, id);

        Assertions.assertEquals(EventMapper.toEventFullDto(event), actualEvent);
    }
}