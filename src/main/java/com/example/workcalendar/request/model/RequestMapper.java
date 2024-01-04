package com.example.workcalendar.request.model;

import com.example.workcalendar.event.model.EventMapper;
import com.example.workcalendar.request.dto.RequestDto;
import com.example.workcalendar.user.model.UserMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestMapper {

    public RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getId(),
                UserMapper.toUserShortDto(request.getParticipant()),
                EventMapper.toEventShortDto(request.getEvent()),
                request.getStatus()
        );
    }
}
