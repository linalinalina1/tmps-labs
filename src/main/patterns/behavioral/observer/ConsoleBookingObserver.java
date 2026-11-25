package patterns.behavioral.observer;

import model.Destination;

public class ConsoleBookingObserver implements BookingObserver {
    @Override
    public void onBookingEvent(BookingEvent event) {
        Destination destination = event.booking().getTrip().getDestination();
        String action = event.type() == BookingEventType.CREATED ? "created" : "removed";

        System.out.printf(
                "Notification: Booking %s for %s, %s%n",
                action,
                destination.getCity(),
                destination.getCountry()
        );
    }
}
