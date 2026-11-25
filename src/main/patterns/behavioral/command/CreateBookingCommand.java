package patterns.behavioral.command;

import model.TripBooking;
import patterns.creational.singleton.TripManager;

public class CreateBookingCommand implements BookingCommand {
    private final TripManager tripManager;
    private final TripBooking booking;
    private boolean executed;

    public CreateBookingCommand(TripManager tripManager, TripBooking booking) {
        if (tripManager == null || booking == null) {
            throw new IllegalArgumentException("Manager and booking are required.");
        }

        this.tripManager = tripManager;
        this.booking = booking;
    }

    @Override
    public void execute() {
        if (executed) {
            throw new IllegalStateException("Booking command already executed.");
        }

        tripManager.addBooking(booking);
        executed = true;
    }

    @Override
    public void undo() {
        if (!executed) {
            throw new IllegalStateException("Booking command has not been executed.");
        }

        if (!tripManager.removeBooking(booking)) {
            throw new IllegalStateException("Booking cannot be removed.");
        }

        executed = false;
    }
}
