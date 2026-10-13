
import java.util.List;

class Destination {
    private final String city;
    private final String country;
    private final double estimatedCost;

    public Destination(String city, String country, double estimatedCost) {
        this.city = city;
        this.country = country;
        this.estimatedCost = estimatedCost;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }
}

class DestinationManager {
    private final DestinationRepository repository;

    public DestinationManager(DestinationRepository repository) {
        this.repository = repository;
    }

    public void addDestination(Destination destination) {
        repository.save(destination);
    }

    public List<Destination> getDestinations() {
        return repository.findAll();
    }
}

class DestinationDisplay {
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
