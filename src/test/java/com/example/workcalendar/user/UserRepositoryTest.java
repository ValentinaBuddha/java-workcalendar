package com.example.workcalendar.user;

import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final User user = new User(
            null,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT"
    );

    @Test
    @DirtiesContext
    void testSaveUser() {
        assertThat(user.getId(), equalTo(null));
        userRepository.save(user);
        assertThat(user.getId(), notNullValue());
    }
}