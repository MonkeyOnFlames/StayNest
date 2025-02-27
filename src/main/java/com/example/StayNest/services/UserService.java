package com.example.StayNest.services;

import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.Role;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.UserRepository;
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


    //this needs to be changed when we implement register and log in
//    public User createUser(@Valid User user) {
//        if (user.getRoles() == null || user.getRoles().isEmpty()) {
//             user.setRoles(Set.of(Role.USER));
//        }
//
//        validateUser(user);
//
//        return userRepository.save(user);
//    }

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
                .orElseThrow(() -> new /*ResponseStatusException(HttpStatus.NOT_FOUND,*/
                        ResourceNotFoundException("User not found"));
//        validateUser(existingUser);
        // only updates none null fields
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getRoles() != null) {
            existingUser.setRoles(user.getRoles());
        }
        if (user.getEmail() != null) {
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
    }

//    public void deleteUserByAdmin(String id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//
//        userRepository.delete(user);
//    }
//
//    public void anonymizeUser(String id) {
//        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.findByUsername(currentUser.getUsername())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//
//        user.setUsername(null);
//        user.setEmail(null);
//        user.setPassword(null);
//        user.setFirstName(null);
//        user.setLastName(null);
//        user.setPhone(null);
//        user.setAdress(null);
//        user.setAge(null);
//        user.setRoles(Set.of(Role.ANONYMOUS));
//
//        userRepository.save(user);
//
//
//    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = userRepository.existsByUsernameAndRolesContaining(currentUser.getUsername(), Role.ADMIN);

        //if admin delete a user account
        if (isAdmin) {
            userRepository.delete(user);
        }
        //if user delete their own account, make user ANONYMOUS
        else if (user.getUsername().equals(currentUser.getUsername())){
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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    public List<Booking> getUserBookings(String id) {
        //went through listingRepository to get more info about the listing
        List<Booking> bookings = bookingRepository.findByUserId(id);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("Listings not found with user ID: " + id);
        }
        return bookings;
    }

        public List<Listing> getUserListings(String id) {
            //went through listingRepository to get more info about the listing
            List<Listing> listings = listingRepository.findByUserId(id);
            if (listings.isEmpty()) {
                throw new ResourceNotFoundException("Listings not found with user ID: " + id);
            }
//        userRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//
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

        /*if(user.getPrice() < 0) {
            throw new IllegalArgumentException("Product price can not be less than 0.");
        }*/
    }



}
