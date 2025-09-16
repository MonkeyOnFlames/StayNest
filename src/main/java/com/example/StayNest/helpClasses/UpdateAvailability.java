package com.example.StayNest.helpClasses;

import com.example.StayNest.models.Listing;
import com.example.StayNest.repositories.ListingRepository;

import java.time.LocalDate;


public class UpdateAvailability {

    private final ListingRepository listingRepository;

    public UpdateAvailability(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    // Uppdaterar tillgängligheten för en listning när en bokning har gjorts, kanske ska flyttas men la allt här nu...
    //  Tar även bort den ursprungliga tillgänglighetsperioden och skapar nya perioder före och efter bokningen om det behövs...
    public void updateAvailability(Listing listing, Listing.Availability matchingAvailability,
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
}
