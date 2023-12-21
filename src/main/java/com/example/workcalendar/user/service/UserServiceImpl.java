package com.example.workcalendar.user.service;

import com.example.workcalendar.exception.EntityNotFoundException;
import com.example.workcalendar.user.User;
import com.example.workcalendar.user.dto.UserDto;
import com.example.workcalendar.user.UserMapper;
import com.example.workcalendar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        log.info("Создание нового пользователя {} {}", userDto.getSurname(), userDto.getName());
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(long userId, UserDto userDto) {
        log.info("Обновление существующего пользователя {} {}", userDto.getSurname(), userDto.getName());
        User oldUser = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", User.class)));
        String name = userDto.getName();
        String email = userDto.getEmail();
        if (name != null && !name.isBlank()) {
            oldUser.setName(name);
        }
        if (email != null && !email.isBlank()) {
            oldUser.setEmail(email);
        }
        return UserMapper.toUserDto(oldUser);
    }

    @Override
    public void deleteUser(long id) {
        log.info("Удаление пользователя по идентификатору {}", id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        log.info("Получение всех пользователей");
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(long userId) {
        log.info("Получение пользователя по идентификатору {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", User.class)));
        return UserMapper.toUserDto(user);
    }
}
