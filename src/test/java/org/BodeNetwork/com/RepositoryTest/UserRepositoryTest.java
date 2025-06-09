package org.BodeNetwork.com.RepositoryTest;


import org.BodeNetwork.com.data.models.User;
import org.BodeNetwork.com.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {

        userRepository.deleteAll();
    }

    @Test
    public void testThatUserRepositoryIsEmpty() {
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    public void testThatUserRepositoryIsNotEmpty() {
        User user = new User();
        userRepository.save(user);
        assertTrue(userRepository.findAll().contains(user));
    }

    @Test
    public void testThatUserRepositoryContainsUser() {
        User user = new User();
        user.setPhoneNumber("07046182869");
        user.setPassword("123456");
        userRepository.save(user);
        User findUser = userRepository.findByPhoneNumber("07046182869").get();
        assertEquals(user, findUser);
        User findUserById = userRepository.findById(String.valueOf(user.getId())).get();
        assertEquals(user, findUserById);

    }
}
