package patterns.structural.decorator;

import java.util.ArrayList;
import java.util.List;

public class InsuranceDecorator extends TripPriceDecorator {
    private final double fee;

    public InsuranceDecorator(TripPrice wrapped, double fee) {
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
        return super.getDescription() + ", Travel insurance";
    }

    @Override
    public List<PriceItem> getBreakdown() {
        List<PriceItem> items = new ArrayList<>(super.getBreakdown());
        items.add(new PriceItem("Travel insurance", fee));
        return List.copyOf(items);
    }
}
