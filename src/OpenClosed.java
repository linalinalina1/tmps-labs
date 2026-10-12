
interface CostCalculator {
    double calculate(double baseCost);
}

class StandardCostCalculator implements CostCalculator {
    @Override
    public double calculate(double baseCost) {
        return baseCost;
    }
}

class StudentCostCalculator implements CostCalculator {
    @Override
    public double calculate(double baseCost) {
        return baseCost * 0.90;
    }
}

class TravelCostService {
    public double calculateCost(double baseCost, CostCalculator calculator) {
        return calculator.calculate(baseCost);
    }
}
