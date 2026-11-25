package patterns.behavioral.observer;

public interface BookingObserver {
    void onBookingEvent(BookingEvent event);
}
