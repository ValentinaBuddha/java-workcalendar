package com.example.workcalendar.event.controller;

import com.example.workcalendar.event.dto.EventFullDto;
import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.event.dto.NewEventDto;
import com.example.workcalendar.event.dto.UpdateEventDto;
import com.example.workcalendar.event.service.EventService;
import com.example.workcalendar.utils.Create;
import com.example.workcalendar.utils.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable Long userId,
                                 @Validated(Create.class) @RequestBody NewEventDto newEventDto) {
        log.info("POST / events / {} ", newEventDto.getTitle());
        return eventService.addEvent(userId, newEventDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                    @Validated(Update.class) @RequestBody UpdateEventDto updateEventDto) {
        log.info("PATCH / user {} / event {}", userId, eventId);
        return eventService.updateEvent(userId, eventId, updateEventDto);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("DELETE / events / {}", eventId);
        eventService.deleteEvent(userId, eventId);
    }

    @GetMapping
    List<EventShortDto> getAllEventsByInitiator(@PathVariable Long userId,
                                                @RequestParam(value = "from", defaultValue = "0")
                                                @PositiveOrZero Integer from,
                                                @RequestParam(value = "size", defaultValue = "10")
                                                @Positive Integer size) {
        log.info("GET / events / user {}", userId);
        return eventService.getAllEventsByInitiator(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("GET / events / {}", eventId);
        return eventService.getEventById(userId, eventId);
    }
}
