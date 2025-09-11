package com.example.StayNest.factories;

import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.services.UserService;

import java.time.LocalDate;
import java.util.List;

public class BookingFactory {

    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;
    private final UserService userService;

    public BookingFactory(BookingRepository bookingRepository, ListingRepository listingRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.listingRepository = listingRepository;
        this.userService = userService;
    }


    public Booking createBookingObject (Booking booking) {
        Booking tempBooking = new Booking();

        User loggedInUser = userService.getLoggedInUser();
        tempBooking.setUser(loggedInUser);

        Listing listing = null;

        if (booking.getListing() != null && booking.getListing().getId() != null) {
            listing = listingRepository.findListingById(booking.getListing().getId());
            if (listing == null) {
                throw new ResourceNotFoundException("Listing not found with id: " + booking.getListing().getId());
            }
        } else {
            throw new IllegalArgumentException("Listing ID is required");
        }

        tempBooking.setListing(listing);
        tempBooking.setTotalAmount(booking.getTotalAmount());
        tempBooking.setReview(booking.getReview());
        tempBooking.setStartDate(booking.getStartDate());
        tempBooking.setEndDate(booking.getEndDate());


        CalculateTotalAmount calcTotalAmount = new CalculateTotalAmount();
        tempBooking.setTotalAmount(calcTotalAmount.calculateTotalAmount(tempBooking));

        validateAndUpdateAvailability(tempBooking);

        Booking savedBooking = bookingRepository.save(tempBooking);

        return savedBooking;
    }





    // hjälpmetod som:
    // - validerar att en bokning är giltig och uppdaterar listningens tillgänglighet
    // - kontrollerar att datumen är giltiga, att listningen är tillgänglig, och att det inte
    // finns överlappande bokningar
    // den här metoden kommer att ersättas av en Chain av validators när den är klar
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

    /*
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
*/

}
