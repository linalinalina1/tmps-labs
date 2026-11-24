package tests;

import java.util.List;
import model.Activity;
import model.Destination;
import model.TripBooking;
import patterns.structural.composite.ActivityGroup;
import patterns.structural.facade.TravelPlanner;
import patterns.structural.facade.TravelPlannerFacade;

public class FacadeTests {
    public static void testFullTripCreation() {
        TravelPlanner planner = new TravelPlannerFacade();
        Destination destination = new Destination("Rome", "Italy", 200);
        planner.addDestination(destination);

        Activity sightseeing = planner.createActivity(1, "Trevi", 10);
        Activity dining = planner.createActivity(2, "Pizza", 20);
        int previousCount = planner.getBookings().size();

        planner.createTrip(destination, List.of(sightseeing, dining), 100, 50);

        Checks.isTrue(planner.getBookings().size() == previousCount + 1,
                "New booking must be stored");
        TripBooking booking = planner.getBookings().get(previousCount);
        Checks.equal(380, booking.getPrice().getCost());
        Checks.isTrue(booking.getTrip().getActivities().get(0) instanceof ActivityGroup,
                "Trip must use a Composite group");
        Checks.isTrue(booking.getPrice().getBreakdown().size() == 3,
                "Trip must have a decorated price breakdown");
    }

    public static void testDestinationCostAndNoExtras() {
        TravelPlanner planner = new TravelPlannerFacade();
        Destination destination = new Destination("Paris", "France", 600);
        planner.addDestination(destination);

        Checks.isTrue(planner.getDestinations().contains(destination),
                "Saved destination must be accessible");
        Checks.equal(600, planner.calculateTravelCost(destination, false));
        Checks.equal(540, planner.calculateTravelCost(destination, true));

        int previousCount = planner.getBookings().size();
        planner.createTrip(destination,
                List.of(planner.createActivity(1, "Museum", 30)), 0, 0);

        TripBooking booking = planner.getBookings().get(previousCount);
        Checks.equal(630, booking.getPrice().getCost());
        Checks.isTrue(booking.getPrice().getBreakdown().size() == 1,
                "No decorators should be applied for zero fees");
    }
}
