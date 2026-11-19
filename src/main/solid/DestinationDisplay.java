package solid;

import java.util.List;
import model.Destination;

public class DestinationDisplay {
    public void showDestinations(List<Destination> destinations) {
        if (destinations.isEmpty()) {
            System.out.println("No destinations added yet.");
            return;
        }

        System.out.println("\nYour destinations:");

        for (int i = 0; i < destinations.size(); i++) {
            Destination destination = destinations.get(i);

            System.out.printf(
                    "%d. %s, %s - Estimated cost: %.2f%n",
                    i + 1,
                    destination.getCity(),
                    destination.getCountry(),
                    destination.getEstimatedCost()
            );
        }
    }
}
