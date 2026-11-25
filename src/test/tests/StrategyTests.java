package tests;

import java.util.List;
import model.Destination;
import model.TripBooking;
import patterns.behavioral.strategy.BookingPricingContext;
import patterns.behavioral.strategy.StandardBookingPricing;
import patterns.behavioral.strategy.StudentBookingPricing;
import patterns.structural.facade.TravelPlanner;
import patterns.structural.facade.TravelPlannerFacade;

public class StrategyTests {
    public static void testStrategiesProduceExpectedPrices() {
        TravelPlanner planner = new TravelPlannerFacade();
        Destination destination = new Destination("Rome", "Italy", 200);
        int previousCount = planner.getBookings().size();

        planner.createTrip(destination,
                List.of(planner.createActivity(1, "Museum", 30)), 50, 20);

        TripBooking booking = planner.getBookings().get(previousCount);
        BookingPricingContext context = new BookingPricingContext(new StandardBookingPricing());

        Checks.equal(300, context.calculatePrice(booking));
        context.setStrategy(new StudentBookingPricing());
        Checks.equal(270, context.calculatePrice(booking));
        Checks.equal(300, booking.getPrice().getCost());
    }

    public static void testFacadeSwitchesStrategiesAtRuntime() {
        TravelPlanner planner = new TravelPlannerFacade();
        Destination destination = new Destination("Paris", "France", 100);
        int previousCount = planner.getBookings().size();

        planner.createTrip(destination,
                List.of(planner.createActivity(2, "Lunch", 20)), 0, 0);

        TripBooking booking = planner.getBookings().get(previousCount);
        planner.setBookingPricingStrategy(new StandardBookingPricing());
        Checks.equal(120, planner.calculateBookingPrice(booking));
        planner.setBookingPricingStrategy(new StudentBookingPricing());
        Checks.equal(108, planner.calculateBookingPrice(booking));
        planner.setBookingPricingStrategy(new StandardBookingPricing());
        Checks.equal(120, planner.calculateBookingPrice(booking));
    }
}
