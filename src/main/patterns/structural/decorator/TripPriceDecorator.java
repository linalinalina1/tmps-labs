package patterns.structural.decorator;

import java.util.List;

public abstract class TripPriceDecorator implements TripPrice {
    protected final TripPrice wrapped;

    protected TripPriceDecorator(TripPrice wrapped) {
        if (wrapped == null) {
            throw new IllegalArgumentException("Price is required.");
        }

        this.wrapped = wrapped;
    }

    @Override
    public double getCost() {
        return wrapped.getCost();
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }

    @Override
    public List<PriceItem> getBreakdown() {
        return wrapped.getBreakdown();
    }
}
