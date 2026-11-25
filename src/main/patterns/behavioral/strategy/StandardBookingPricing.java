package patterns.behavioral.strategy;

public class StandardBookingPricing implements BookingPricingStrategy {
    @Override
    public String getName() {
        return "Standard";
    }

    @Override
    public double calculate(double bookingCost) {
        return bookingCost;
    }
}
