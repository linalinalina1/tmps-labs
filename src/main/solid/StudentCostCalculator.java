package solid;

public class StudentCostCalculator implements CostCalculator {
    @Override
    public double calculate(double baseCost) {
        return baseCost * 0.90;
    }
}
