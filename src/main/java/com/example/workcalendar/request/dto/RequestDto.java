package com.example.workcalendar.request.dto;

import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.request.model.RequestStatus;
import com.example.workcalendar.user.dto.UserShortDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {

    Long id;

    UserShortDto participant;

    EventShortDto event;

    RequestStatus status;
}
