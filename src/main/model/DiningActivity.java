package model;

public class DiningActivity implements Activity {
    private final String name;
    private final double cost;

    public DiningActivity(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "Dining";
    }

    @Override
    public double getCost() {
        return cost;
    }
}
