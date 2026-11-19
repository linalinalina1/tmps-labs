package model;

import java.util.List;

public class Trip {
    private final Destination destination;
    private final List<Activity> activities;

    public Trip(Destination destination, List<Activity> activities) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is required.");
        }

        if (activities == null || activities.isEmpty()) {
            throw new IllegalStateException(
                    "A trip must have at least one activity."
            );
        }

        if (activities.contains(null)) {
            throw new IllegalArgumentException("Activity is required.");
        }

        this.destination = destination;
        this.activities = List.copyOf(activities);
    }

    public Destination getDestination() {
        return destination;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public double getTotalCost() {
        double total = destination.getEstimatedCost();

        for (Activity activity : activities) {
            total += activity.getCost();
        }

        return total;
    }
}
