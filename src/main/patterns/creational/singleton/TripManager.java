package patterns.creational.singleton;

import java.util.ArrayList;
import java.util.List;
import model.TripBooking;
import patterns.behavioral.observer.BookingEvent;
import patterns.behavioral.observer.BookingEventType;
import patterns.behavioral.observer.BookingObserver;

public class TripManager {
    private static final TripManager INSTANCE = new TripManager();
    private final List<TripBooking> bookings = new ArrayList<>();
    private final List<BookingObserver> observers = new ArrayList<>();

    private TripManager() {
    }

    public static TripManager getInstance() {
        return INSTANCE;
    }

    public void addBooking(TripBooking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking is required.");
        }

        bookings.add(booking);
        notifyObservers(new BookingEvent(BookingEventType.CREATED, booking));
    }

    public void addObserver(BookingObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer is required.");
        }

        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(BookingObserver observer) {
        observers.remove(observer);
    }

    public boolean removeBooking(TripBooking booking) {
        if (bookings.remove(booking)) {
            notifyObservers(new BookingEvent(BookingEventType.REMOVED, booking));
            return true;
        }

        return false;
    }

    private void notifyObservers(BookingEvent event) {
        for (BookingObserver observer : List.copyOf(observers)) {
            observer.onBookingEvent(event);
        }
    }

    public List<TripBooking> getBookings() {
        return List.copyOf(bookings);
    }
}
