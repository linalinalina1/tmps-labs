package patterns.creational.factorymethod;

import model.Activity;
import model.DiningActivity;

public class DiningCreator extends ActivityCreator {
    @Override
    public Activity createActivity(String name, double cost) {
        validate(name, cost);
        return new DiningActivity(name.trim(), cost);
    }
}
