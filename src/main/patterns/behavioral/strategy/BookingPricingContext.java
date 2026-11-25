package patterns.behavioral.strategy;

import model.TripBooking;

public class BookingPricingContext {
    private BookingPricingStrategy strategy;

    public BookingPricingContext(BookingPricingStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(BookingPricingStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Pricing strategy is required.");
        }

        this.strategy = strategy;
    }

    public String getStrategyName() {
        return strategy.getName();
    }

    public double calculatePrice(TripBooking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking is required.");
        }

        return strategy.calculate(booking.getPrice().getCost());
    }
}
