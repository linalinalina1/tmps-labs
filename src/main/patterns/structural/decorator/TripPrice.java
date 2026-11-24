package patterns.structural.decorator;

import java.util.List;

public interface TripPrice {
    double getCost();
    String getDescription();
    List<PriceItem> getBreakdown();
}
