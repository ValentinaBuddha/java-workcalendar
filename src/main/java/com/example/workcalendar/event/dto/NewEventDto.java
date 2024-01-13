package com.example.workcalendar.event.dto;

import com.example.workcalendar.utils.Create;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.example.workcalendar.utils.DateConstant.DATE_PATTERN;
import static com.example.workcalendar.utils.DateConstant.TIME_PATTERN;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {

    @NotBlank(groups = {Create.class})
    @Size(max = 255, groups = {Create.class})
    String title;

    @NotNull(groups = {Create.class})
    @FutureOrPresent(groups = {Create.class})
    @JsonFormat(pattern = DATE_PATTERN)
    LocalDate date;

    @NotNull(groups = {Create.class})
    @JsonFormat(pattern = TIME_PATTERN)
    LocalTime start;

    @NotNull(groups = {Create.class})
    @JsonFormat(pattern = TIME_PATTERN)
    LocalTime end;

    @NotBlank(groups = {Create.class})
    @Size(max = 255, groups = {Create.class})
    String location;

    @NotBlank(groups = {Create.class})
    @Size(min = 20, max = 7000, groups = {Create.class})
    String description;
}
