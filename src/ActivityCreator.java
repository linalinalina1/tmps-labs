
interface Activity {
    String getName();
    String getType();
    double getCost();
}

class SightseeingActivity implements Activity {
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

class DiningActivity implements Activity {
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

abstract class ActivityCreator {
    public abstract Activity createActivity(String name, double cost);

    protected void validate(String name, double cost) {
        if (name == null || name.isBlank() ||
                !Double.isFinite(cost) || cost < 0) {
            throw new IllegalArgumentException("Invalid activity name or cost.");
        }
    }
}

class SightseeingCreator extends ActivityCreator {
    @Override
    public Activity createActivity(String name, double cost) {
        validate(name, cost);
        return new SightseeingActivity(name.trim(), cost);
    }
}

class DiningCreator extends ActivityCreator {
    @Override
    public Activity createActivity(String name, double cost) {
        validate(name, cost);
        return new DiningActivity(name.trim(), cost);
    }
}
