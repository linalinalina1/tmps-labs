package patterns.creational.builder;

import java.util.ArrayList;
import java.util.List;
import model.Activity;
import model.Destination;
import model.Trip;

public class TripBuilder {
    private final Destination destination;
    private final List<Activity> activities = new ArrayList<>();

    public TripBuilder(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is required.");
        }

        this.destination = destination;
    }

    public TripBuilder addActivity(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity is required.");
        }

        activities.add(activity);
        return this;
    }

    public Trip build() {
        if (activities.isEmpty()) {
            throw new IllegalStateException(
                    "A trip must have at least one activity."
            );
        }

        return new Trip(destination, activities);
    }
}
