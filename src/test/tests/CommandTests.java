package tests;

import java.util.List;
import model.Destination;
import model.Trip;
import model.TripBooking;
import patterns.behavioral.command.BookingCommandInvoker;
import patterns.behavioral.command.CreateBookingCommand;
import patterns.behavioral.observer.BookingEventType;
import patterns.behavioral.observer.BookingHistoryObserver;
import patterns.creational.builder.TripBuilder;
import patterns.creational.singleton.TripManager;
import patterns.structural.decorator.BasicTripPrice;
import patterns.structural.facade.TravelPlanner;
import patterns.structural.facade.TravelPlannerFacade;

public class CommandTests {
    public static void testFacadeCreationCanBeUndoneAndNotifiesObservers() {
        TravelPlanner planner = new TravelPlannerFacade();
        BookingHistoryObserver observer = new BookingHistoryObserver();
        planner.addBookingObserver(observer);
        int before = planner.getBookings().size();

        planner.createTrip(new Destination("Porto", "Portugal", 100),
                List.of(planner.createActivity(1, "Walk", 20)), 10, 0);

        Checks.equal(before + 1, planner.getBookings().size());
        Checks.isTrue(planner.undoLastBooking(), "Undo should succeed.");
        Checks.equal(before, planner.getBookings().size());
        Checks.equal(2, observer.getEvents().size());
        Checks.isTrue(observer.getEvents().get(0).type() == BookingEventType.CREATED,
                "Command execution should notify observers.");
        Checks.isTrue(observer.getEvents().get(1).type() == BookingEventType.REMOVED,
                "Command undo should notify observers.");
        Checks.isTrue(!planner.undoLastBooking(), "Undo history should be empty.");
        planner.removeBookingObserver(observer);
    }

    public static void testInvokerUndoesCommandsInReverseOrder() {
        TripManager manager = TripManager.getInstance();
        BookingCommandInvoker invoker = new BookingCommandInvoker();
        TripBooking first = makeBooking("A");
        TripBooking second = makeBooking("B");
        int before = manager.getBookings().size();

        invoker.execute(new CreateBookingCommand(manager, first));
        invoker.execute(new CreateBookingCommand(manager, second));
        Checks.equal(before + 2, manager.getBookings().size());

        Checks.isTrue(invoker.undoLast(), "Second command should be undone.");
        Checks.equal(before + 1, manager.getBookings().size());
        Checks.isTrue(manager.getBookings().contains(first), "First booking should remain.");
        Checks.isTrue(!manager.getBookings().contains(second), "Second booking should be removed.");

        Checks.isTrue(invoker.undoLast(), "First command should be undone.");
        Checks.equal(before, manager.getBookings().size());
        Checks.isTrue(!invoker.undoLast(), "No commands should remain.");
    }

    private static TripBooking makeBooking(String city) {
        Trip trip = new TripBuilder(new Destination(city, "Test", 100))
                .addActivity(new model.SightseeingActivity("Tour", 10))
                .build();
        return new TripBooking(trip, new BasicTripPrice(trip));
    }
}
