package com.example.StayNest.services;

import com.example.StayNest.models.Role;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        //hash password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //ensure the user got at least default role USER
        if(user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        }

        userRepository.save(user);
    }

    //check if username already exists
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    //this needs to be changed when we implement register and log in
    public User createUser(@Valid User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
             user.setRoles(Set.of(Role.USER));
        }

        validateUser(user);

        return userRepository.save(user);
    }

    //find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> getUserById(String id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(@Valid String id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // only updates none null fields
        if (user.getRoles() != null) {
            existingUser.setRoles(user.getRoles());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getFirst_name() != null) {
            existingUser.setFirst_name(user.getFirst_name());
        }
        if (user.getLast_name() != null) {
            existingUser.setLast_name(user.getLast_name());
        }
        if (user.getAdress() != null) {
            existingUser.setAdress(user.getAdress());
        }
        if (user.getPhone() != null) {
            existingUser.setPhone(user.getPhone());
        }
        if (user.getAge() != null) {
            existingUser.setAge(user.getAge());
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.delete(user);
    }

    //not sure about the user.getAge. can't use trim
    private void validateUser(User user) {
        if(user.getFirst_name() == null || user.getFirst_name().trim().isEmpty()) {
            throw new IllegalArgumentException("First name can't be empty or null.");
        }

        if(user.getLast_name() == null || user.getLast_name().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name can't be empty or null.");
        }

        if(user.getAdress() == null || user.getAdress().trim().isEmpty()) {
            throw new IllegalArgumentException("Adress name can't be empty or null.");
        }

        if(user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("The phone number can't be empty or null.");
        }

        if(user.getAge() == null || user.getAge() < 0 /*.trim().isEmpty()*/) {
            throw new IllegalArgumentException("The age can't be negative or null.");
        }

        /*if(user.getPrice() < 0) {
            throw new IllegalArgumentException("Product price can not be less than 0.");
        }*/
    }



}
