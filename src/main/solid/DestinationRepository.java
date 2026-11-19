package solid;

import java.util.List;
import model.Destination;

public interface DestinationRepository {
    void save(Destination destination);
    List<Destination> findAll();
}
