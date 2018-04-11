package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.database.UserRepository;
import no.bekk.sommerskole.domain.User;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    UserRepository userRepository;

    @Inject
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return this.userRepository.getUserById(id);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        this.userRepository.createUser(user.getName());
    }
}
