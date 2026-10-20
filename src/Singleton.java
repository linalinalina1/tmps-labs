
import java.util.ArrayList;
import java.util.List;

class TripManager {
    private static final TripManager INSTANCE = new TripManager();
    private final List<Trip> trips = new ArrayList<>();

    private TripManager() {
    }

    public static TripManager getInstance() {
        return INSTANCE;
    }

    public void addTrip(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip is required.");
        }

        trips.add(trip);
    }

    public List<Trip> getTrips() {
        return List.copyOf(trips);
    }
}
