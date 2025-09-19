package com.example.StayNest.factories;

import com.example.StayNest.dto.BookingResponseDTO;
import com.example.StayNest.exceptions.ResourceNotFoundException;
import com.example.StayNest.helpClasses.bookingFactory.CalculateTotalAmount;
import com.example.StayNest.helpClasses.bookingFactory.ConvertToBookingResponseDTO;
import com.example.StayNest.helpClasses.bookingFactory.UpdateAvailability;
import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.models.User;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.services.UserService;
import com.example.StayNest.validators.RequestValidator;
import com.example.StayNest.validators.ValidatorProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookingFactory {

    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;
    private final UserService userService;

    public BookingFactory(BookingRepository bookingRepository, ListingRepository listingRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.listingRepository = listingRepository;
        this.userService = userService;
    }


    public BookingResponseDTO createBookingObject (Booking booking) {
        ConvertToBookingResponseDTO convertToBookingResponseDTO = new ConvertToBookingResponseDTO();

        User loggedInUser = userService.getLoggedInUser();
        booking.setUser(loggedInUser);

        RequestValidator validateChain = ValidatorProcessor.getChainOfValidators(booking, bookingRepository, listingRepository, userService);
        validateChain.validationHandler();

        Listing listing = null;

        if (booking.getListing() != null && booking.getListing().getId() != null) {
            listing = listingRepository.findListingById(booking.getListing().getId());
            if (listing == null) {
                throw new ResourceNotFoundException("Listing not found with id: " + booking.getListing().getId());
            }
        } else {
            throw new IllegalArgumentException("Listing ID is required");
        }

        booking.setListing(listing);


        CalculateTotalAmount calcTotalAmount = new CalculateTotalAmount();
        booking.setTotalAmount(calcTotalAmount.calculateTotalAmount(booking));


        UpdateAvailability updateAvailability = new UpdateAvailability(listingRepository);
        updateAvailability.updateAvailability(listing, booking.getStartDate(), booking.getEndDate());


        return convertToBookingResponseDTO.convertToBookingResponseDTO(bookingRepository.save(booking));
    }
}
