package com.example.workcalendar.calendar.dto;

import com.example.workcalendar.user.dto.UserShortDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthWorkHours {

    UserShortDto employee;

    String month;

    Long hours;
}
