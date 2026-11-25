package patterns.behavioral.observer;

import model.TripBooking;

public record BookingEvent(BookingEventType type, TripBooking booking) {
    public BookingEvent {
        if (type == null || booking == null) {
            throw new IllegalArgumentException("Event type and booking are required.");
        }
    }
}
