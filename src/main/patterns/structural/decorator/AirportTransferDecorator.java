package patterns.structural.decorator;

import java.util.ArrayList;
import java.util.List;

public class AirportTransferDecorator extends TripPriceDecorator {
    private final double fee;

    public AirportTransferDecorator(TripPrice wrapped, double fee) {
        super(wrapped);
        if (!Double.isFinite(fee) || fee < 0) {
            throw new IllegalArgumentException("Invalid extra cost.");
        }

        this.fee = fee;
    }

    @Override
    public double getCost() {
        return super.getCost() + fee;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Airport transfer";
    }

    @Override
    public List<PriceItem> getBreakdown() {
        List<PriceItem> items = new ArrayList<>(super.getBreakdown());
        items.add(new PriceItem("Airport transfer", fee));
        return List.copyOf(items);
    }
}
