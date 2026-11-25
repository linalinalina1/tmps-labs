package patterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryObserver implements BookingObserver {
    private final List<BookingEvent> events = new ArrayList<>();

    @Override
    public void onBookingEvent(BookingEvent event) {
        events.add(event);
    }

    public List<BookingEvent> getEvents() {
        return List.copyOf(events);
    }
}
