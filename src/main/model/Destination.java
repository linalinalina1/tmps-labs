package model;

public class Destination {
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
