import java.util.ArrayList;
import java.util.List;

interface DestinationRepository {
    void save(Destination destination);
    List<Destination> findAll();
}

class InMemoryDestinationRepository implements DestinationRepository {
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
