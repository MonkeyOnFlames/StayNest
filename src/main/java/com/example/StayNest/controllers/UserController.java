package com.example.StayNest.controllers;

import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.User;
import com.example.StayNest.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id) {
        Optional<User> users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD', 'USER')")
    public ResponseEntity<User> updateUser(@PathVariable String id, /*@Valid */@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @GetMapping("/{id}/listings")
    //@PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<List<Listing>> getUserListings(@PathVariable String id) {
        List<Listing> userListings = userService.getUserListings(id);
        return new ResponseEntity<>(userListings, HttpStatus.OK);
    }

    @GetMapping("/{id}/bookings")
    //@PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String id) {
        List<Booking> userBookings = userService.getUserBookings(id);
        return new ResponseEntity<>(userBookings, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN') or hasAnyRole('USER', 'LANDLORD')")
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



}
