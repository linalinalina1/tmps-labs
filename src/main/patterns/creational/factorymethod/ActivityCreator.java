package patterns.creational.factorymethod;

import model.Activity;

public abstract class ActivityCreator {
    public abstract Activity createActivity(String name, double cost);

    protected void validate(String name, double cost) {
        if (name == null || name.isBlank() ||
                !Double.isFinite(cost) || cost < 0) {
            throw new IllegalArgumentException("Invalid activity name or cost.");
        }
    }
}
