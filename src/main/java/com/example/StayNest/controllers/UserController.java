package com.example.StayNest.controllers;

import com.example.StayNest.dto.BookingResponseDTO;
import com.example.StayNest.dto.ListingResponseGetAll;
import com.example.StayNest.dto.UserResponseDTO;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'LANDLORD')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        UserResponseDTO users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD', 'USER')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, /*@Valid */@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @GetMapping("/{id}/listings")
    public ResponseEntity<List<ListingResponseGetAll>> getUserListings(@PathVariable String id) {
        List<ListingResponseGetAll> userListings = userService.getUserListings(id);
        return new ResponseEntity<>(userListings, HttpStatus.OK);
    }

    @GetMapping("/{id}/bookings")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD', 'USER')")
    public ResponseEntity<List<BookingResponseDTO>> getUserBookings(@PathVariable String id) {
        List<BookingResponseDTO> userBookings = userService.getUserBookings(id);
        return new ResponseEntity<>(userBookings, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAnyRole('USER', 'LANDLORD')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }




}
