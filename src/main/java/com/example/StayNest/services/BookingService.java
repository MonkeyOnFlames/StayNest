package com.example.StayNest.services;

import com.example.StayNest.dto.BookingResponseDTO;
import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.exceptions.UnauthorizedException;
import com.example.StayNest.factories.BookingFactory;
import com.example.StayNest.helpClasses.bookingFactory.CalculateTotalAmount;
import com.example.StayNest.helpClasses.bookingFactory.ConvertToBookingResponseDTO;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.User;
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

        BookingResponseDTO tempBooking = bookingFactory.createBookingObject(booking);

        return tempBooking;

//        Booking booking = new Booking();
//
//        User loggedInUser = userService.getLoggedInUser();
//        booking.setUser(loggedInUser);
//
//        Listing listing = null;
//
//        // kontrollerar att requesten innehåller en giltig listning
//        if (bookingRequestDTO.getListing() != null && bookingRequestDTO.getListing().getId() != null) {
//            listing = listingRepository.findListingById(bookingRequestDTO.getListing().getId());
//            if (listing == null) {
//                throw new ResourceNotFoundException("Listing not found with id: " + bookingRequestDTO.getListing().getId());
//            }
//        } else {
//            throw new IllegalArgumentException("Listing ID is required");
//        }
//
//        booking.setListing(listing);
//        booking.setStartDate(bookingRequestDTO.getStartDate());
//        booking.setEndDate(bookingRequestDTO.getEndDate());
//
//        if (bookingRequestDTO.getTotalAmount() == null) {
//            calculateTotalAmount(booking);
//        } else {
//            booking.setTotalAmount(bookingRequestDTO.getTotalAmount());
//        }
//
//        // validerar bokningen och uppdaterar listningens tillgänglighet
//        validateAndUpdateAvailability(booking);
//
//        Booking savedBooking = bookingRepository.save(booking);
//        return convertToBookingResponseDTO(savedBooking);
//    }
//
//    // hjälpmetod som:
//    // - validerar att en bokning är giltig och uppdaterar listningens tillgänglighet
//    // - kontrollerar att datumen är giltiga, att listningen är tillgänglig, och att det inte
//    // finns överlappande bokningar
//    private void validateAndUpdateAvailability(Booking booking) {
//        LocalDate startDate = booking.getStartDate();
//        LocalDate endDate = booking.getEndDate();
//
//        // kontrollerar att start- och slutdatum är angivna
//        if (startDate == null || endDate == null) {
//            throw new IllegalArgumentException("Booking start and end dates cannot be null");
//        }
//
//        // kontrollerar att startdatum inte är efter slutdatum
//        if (startDate.isAfter(endDate)) {
//            throw new IllegalArgumentException("Booking start date cannot be after end date");
//        }
//
//        // kontrollerar att startdatum inte är i det förflutna
//        if (startDate.isBefore(LocalDate.now())) {
//            throw new IllegalArgumentException("Booking start date cannot be in the past");
//        }
//
//        Listing listing = booking.getListing();
//
//        // letar efter en tillgänglighetsperiod som matchar de begärda datumen eftersom ni ville ha det..
//        Listing.Availability matchingAvailability = null;
//        for (Listing.Availability availability : listing.getAvailabilities()) {
//            // kontrollerar om datumen ligger inom tillgänglighetsperioden
//            if (!startDate.isBefore(availability.getStartDate()) &&
//                    !endDate.isAfter(availability.getEndDate())) {
//                matchingAvailability = availability;
//                break;
//            }
//        }
//
//        if (matchingAvailability == null) {
//            throw new IllegalArgumentException("The selected dates are not available for booking");
//        }
//
//        // kontrollerar att det inte finns några överlappande bokningar under den valda perioden
//        List<Booking> existingBookings = bookingRepository.findByListingIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
//                listing.getId(), endDate, startDate);
//
//        if (existingBookings != null && !existingBookings.isEmpty()) {
//            throw new IllegalArgumentException("The selected dates overlap with existing bookings");
//        }
//
//        // uppdaterar listningens tillgänglighet baserat på den nya bokningen
//        updateAvailability(listing, matchingAvailability, startDate, endDate);
//    }
//
//    // uppdaterar tillgängligheten för en listning när en bokning har gjorts, kanske ska flyttas men la allt här nu...
//    //  tar även bort den ursprungliga tillgänglighetsperioden och skapar nya perioder före och efter bokningen om det behövs..
//    private void updateAvailability(Listing listing, Listing.Availability matchingAvailability,
//                                    LocalDate startDate, LocalDate endDate) {
//        // tar bort den ursprungliga tillgänglighetsperioden från listningen
//        listing.getAvailabilities().remove(matchingAvailability);
//
//        // skapar en ny tillgänglighetsperiod före bokningen om det finns dagar kvar före
//        if (startDate.isAfter(matchingAvailability.getStartDate())) {
//            Listing.Availability beforeBooking = new Listing.Availability();
//            beforeBooking.setStartDate(matchingAvailability.getStartDate());
//            beforeBooking.setEndDate(startDate.minusDays(1));
//            listing.getAvailabilities().add(beforeBooking);
//        }
//
//        // skapar en ny tillgänglighetsperiod efter bokningen om det finns dagar kvar efter
//        if (endDate.isBefore(matchingAvailability.getEndDate())) {
//            Listing.Availability afterBooking = new Listing.Availability();
//            afterBooking.setStartDate(endDate.plusDays(1));
//            afterBooking.setEndDate(matchingAvailability.getEndDate());
//            listing.getAvailabilities().add(afterBooking);
//        }
//
//        // sparar den uppdaterade listningen med de nya tillgänglighetsperioderna
//        listingRepository.save(listing);
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




