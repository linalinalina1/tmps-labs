package tests;

import java.util.List;
import model.Destination;
import model.TripBooking;
import patterns.behavioral.observer.BookingEventType;
import patterns.behavioral.observer.BookingHistoryObserver;
import patterns.creational.singleton.TripManager;
import patterns.structural.facade.TravelPlanner;
import patterns.structural.facade.TravelPlannerFacade;

public class ObserverTests {
    public static void testSubscribedObserversReceiveCreation() {
        TravelPlanner planner = new TravelPlannerFacade();
        BookingHistoryObserver history = new BookingHistoryObserver();
        planner.addBookingObserver(history);
        int before = planner.getBookings().size();

        planner.createTrip(new Destination("Oslo", "Norway", 100),
                List.of(planner.createActivity(1, "Museum", 20)), 0, 0);

        TripBooking booking = planner.getBookings().get(before);
        Checks.equal(1, history.getEvents().size());
        Checks.isTrue(history.getEvents().get(0).type() == BookingEventType.CREATED,
                "A creation event must be delivered.");
        Checks.isTrue(history.getEvents().get(0).booking() == booking,
                "The event must reference the new booking.");

        planner.removeBookingObserver(history);
        TripManager.getInstance().removeBooking(booking);
    }

    public static void testUnsubscribedObserversStopReceivingEvents() {
        TravelPlanner planner = new TravelPlannerFacade();
        BookingHistoryObserver history = new BookingHistoryObserver();
        planner.addBookingObserver(history);
        int before = planner.getBookings().size();

        planner.createTrip(new Destination("Lyon", "France", 150),
                List.of(planner.createActivity(2, "Lunch", 10)), 0, 0);
        TripBooking first = planner.getBookings().get(before);
        planner.removeBookingObserver(history);

        planner.createTrip(new Destination("Nice", "France", 170),
                List.of(planner.createActivity(1, "Tour", 10)), 0, 0);
        TripBooking second = planner.getBookings().get(before + 1);

        Checks.equal(1, history.getEvents().size());
        Checks.isTrue(history.getEvents().get(0).booking() == first,
                "Only the first event should be recorded.");
        TripManager.getInstance().removeBooking(first);
        TripManager.getInstance().removeBooking(second);
    }
}
