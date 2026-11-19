package patterns.creational;

import model.Activity;
import model.SightseeingActivity;

public class SightseeingCreator extends ActivityCreator {
    @Override
    public Activity createActivity(String name, double cost) {
        validate(name, cost);
        return new SightseeingActivity(name.trim(), cost);
    }
}
