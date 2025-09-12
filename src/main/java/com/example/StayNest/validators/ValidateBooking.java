package com.example.StayNest.validators;

public class ValidateBooking extends RequestValidator{

    public void validationHandler (Validate validate) {
        if (validate.isBooking()) {
//            System.out.println("Booking succesful");
            super.validationHandler(validate, "Booking succesful");
        } else {
            System.out.println("Booking failed");
            validate.setValid(false);
        }
    }

    @Override
    protected void write(String message) {

    }


//    @Override
//    protected void write(String message) {
////        System.out.println("BOOKING: " + message);
//        if (validate.isBooking()) {
//            System.out.println("Booking succesful");
//            super.validateMessage(validate);
//        } else {
//            System.out.println("Booking failed");
//            validate.setValid(false);
//        }
//    }
}



// kontrollerar att requesten innehåller en giltig listning
//        if (bookingRequestDTO.getListing() != null && bookingRequestDTO.getListing().getId() != null) {
//            listing = listingRepository.findListingById(bookingRequestDTO.getListing().getId());
//            if (listing == null) {
//                throw new ResourceNotFoundException("Listing not found with id: " + bookingRequestDTO.getListing().getId());
//            }
//        } else {
//            throw new IllegalArgumentException("Listing ID is required");
//        }


//kan den validera en bokning

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