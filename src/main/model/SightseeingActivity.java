package model;

public class SightseeingActivity implements Activity {
    private final String name;
    private final double cost;

    public SightseeingActivity(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "Sightseeing";
    }

    @Override
    public double getCost() {
        return cost;
    }
}
