package com.example.StayNest.services;

import com.example.StayNest.dto.BookingRequestDTO;
import com.example.StayNest.dto.BookingResponseDTO;
import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.exceptions.UnauthorizedException;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;
    private final UserService userService;

    public BookingService(BookingRepository bookingRepository, ListingRepository listingRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.listingRepository = listingRepository;
        this.userService = userService;
    }

    // Helenas createBooking
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {
        Booking booking = new Booking();

        User loggedInUser = userService.getLoggedInUser();
        booking.setUser(loggedInUser);

        Listing listing = null;

        // kontrollerar att requesten innehåller en giltig listning
        if (bookingRequestDTO.getListing() != null && bookingRequestDTO.getListing().getId() != null) {
            listing = listingRepository.findListingById(bookingRequestDTO.getListing().getId());
            if (listing == null) {
                throw new ResourceNotFoundException("Listing not found with id: " + bookingRequestDTO.getListing().getId());
            }
        } else {
            throw new IllegalArgumentException("Listing ID is required");
        }

        booking.setListing(listing);
        booking.setStartDate(bookingRequestDTO.getStartDate());
        booking.setEndDate(bookingRequestDTO.getEndDate());

        if (bookingRequestDTO.getTotalAmount() == null) {
            // ChronoUnit.DAYS.between beräknar antalet hela dagar mellan två datum
            // den räknar INTE med slutdatumet i resultatet, endast hela dagar mellan datumen...
            // exempel: mellan 2025-06-05 och 2025-06-10 blir resultatet 5 dagar
            // lägger till 1 till slutdatumet så att både check-in och check-out dagen räknas med
            long daysBetween = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate()) + 1;
            // totalpriset blir listningens pris per natt multiplicerat med antalet nätter
            double totalAmount = daysBetween * listing.getPrice();
            booking.setTotalAmount(totalAmount);
        } else {
            booking.setTotalAmount(bookingRequestDTO.getTotalAmount());
        }

        // validerar bokningen och uppdaterar listningens tillgänglighet
        validateAndUpdateAvailability(booking);

        Booking savedBooking = bookingRepository.save(booking);
        return convertToBookingResponseDTO(savedBooking);
    }

    // hjälpmetod som:
    // - validerar att en bokning är giltig och uppdaterar listningens tillgänglighet
    // - kontrollerar att datumen är giltiga, att listningen är tillgänglig, och att det inte
    // finns överlappande bokningar
    private void validateAndUpdateAvailability(Booking booking) {
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();

        // kontrollerar att start- och slutdatum är angivna
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Booking start and end dates cannot be null");
        }

        // kontrollerar att startdatum inte är efter slutdatum
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Booking start date cannot be after end date");
        }

        // kontrollerar att startdatum inte är i det förflutna
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Booking start date cannot be in the past");
        }

        Listing listing = booking.getListing();

        // letar efter en tillgänglighetsperiod som matchar de begärda datumen eftersom ni ville ha det..
        Listing.Availability matchingAvailability = null;
        for (Listing.Availability availability : listing.getAvailabilities()) {
            // kontrollerar om datumen ligger inom tillgänglighetsperioden
            if (!startDate.isBefore(availability.getStartDate()) &&
                    !endDate.isAfter(availability.getEndDate())) {
                matchingAvailability = availability;
                break;
            }
        }

        if (matchingAvailability == null) {
            throw new IllegalArgumentException("The selected dates are not available for booking");
        }

        // kontrollerar att det inte finns några överlappande bokningar under den valda perioden
        List<Booking> existingBookings = bookingRepository.findByListingIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                listing.getId(), endDate, startDate);

        if (existingBookings != null && !existingBookings.isEmpty()) {
            throw new IllegalArgumentException("The selected dates overlap with existing bookings");
        }

        // uppdaterar listningens tillgänglighet baserat på den nya bokningen
        updateAvailability(listing, matchingAvailability, startDate, endDate);
    }

    // uppdaterar tillgängligheten för en listning när en bokning har gjorts, kanske ska flyttas men la allt här nu...
    //  tar även bort den ursprungliga tillgänglighetsperioden och skapar nya perioder före och efter bokningen om det behövs..
    private void updateAvailability(Listing listing, Listing.Availability matchingAvailability,
                                    LocalDate startDate, LocalDate endDate) {
        // tar bort den ursprungliga tillgänglighetsperioden från listningen
        listing.getAvailabilities().remove(matchingAvailability);

        // skapar en ny tillgänglighetsperiod före bokningen om det finns dagar kvar före
        if (startDate.isAfter(matchingAvailability.getStartDate())) {
            Listing.Availability beforeBooking = new Listing.Availability();
            beforeBooking.setStartDate(matchingAvailability.getStartDate());
            beforeBooking.setEndDate(startDate.minusDays(1));
            listing.getAvailabilities().add(beforeBooking);
        }

        // skapar en ny tillgänglighetsperiod efter bokningen om det finns dagar kvar efter
        if (endDate.isBefore(matchingAvailability.getEndDate())) {
            Listing.Availability afterBooking = new Listing.Availability();
            afterBooking.setStartDate(endDate.plusDays(1));
            afterBooking.setEndDate(matchingAvailability.getEndDate());
            listing.getAvailabilities().add(afterBooking);
        }

        // sparar den uppdaterade listningen med de nya tillgänglighetsperioderna
        listingRepository.save(listing);
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
