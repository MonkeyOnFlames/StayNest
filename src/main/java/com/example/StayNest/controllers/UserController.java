package com.example.StayNest.controllers;

import com.example.StayNest.models.Listing;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.User;
import com.example.StayNest.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        userService.registerUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //this needs to be changed when we implement register and log in
//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User createdUser = userService.createUser(user);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id) {
        Optional<User> users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable String id, /*@Valid */@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @GetMapping("/{id}/bookings")
    //@PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String id) {
        List<Booking> userBookings = userService.getUserBookings(id);
        return new ResponseEntity<>(userBookings, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping("/admin/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Void> deleteUserByAdmin(@PathVariable String id) {
//        userService.deleteUserByAdmin(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    //can't have the same endpoint...
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
//        userService.anonymizeUser(id);
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/{id}/listings")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<List<Listing>> getUserListings(@PathVariable String id) {
        List<Listing> userListings = userService.getUserListings(id);
        return new ResponseEntity<>(userListings, HttpStatus.OK);
    }

}
