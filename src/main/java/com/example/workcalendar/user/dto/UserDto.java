package com.example.workcalendar.user.dto;

import com.example.workcalendar.utils.Create;
import com.example.workcalendar.utils.Update;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Long id;

    @NotBlank(groups = {Create.class})
    @Size(max = 50, groups = {Create.class, Update.class})
    String surname;

    @NotBlank(groups = {Create.class})
    @Size(max = 50, groups = {Create.class, Update.class})
    String name;

    @NotEmpty(groups = {Create.class})
    @Email(groups = {Create.class, Update.class})
    @Size(max = 512, groups = {Create.class, Update.class})
    String email;

    @NotBlank(groups = {Create.class})
    @Size(max = 255, groups = {Create.class, Update.class})
    String position;

    @NotBlank(groups = {Create.class})
    @Size(max = 255, groups = {Create.class, Update.class})
    String department;
}

