package patterns.structural.facade;

import java.util.List;
import model.Activity;
import model.Destination;
import model.Trip;
import model.TripBooking;
import patterns.creational.builder.TripBuilder;
import patterns.creational.factorymethod.ActivityCreator;
import patterns.creational.factorymethod.DiningCreator;
import patterns.creational.factorymethod.SightseeingCreator;
import patterns.creational.singleton.TripManager;
import patterns.structural.composite.ActivityGroup;
import patterns.structural.decorator.AirportTransferDecorator;
import patterns.structural.decorator.BasicTripPrice;
import patterns.structural.decorator.InsuranceDecorator;
import patterns.structural.decorator.TripPrice;
import solid.CostCalculator;
import solid.DestinationManager;
import solid.DestinationRepository;
import solid.InMemoryDestinationRepository;
import solid.StandardCostCalculator;
import solid.StudentCostCalculator;
import solid.TravelCostService;

public class TravelPlannerFacade implements TravelPlanner {
    private final DestinationManager destinationManager;
    private final TravelCostService costService;
    private final TripManager tripManager;
    private final ActivityCreator sightseeingCreator;
    private final ActivityCreator diningCreator;

    public TravelPlannerFacade() {
        DestinationRepository repository = new InMemoryDestinationRepository();
        this.destinationManager = new DestinationManager(repository);
        this.costService = new TravelCostService();
        this.tripManager = TripManager.getInstance();
        this.sightseeingCreator = new SightseeingCreator();
        this.diningCreator = new DiningCreator();
    }

    @Override
    public void addDestination(Destination destination) {
        destinationManager.addDestination(destination);
    }

    @Override
    public List<Destination> getDestinations() {
        return destinationManager.getDestinations();
    }

    @Override
    public double calculateTravelCost(Destination destination, boolean studentDiscount) {
        CostCalculator calculator = studentDiscount
                ? new StudentCostCalculator()
                : new StandardCostCalculator();

        return costService.calculateCost(destination.getEstimatedCost(), calculator);
    }

    @Override
    public Activity createActivity(int type, String name, double cost) {
        if (type == 1) {
            return sightseeingCreator.createActivity(name, cost);
        }

        if (type == 2) {
            return diningCreator.createActivity(name, cost);
        }

        throw new IllegalArgumentException("Invalid activity type.");
    }

    @Override
    public void createTrip(Destination destination, List<Activity> activities,
                           double insuranceCost, double transferCost) {
        if (activities == null || activities.isEmpty()) {
            throw new IllegalArgumentException("Add at least one activity.");
        }

        if (!Double.isFinite(insuranceCost) || insuranceCost < 0 ||
                !Double.isFinite(transferCost) || transferCost < 0) {
            throw new IllegalArgumentException("Service costs must be finite and non-negative.");
        }

        ActivityGroup tripActivities = new ActivityGroup("Trip activities");
        ActivityGroup sightseeingActivities = new ActivityGroup("Sightseeing");
        ActivityGroup diningActivities = new ActivityGroup("Dining");

        for (Activity activity : activities) {
            if (activity == null) {
                throw new IllegalArgumentException("Activity is required.");
            }

            if (activity.getType().equals("Sightseeing")) {
                sightseeingActivities.addActivity(activity);
            } else if (activity.getType().equals("Dining")) {
                diningActivities.addActivity(activity);
            } else {
                tripActivities.addActivity(activity);
            }
        }

        if (!sightseeingActivities.getActivities().isEmpty()) {
            tripActivities.addActivity(sightseeingActivities);
        }

        if (!diningActivities.getActivities().isEmpty()) {
            tripActivities.addActivity(diningActivities);
        }

        TripBuilder builder = new TripBuilder(destination);
        builder.addActivity(tripActivities);
        Trip trip = builder.build();
        TripPrice price = new BasicTripPrice(trip);

        if (insuranceCost > 0) {
            price = new InsuranceDecorator(price, insuranceCost);
        }

        if (transferCost > 0) {
            price = new AirportTransferDecorator(price, transferCost);
        }

        tripManager.addBooking(new TripBooking(trip, price));
    }

    @Override
    public List<TripBooking> getBookings() {
        return tripManager.getBookings();
    }
}
