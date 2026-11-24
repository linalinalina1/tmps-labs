package tests;

import java.util.List;
import model.Destination;
import model.DiningActivity;
import model.Trip;
import patterns.creational.builder.TripBuilder;
import patterns.structural.decorator.AirportTransferDecorator;
import patterns.structural.decorator.BasicTripPrice;
import patterns.structural.decorator.InsuranceDecorator;
import patterns.structural.decorator.PriceItem;
import patterns.structural.decorator.TripPrice;

public class DecoratorTests {
    private static Trip sampleTrip() {
        TripBuilder builder = new TripBuilder(new Destination("Rome", "Italy", 200));
        builder.addActivity(new DiningActivity("Pizza", 30));
        return builder.build();
    }

    public static void testStackingAndPriceBreakdown() {
        Trip trip = sampleTrip();
        TripPrice price = new BasicTripPrice(trip);
        price = new InsuranceDecorator(price, 100);
        price = new AirportTransferDecorator(price, 50);

        Checks.equal(380, price.getCost());
        List<PriceItem> breakdown = price.getBreakdown();
        Checks.isTrue(breakdown.size() == 3, "Expected 3 pricing items");
        Checks.equal(230, breakdown.get(0).cost());
        Checks.equal(100, breakdown.get(1).cost());
        Checks.equal(50, breakdown.get(2).cost());
    }

    public static void testWrappingDoesNotChangeBase() {
        Trip trip = sampleTrip();
        TripPrice base = new BasicTripPrice(trip);
        TripPrice first = new InsuranceDecorator(
                new AirportTransferDecorator(base, 50), 100);
        TripPrice second = new AirportTransferDecorator(
                new InsuranceDecorator(base, 100), 50);

        Checks.equal(230, base.getCost());
        Checks.equal(230, trip.getTotalCost());
        Checks.equal(380, first.getCost());
        Checks.equal(first.getCost(), second.getCost());
    }
}
