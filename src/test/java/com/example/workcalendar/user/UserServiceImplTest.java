package com.example.workcalendar.user;

import com.example.workcalendar.exception.EntityNotFoundException;
import com.example.workcalendar.user.dto.UserDto;
import com.example.workcalendar.user.dto.UserShortDto;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.model.UserMapper;
import com.example.workcalendar.user.repository.UserRepository;
import com.example.workcalendar.user.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private final Long id = 1L;
    private final UserDto userDto = new UserDto(
            id,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT");
    private final User user = new User(
            id,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT");

    @Test
    void addUser_whenFieldsValid_thenSavedUser() {
        when(userRepository.save(any())).thenReturn(user);

        UserDto actualUser = userService.addUser(userDto);

        Assertions.assertEquals(userDto, actualUser);
    }

    @Test
    void addUser_whenUserEmailDuplicate_thenNotSavedUser() {
        doThrow(DataIntegrityViolationException.class).when(userRepository).save(any(User.class));

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(userDto));
    }

    @Test
    void addUser_whenInvalidEmail_thenNotSavedUser() {
        doThrow(ConstraintViolationException.class).when(userRepository).save(any(User.class));

        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.addUser(userDto));
    }

    @Test
    void updateUser_whenUserFound_thenUpdatedOnlyAvailableFields() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDto actualUser = userService.updateUser(id, userDto);

        Assertions.assertEquals(UserMapper.toUserDto(user), actualUser);
        verify(userRepository, times(1))
                .findById(user.getId());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1L);

        verify(userRepository, times(1))
                .deleteById(1L);
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserShortDto> targetUsers = userService.getAllUsers();

        Assertions.assertNotNull(targetUsers);
        Assertions.assertEquals(1, targetUsers.size());
        verify(userRepository, times(1))
                .findAll();
    }

    @Test
    void getUserById_whenUserFound_thenReturnedUser() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDto actualUser = userService.getUserById(id);

        Assertions.assertEquals(UserMapper.toUserDto(user), actualUser);
    }

    @Test
    void getUserById_whenUserNotFound_thenExceptionThrown() {
        when((userRepository).findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(2L));
    }
}