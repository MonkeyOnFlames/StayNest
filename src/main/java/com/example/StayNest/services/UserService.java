package com.example.StayNest.services;

import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.exceptions.UnauthorizedException;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.Role;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;

    public UserService(UserRepository userRepository, BookingRepository bookingRepository, ListingRepository listingRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookingRepository = bookingRepository;
        this.listingRepository = listingRepository;
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

    //check if username already exists
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
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

    //when we implement register/login we need to delete some fields
    public User updateUser(String id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User loggedInUser = getLoggedInUser();

        // only updates none null fields
        if (loggedInUser.getUsername().equals(existingUser.getUsername())) {
//            if (user.getUsername() != null) {
//                existingUser.setUsername(user.getUsername());
//            }
//            if (user.getPassword() != null) {
//                existingUser.setPassword(user.getPassword());
//            }
//            if (user.getRoles() != null) {
//                existingUser.setRoles(user.getRoles());
//            }
            if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
                if (existsByEmail(user.getEmail())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
                }
                existingUser.setEmail(user.getEmail());
            }
            if (user.getFirstName() != null) {
                existingUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                existingUser.setLastName(user.getLastName());
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

        }else {
            throw new UnauthorizedException("You do not have permission to update this user.");
        }
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        User loggedInUser = getLoggedInUser();
        boolean isAdmin = userRepository.existsByUsernameAndRolesContaining(loggedInUser.getUsername(), Role.ADMIN);

        //if admin delete a user account
        if (isAdmin) {
            userRepository.delete(user);
        }
        //if user delete their own account, make user ANONYMOUS
        else if (user.getUsername().equals(loggedInUser.getUsername())) {
            user.setUsername(null);
            user.setEmail(null);
            user.setPassword(null);
            user.setFirstName(null);
            user.setLastName(null);
            user.setPhone(null);
            user.setAdress(null);
            user.setAge(null);
            user.setRoles(Set.of(Role.ANONYMOUS));

            userRepository.save(user);

        } else {
            throw new UnauthorizedException("You do not have permission to delete this user.");
        }
    }

    public List<Booking> getUserBookings(String id) {
        //went through listingRepository to get more info about the listing
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User loggedInUser = getLoggedInUser();

        if (loggedInUser.getUsername().equals(existingUser.getUsername())) {

            List<Booking> bookings = bookingRepository.findByUserId(id);
            if (bookings.isEmpty()) {
                throw new ResourceNotFoundException("Bookings not found with user ID: " + id);
            }

            return bookings;

        }else {
            throw new UnauthorizedException("You do not have permission to see this user bookings.");
        }

    }

        public List<Listing> getUserListings(String id) {
            //went through listingRepository to get more info about the listing
            List<Listing> listings = listingRepository.findByUserId(id);
            if (listings.isEmpty()) {
                throw new ResourceNotFoundException("Listings not found with user ID: " + id);
            }

            return listings;
        }


    //not sure about the user.getAge. can't use trim
    private void validateUser(User user) {
        if(user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name can't be empty or null.");
        }

        if(user.getLastName() == null || user.getLastName().trim().isEmpty()) {
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

    }

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
            throw new UnauthorizedException("User is not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user;
    }


}

