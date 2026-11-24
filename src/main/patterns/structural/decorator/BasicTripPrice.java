package patterns.structural.decorator;

import java.util.List;
import model.Trip;

public class BasicTripPrice implements TripPrice {
    private final Trip trip;

    public BasicTripPrice(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip is required.");
        }

        this.trip = trip;
    }

    @Override
    public double getCost() {
        return trip.getTotalCost();
    }

    @Override
    public String getDescription() {
        return "Base trip";
    }

    @Override
    public List<PriceItem> getBreakdown() {
        return List.of(new PriceItem("Base trip cost", getCost()));
    }
}
