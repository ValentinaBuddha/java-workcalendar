package com.example.workcalendar.event.service;

import com.example.workcalendar.event.dto.EventFullDto;
import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.event.dto.NewEventDto;
import com.example.workcalendar.event.dto.UpdateEventDto;
import com.example.workcalendar.event.model.Event;
import com.example.workcalendar.event.model.EventMapper;
import com.example.workcalendar.event.repository.EventRepository;
import com.example.workcalendar.exception.EntityNotFoundException;
import com.example.workcalendar.exception.NotInitiatorException;
import com.example.workcalendar.exception.WrongDatesException;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        log.info("Создание нового события {}", newEventDto.getTitle());
        if (!newEventDto.getEnd().isAfter(newEventDto.getStart()) ||
                newEventDto.getStart().atDate(newEventDto.getDate()).isBefore(LocalDateTime.now())) {
            throw new WrongDatesException("Время начала события должно быть раньше времени конца события.");
        }
        Event event = eventRepository.save(EventMapper.toEvent(newEventDto));
        event.setInitiator(getUser(userId));
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventDto updateEventDto) {
        log.info("Обновление существующего события c id {}", eventId);
        getUser(userId);
        Event event = getEvent(eventId);
        String title = updateEventDto.getTitle();
        if (title != null && !title.isBlank()) {
            event.setTitle(title);
        }
        LocalTime start = updateEventDto.getStart();
        if (start != null) {
            event.setStart(start);
        }
        LocalTime end = updateEventDto.getEnd();
        if (end != null) {
            event.setEnd(end);
        }
        String location = updateEventDto.getLocation();
        if (location != null && !location.isBlank()) {
            event.setLocation(location);
        }
        String description = updateEventDto.getDescription();
        if (description != null && !description.isBlank()) {
            event.setDescription(description);
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotInitiatorException("Пользователь не является инициатором события.");
        }
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public void deleteEvent(Long userId, Long eventId) {
        log.info("Удаление события по идентификатору {}", eventId);
        getUser(userId);
        eventRepository.deleteById(eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getAllEventsByInitiator(Long userId, Integer from, Integer size) {
        log.info("Получение всех событий для пользователя с id {}", userId);
        getUser(userId);
        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from / size, size)).stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventById(Long userId, Long eventId) {
        log.info("Получение события по идентификатору {}", eventId);
        getUser(userId);
        return EventMapper.toEventFullDto(getEvent(eventId));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", Event.class)));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", User.class)));
    }
}