package com.example.StayNest.helpClasses.validatorChain;

import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.repositories.ListingRepository;

public class ValidateAvailability {

    private final ListingRepository listingRepository;

    public ValidateAvailability(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public boolean validateAvailability(Booking booking) {

        Listing listing = listingRepository.findListingById(booking.getListing().getId());

        Listing.Availability matchingAvailability = null;
        for (Listing.Availability availability : listing.getAvailabilities()) {
            // kontrollerar om datumen ligger inom tillgänglighetsperioden
            if (!booking.getStartDate().isBefore(availability.getStartDate()) &&
                    !booking.getEndDate().isAfter(availability.getEndDate())) {
                matchingAvailability = availability;
                break;
            }
        }

        return matchingAvailability != null;

    }
}
