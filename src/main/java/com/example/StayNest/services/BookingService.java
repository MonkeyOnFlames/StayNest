package com.example.StayNest.services;

import com.example.StayNest.dto.BookingResponseDTO;
import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.exceptions.UnauthorizedException;
import com.example.StayNest.factories.BookingFactory;
import com.example.StayNest.helpClasses.bookingFactory.CalculateTotalAmount;
import com.example.StayNest.helpClasses.bookingFactory.ConvertToBookingResponseDTO;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.User;
import com.example.StayNest.notification.EmailNotification;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;
    private final UserService userService;
    private final BookingFactory bookingFactory;

    public BookingService(BookingRepository bookingRepository, ListingRepository listingRepository, UserService userService, BookingFactory bookingFactory) {
        this.bookingRepository = bookingRepository;
        this.listingRepository = listingRepository;
        this.userService = userService;
        this.bookingFactory = bookingFactory;
    }

    // Helenas createBooking
    public BookingResponseDTO createBooking(Booking booking) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.emailNotification("success your message is sent");
        return bookingFactory.createBookingObject(booking);

    }

    public BookingResponseDTO getBookingsById(String id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        User loggedInUser = userService.getLoggedInUser();

        if (loggedInUser.getUsername().equals(existingBooking.getUser().getUsername())
        || loggedInUser.getUsername().equals(existingBooking.getListing().getUser().getUsername())) {
            return ConvertToBookingResponseDTO.convertToBookingResponseDTO(existingBooking);
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
       CalculateTotalAmount calcTotalAmount = new CalculateTotalAmount();

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
           if (booking.getStartDate() != null){
               existingBooking.setStartDate(booking.getStartDate());
               existingBooking.setTotalAmount(calcTotalAmount.calculateTotalAmount(existingBooking));
           }
           if (booking.getEndDate() != null){
               existingBooking.setEndDate(booking.getEndDate());
               existingBooking.setTotalAmount(calcTotalAmount.calculateTotalAmount(existingBooking));
           }
       } else {
           throw new UnauthorizedException("You do not have permission to update this booking.");
       }

       Booking updatedBooking =  bookingRepository.save(existingBooking);

       return ConvertToBookingResponseDTO.convertToBookingResponseDTO(updatedBooking);
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


}




