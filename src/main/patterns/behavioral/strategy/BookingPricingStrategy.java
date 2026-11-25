package patterns.behavioral.strategy;

public interface BookingPricingStrategy {
    String getName();
    double calculate(double bookingCost);
}
