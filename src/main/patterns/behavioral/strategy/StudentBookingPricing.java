package patterns.behavioral.strategy;

public class StudentBookingPricing implements BookingPricingStrategy {
    @Override
    public String getName() {
        return "Student discount (10%)";
    }

    @Override
    public double calculate(double bookingCost) {
        return bookingCost * 0.90;
    }
}
