package solid;

public class TravelCostService {
    public double calculateCost(double baseCost, CostCalculator calculator) {
        return calculator.calculate(baseCost);
    }
}
