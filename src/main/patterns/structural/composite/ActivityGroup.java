package patterns.structural.composite;

import java.util.ArrayList;
import java.util.List;
import model.Activity;

public class ActivityGroup implements Activity {
    private final String name;
    private final List<Activity> activities = new ArrayList<>();

    public ActivityGroup(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name is required.");
        }
        this.name = name.trim();
    }

    public void addActivity(Activity activity) {
        if (activity == null || contains(activity, this)) {
            throw new IllegalArgumentException("Invalid or circular activity group.");
        }
        activities.add(activity);
    }

    private static boolean contains(Activity current, Activity target) {
        if (current == target) {
            return true;
        }

        if (current instanceof ActivityGroup group) {
            for (Activity child : group.activities) {
                if (contains(child, target)) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Activity> getActivities() {
        return List.copyOf(activities);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "Group";
    }

    @Override
    public double getCost() {
        double total = 0;

        for (Activity activity : activities) {
            total += activity.getCost();
        }

        return total;
    }
}
