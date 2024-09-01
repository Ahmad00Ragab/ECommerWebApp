package gov.iti.jets.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User("john_doe", "john@example.com", "password123", LocalDate.now(), LocalDate.now());
        user2 = new User("jane_doe", "jane@example.com", "password456", LocalDate.now(), LocalDate.now());
        user3 = new User("alice_smith", "alice@example.com", "password789", LocalDate.now(), LocalDate.now());

        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(1)).thenReturn(user1);
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(userRepository.findByUsername("john_doe")).thenReturn(user1);
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
    }

    @Test
    void findAll() {
        Set<User> users = userRepository.findAll();
        assertEquals(3, users.size());
        assertTrue(users.contains(user1));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        User foundUser = userRepository.findById(1);
        assertNotNull(foundUser);
        assertEquals("john_doe", foundUser.getUsername());
    }

    @Test
    void save() {
        User newUser = new User("new_kid3", "john.doe@gmail.com", "password123", LocalDate.now(), LocalDate.now());

        given(this.userRepository.save(newUser)).willReturn(newUser);
        given(this.userRepository.findByUsername("new_kid3")).willReturn(newUser);

        this.userRepository.save(newUser);

        User savedUser = userRepository.findByUsername("new_kid3");
        assertNotNull(savedUser);

        assertThat(savedUser.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(newUser.getPassword());

        verify(this.userRepository, times(1)).save(newUser);
    }

    @Test
    void delete() {
        userRepository.delete(user1);
        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    void update() {
        user1.setEmail("updated1@example.com");
        userRepository.update(user1);
        assertEquals("updated1@example.com", user1.getEmail());

        verify(userRepository, times(1)).update(user1);
    }

    @Test
    void getUserByUsername() {
        User foundUser = userRepository.findByUsername("john_doe");
        assertNotNull(foundUser);
        assertEquals("john_doe", foundUser.getUsername());
    }
}