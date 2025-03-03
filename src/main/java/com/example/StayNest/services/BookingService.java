package com.example.StayNest.services;

import com.example.StayNest.models.Booking;
import com.example.StayNest.models.Listing;
import com.example.StayNest.repositories.BookingRepository;
import com.example.StayNest.repositories.ListingRepository;
import com.example.StayNest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, ListingRepository listingRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    public Booking createBooking(@Valid Booking booking) {

        validateBooking(booking);

//        SimpleDateFormat startDate = new SimpleDateFormat("YYYY-MM-DD");
//        SimpleDateFormat endDate = new SimpleDateFormat("YYYY-MM-DD");


        return bookingRepository.save(booking);
    }

    public Booking getBookingsById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with id " + id));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


//PATCH
   public Booking updateBooking(String id, Booking booking){
       Booking existingBooking = bookingRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Booking not found with id " + id));

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

       return bookingRepository.save(existingBooking);
    }

    public void deleteBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));

        bookingRepository.delete(booking);
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

}
