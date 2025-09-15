package com.example.StayNest.factories;

import com.example.StayNest.dto.BookingResponseDTO;
import com.example.StayNest.models.Booking;

public class ConvertToBookingResponseDTO {

    public static BookingResponseDTO convertToBookingResponseDTO(Booking booking) {
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
