package model;

import patterns.structural.decorator.TripPrice;

public class TripBooking {
    private final Trip trip;
    private final TripPrice price;

    public TripBooking(Trip trip, TripPrice price) {
        if (trip == null || price == null) {
            throw new IllegalArgumentException("Trip and price are required.");
        }

        this.trip = trip;
        this.price = price;
    }

    public Trip getTrip() {
        return trip;
    }

    public TripPrice getPrice() {
        return price;
    }
}
