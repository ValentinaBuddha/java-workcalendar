package com.example.workcalendar.event.service;

import com.example.workcalendar.event.dto.EventFullDto;
import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.event.dto.NewEventDto;
import com.example.workcalendar.event.dto.UpdateEventDto;

import java.util.List;

public interface EventService {

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventDto updateEventDto);

    void deleteEvent(Long userId, Long eventId);

    List<EventShortDto> getAllEventsByInitiator(Long userId, Integer from, Integer size);

    EventFullDto getEventById(Long userId, Long eventId);
}
