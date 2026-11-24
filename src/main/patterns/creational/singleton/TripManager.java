package patterns.creational.singleton;

import java.util.ArrayList;
import java.util.List;
import model.TripBooking;

public class TripManager {
    private static final TripManager INSTANCE = new TripManager();
    private final List<TripBooking> bookings = new ArrayList<>();

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
    }

    public List<TripBooking> getBookings() {
        return List.copyOf(bookings);
    }
}
