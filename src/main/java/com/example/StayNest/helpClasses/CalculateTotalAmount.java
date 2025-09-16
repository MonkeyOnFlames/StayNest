package com.example.StayNest.helpClasses;

import com.example.StayNest.models.Booking;

import java.time.temporal.ChronoUnit;

public class CalculateTotalAmount {

    // Metod som gör att priset på en bokning räknas ut automatiskt
    public Double calculateTotalAmount (Booking booking) {
        // ChronoUnit.DAYS.between beräknar antalet hela dagar mellan två datum
        // den räknar INTE med slutdatumet i resultatet, endast hela dagar mellan datumen...
        // exempel: mellan 2025-06-05 och 2025-06-10 blir resultatet 5 dagar
        // lägger till 1 till slutdatumet så att både check-in och check-out dagen räknas med
        long daysBetween = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate()) + 1;
        // totalpriset blir listningens pris per natt multiplicerat med antalet nätter
        double totalAmount = daysBetween * booking.getListing().getPrice();
        return totalAmount;
    }
}
