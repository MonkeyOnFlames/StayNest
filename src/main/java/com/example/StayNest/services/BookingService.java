package com.example.StayNest.services;

import com.example.StayNest.dto.BookingResponseDTO;
import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.exceptions.UnauthorizedException;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final UserService userService;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, ListingRepository listingRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.userService = userService;
    }

    public BookingResponseDTO createBooking(@Valid Booking booking) {

        User loggedInUser = userService.getLoggedInUser();

        booking.setUser(loggedInUser);

        validateBooking(booking);

        Booking savedBooking = bookingRepository.save(booking);


        return convertToBookingResponseDTO(savedBooking);
    }

    public BookingResponseDTO getBookingsById(String id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        User loggedInUser = userService.getLoggedInUser();

        if (loggedInUser.getUsername().equals(existingBooking.getUser().getUsername())
        || loggedInUser.getUsername().equals(existingBooking.getListing().getUser().getUsername())) {
            return convertToBookingResponseDTO(existingBooking);
        }
        else {
            throw new UnauthorizedException("You are not authorized to view this booking");
        }

    }

//PATCH
   public BookingResponseDTO updateBooking(String id, Booking booking){

       Booking existingBooking = bookingRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

       User loggedInUser = userService.getLoggedInUser();

       if (loggedInUser.getUsername().equals(existingBooking.getUser().getUsername())) {
           //uppdatera endast icke null fält
           if (booking.getListing() != null){
               existingBooking.setListing(booking.getListing());
           }
           if (booking.getUser() != null){
               existingBooking.setUser(booking.getUser());
           }
           if (booking.getReview() != null){
               existingBooking.setReview(booking.getReview());
           }
           if (booking.getTotalAmount() != null){
               existingBooking.setTotalAmount(booking.getTotalAmount());
           }
           if (booking.getStartDate() != null){
               existingBooking.setStartDate(booking.getStartDate());
           }
           if (booking.getEndDate() != null){
               existingBooking.setEndDate(booking.getEndDate());
           }
       } else {
           throw new UnauthorizedException("You do not have permission to update this booking.");
       }


       Booking updatedBooking =  bookingRepository.save(existingBooking);

       return convertToBookingResponseDTO(updatedBooking);
    }

    public void deleteBooking(String id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        User loggedInUser = userService.getLoggedInUser();

        if (loggedInUser.getUsername().equals(booking.getUser().getUsername())) {
            bookingRepository.delete(booking);
        } else {
            throw new UnauthorizedException("You do not have permission to delete this booking.");
        }
    }

    private void validateBooking(Booking booking){
        if (booking.getListing() == null){
            throw new IllegalArgumentException("Listing cannot be empty or null");
        }
        if (booking.getUser() == null){
            throw new IllegalArgumentException("User cannot be empty or null");
        }
        if (booking.getTotalAmount() < 0){
            throw new IllegalArgumentException("Price cannot be less than 0");
        }
        if (booking.getStartDate() == null){
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (booking.getEndDate() == null){
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (booking.getEndDate().before(booking.getStartDate())){
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        for (Listing.Availability availability : listingRepository.findListingById(booking.getListing().getId()).getAvailabilities()){
            int i = 0;
            if ((booking.getStartDate().equals(availability.getStartDate()) || booking.getStartDate().after(availability.getStartDate()))
                    && (booking.getEndDate().equals(availability.getEndDate()) || booking.getEndDate().before(availability.getEndDate()))){

                if (booking.getStartDate().equals(availability.getStartDate())){
                    availability.setStartDate(booking.getEndDate()); // +1 dag
                }
                else if (booking.getEndDate().equals(availability.getEndDate())) {
                    availability.setEndDate(booking.getStartDate()); // -1 dag
                }
                else if (booking.getStartDate().equals(availability.getStartDate()) && booking.getEndDate().equals(availability.getEndDate())) {
                    listingRepository.findListingById(booking.getListing().getId()).getAvailabilities().remove(availability);
                }
                else {
                    Listing.Availability newAvailability = new Listing.Availability();
                    newAvailability.setStartDate(booking.getEndDate()); // +1 dag
                    newAvailability.setEndDate(availability.getEndDate());
                    availability.setEndDate(booking.getStartDate()); //-1 dag

                    listingRepository.findListingById(booking.getListing().getId()).getAvailabilities().add(newAvailability);
                }
                break;
            } else {
                i++;
                if (i >= listingRepository.findListingById(booking.getListing().getId()).getAvailabilities().size()){
                    throw new IllegalArgumentException("The chosen date is not available");
                }
            }
        }
    }
    private BookingResponseDTO convertToBookingResponseDTO(Booking booking) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setId(booking.getId());
        bookingResponseDTO.setListingId(booking.getListing().getId());
        bookingResponseDTO.setListingName(booking.getListing().getName());
        bookingResponseDTO.setUserId(booking.getUser().getId());
        bookingResponseDTO.setUserName(booking.getUser().getFirstName());
        bookingResponseDTO.setTotalAmount(booking.getTotalAmount());
        bookingResponseDTO.setStartDate(booking.getStartDate());
        bookingResponseDTO.setEndDate(booking.getEndDate());
        bookingResponseDTO.setCreatedAt(booking.getCreatedAt());

        return bookingResponseDTO;
    }
}
