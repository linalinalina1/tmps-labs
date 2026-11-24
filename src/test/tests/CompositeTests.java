package tests;

import model.Activity;
import model.DiningActivity;
import model.SightseeingActivity;
import patterns.structural.composite.ActivityGroup;

public class CompositeTests {
    public static void testLeavesAndGroupsShareInterface() {
        Activity leaf = new SightseeingActivity("Trevi", 10);
        ActivityGroup group = new ActivityGroup("Sightseeing");
        group.addActivity(leaf);

        Activity composite = group;
        Checks.equal(10, leaf.getCost());
        Checks.equal(10, composite.getCost());
        Checks.isTrue(group.getActivities().contains(leaf),
                "Group must contain the activity");
    }

    public static void testNestedGroupsCalculateTotal() {
        ActivityGroup sightseeing = new ActivityGroup("Sightseeing");
        sightseeing.addActivity(new SightseeingActivity("Trevi", 10));
        sightseeing.addActivity(new SightseeingActivity("Colosseum", 29));

        ActivityGroup dining = new ActivityGroup("Dining");
        dining.addActivity(new DiningActivity("Pizza", 10));
        dining.addActivity(new DiningActivity("Pasta", 13));

        ActivityGroup all = new ActivityGroup("Trip activities");
        all.addActivity(sightseeing);
        all.addActivity(dining);

        Checks.equal(39, sightseeing.getCost());
        Checks.equal(23, dining.getCost());
        Checks.equal(62, all.getCost());
        Checks.isTrue(all.getActivities().size() == 2,
                "Expected two category groups");
    }
}
