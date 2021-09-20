package com.example.webmvc.registration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:testdb", "spring.jpa.hibernate.ddl-auto=create-drop"})
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registerNewUserAccount() {
        UserDto userDto = new UserDto("Name", "hans@mail.de", "123456789", "123456789");

        User user = userService.registerNewUserAccount(userDto);
        var maybeDbUser = userRepository.findByUsername("Name");
        assertThat(maybeDbUser).isPresent();
        assertThat(maybeDbUser.get()).isEqualTo(user);
    }

    @Test
    void getUserByUsername() {
        UserDto userDto = new UserDto("NachName", "hansal@mail.de", "123456789", "123456789");

        User user = userService.registerNewUserAccount(userDto);
        var maybeUser = userService.getUserByUsername("NachName");
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get()).isEqualTo(user);
    }
}