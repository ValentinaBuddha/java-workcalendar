package com.example.workcalendar.event.model;

import com.example.workcalendar.event.dto.EventFullDto;
import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.event.dto.NewEventDto;
import com.example.workcalendar.user.model.UserMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventMapper {

    public Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .start(newEventDto.getStart())
                .end(newEventDto.getEnd())
                .location(newEventDto.getLocation())
                .description(newEventDto.getDescription())
                .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .start(event.getStart())
                .end(event.getEnd())
                .location(event.getLocation())
                .description(event.getDescription())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .build();
    }

    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .start(event.getStart())
                .build();
    }
}
