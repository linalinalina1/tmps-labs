package solid;

import java.util.ArrayList;
import java.util.List;
import model.Destination;

public class InMemoryDestinationRepository implements DestinationRepository {
    private final List<Destination> destinations = new ArrayList<>();

    @Override
    public void save(Destination destination) {
        destinations.add(destination);
    }

    @Override
    public List<Destination> findAll() {
        return List.copyOf(destinations);
    }
}
