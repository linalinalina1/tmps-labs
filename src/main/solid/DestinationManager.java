package solid;

import java.util.List;
import model.Destination;

public class DestinationManager {
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
