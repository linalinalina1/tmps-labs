
import java.util.ArrayList;
import java.util.List;

class Trip {
    private final Destination destination;
    private final List<Activity> activities;

    private Trip(Builder builder) {
        this.destination = builder.destination;
        this.activities = List.copyOf(builder.activities);
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

    static class Builder {
        private final Destination destination;
        private final List<Activity> activities = new ArrayList<>();

        public Builder(Destination destination) {
            if (destination == null) {
                throw new IllegalArgumentException("Destination is required.");
            }

            this.destination = destination;
        }

        public Builder addActivity(Activity activity) {
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

            return new Trip(this);
        }
    }
}
