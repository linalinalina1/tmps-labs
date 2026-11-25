package patterns.structural.facade;

import java.util.List;
import patterns.behavioral.strategy.BookingPricingStrategy;
import model.Activity;
import model.Destination;
import model.TripBooking;

public interface TravelPlanner {
    void addDestination(Destination destination);
    List<Destination> getDestinations();
    double calculateTravelCost(Destination destination, boolean studentDiscount);
    Activity createActivity(int type, String name, double cost);
    void createTrip(Destination destination, List<Activity> activities,
                    double insuranceCost, double transferCost);
    List<TripBooking> getBookings();
    void setBookingPricingStrategy(BookingPricingStrategy strategy);
    String getBookingPricingStrategyName();
    double calculateBookingPrice(TripBooking booking);
}
