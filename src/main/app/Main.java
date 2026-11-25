package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import patterns.behavioral.observer.BookingObserver;
import patterns.behavioral.observer.ConsoleBookingObserver;
import patterns.behavioral.strategy.StandardBookingPricing;
import patterns.behavioral.strategy.StudentBookingPricing;
import model.Activity;
import model.Destination;
import model.Trip;
import model.TripBooking;
import solid.DestinationDisplay;
import patterns.structural.composite.ActivityGroup;
import patterns.structural.decorator.PriceItem;
import patterns.structural.facade.TravelPlanner;
import patterns.structural.facade.TravelPlannerFacade;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TravelPlanner planner = new TravelPlannerFacade();
        BookingObserver consoleObserver = new ConsoleBookingObserver();
        planner.addBookingObserver(consoleObserver);
        boolean notificationsEnabled = true;
        DestinationDisplay display = new DestinationDisplay();

        int choice = -1;

        do {
            System.out.println("\nTravel Planner");
            System.out.println("1. Add destination");
            System.out.println("2. View destinations");
            System.out.println("3. Calculate travel cost");
            System.out.println("4. Create trip");
            System.out.println("5. View trips");
            System.out.println("6. Calculate booking price");
            System.out.println("7. Toggle booking notifications");
            System.out.println("8. Undo last trip creation");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter city: ");
                    String city = scanner.nextLine().trim();

                    System.out.print("Enter country: ");
                    String country = scanner.nextLine().trim();

                    if (city.isEmpty() || country.isEmpty()) {
                        System.out.println("City and country cannot be empty.");
                        break;
                    }

                    System.out.print("Enter estimated cost: ");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid cost. Please enter a number.");
                        scanner.nextLine();
                        break;
                    }

                    double cost = scanner.nextDouble();
                    scanner.nextLine();

                    if (!Double.isFinite(cost) || cost < 0) {
                        System.out.println("Cost must be a finite, non-negative number.");
                        break;
                    }

                    planner.addDestination(
                            new Destination(city, country, cost)
                    );

                    System.out.println("Destination added successfully!");
                    break;

                case 2:
                    display.showDestinations(planner.getDestinations());
                    break;

                case 3:
                    if (planner.getDestinations().isEmpty()) {
                        System.out.println("No destinations available.");
                        break;
                    }

                    display.showDestinations(planner.getDestinations());
                    System.out.print("Choose destination number: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid destination number.");
                        scanner.nextLine();
                        break;
                    }

                    int destinationNumber = scanner.nextInt();
                    scanner.nextLine();

                    if (destinationNumber < 1 ||
                            destinationNumber > planner.getDestinations().size()) {
                        System.out.println("Invalid destination number.");
                        break;
                    }

                    Destination selectedDestination =
                            planner.getDestinations().get(destinationNumber - 1);

                    System.out.println("1. Standard cost");
                    System.out.println("2. Student discount (10%)");
                    System.out.print("Choose calculation method: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid calculation method.");
                        scanner.nextLine();
                        break;
                    }

                    int calculationMethod = scanner.nextInt();
                    scanner.nextLine();

                    if (calculationMethod != 1 && calculationMethod != 2) {
                        System.out.println("Invalid calculation method.");
                        break;
                    }

                    double finalCost = planner.calculateTravelCost(
                            selectedDestination,
                            calculationMethod == 2
                    );

                    System.out.printf("Final travel cost: %.2f%n", finalCost);
                    break;

                case 4:
                    if (planner.getDestinations().isEmpty()) {
                        System.out.println("Add a destination before creating a trip.");
                        break;
                    }

                    display.showDestinations(planner.getDestinations());
                    System.out.print("Choose destination number: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid destination number.");
                        scanner.nextLine();
                        break;
                    }

                    int tripDestinationNumber = scanner.nextInt();
                    scanner.nextLine();

                    if (tripDestinationNumber < 1 ||
                            tripDestinationNumber > planner.getDestinations().size()) {
                        System.out.println("Invalid destination number.");
                        break;
                    }

                    Destination tripDestination =
                            planner.getDestinations().get(tripDestinationNumber - 1);

                    List<Activity> activities = new ArrayList<>();
                    int activityCount = 0;
                    boolean addingActivities = true;

                    while (addingActivities) {
                        System.out.println("\nChoose activity type:");
                        System.out.println("1. Sightseeing");
                        System.out.println("2. Dining");
                        System.out.println("0. Finish adding activities");
                        System.out.print("Choose an option: ");

                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid activity type.");
                            scanner.nextLine();
                            continue;
                        }

                        int activityType = scanner.nextInt();
                        scanner.nextLine();

                        if (activityType == 0) {
                            if (activityCount == 0) {
                                System.out.println("Add at least one activity.");
                                continue;
                            }

                            addingActivities = false;
                            continue;
                        }

                        if (activityType != 1 && activityType != 2) {
                            System.out.println("Invalid activity type.");
                            continue;
                        }

                        System.out.print("Enter activity name: ");
                        String activityName = scanner.nextLine().trim();

                        if (activityName.isEmpty()) {
                            System.out.println("Activity name cannot be empty.");
                            continue;
                        }

                        System.out.print("Enter activity cost: ");

                        if (!scanner.hasNextDouble()) {
                            System.out.println("Invalid activity cost.");
                            scanner.nextLine();
                            continue;
                        }

                        double activityCost = scanner.nextDouble();
                        scanner.nextLine();

                        if (!Double.isFinite(activityCost) || activityCost < 0) {
                            System.out.println(
                                    "Activity cost must be a finite, non-negative number."
                            );
                            continue;
                        }

                        Activity activity = planner.createActivity(
                                activityType,
                                activityName,
                                activityCost
                        );

                        activities.add(activity);
                        activityCount++;

                        System.out.println("Activity added successfully!");
                    }

                    System.out.print("Enter travel insurance cost (0 to skip): ");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid insurance cost.");
                        scanner.nextLine();
                        break;
                    }

                    double insuranceCost = scanner.nextDouble();
                    scanner.nextLine();

                    if (!Double.isFinite(insuranceCost) || insuranceCost < 0) {
                        System.out.println("Insurance cost must be a finite, non-negative number.");
                        break;
                    }

                    System.out.print("Enter airport transfer cost (0 to skip): ");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid airport transfer cost.");
                        scanner.nextLine();
                        break;
                    }

                    double transferCost = scanner.nextDouble();
                    scanner.nextLine();

                    if (!Double.isFinite(transferCost) || transferCost < 0) {
                        System.out.println("Airport transfer cost must be a finite, non-negative number.");
                        break;
                    }

                    planner.createTrip(
                            tripDestination,
                            activities,
                            insuranceCost,
                            transferCost
                    );

                    System.out.println("Trip created successfully!");
                    break;

                case 5:
                    if (planner.getBookings().isEmpty()) {
                        System.out.println("No trips created yet.");
                        break;
                    }

                    System.out.println("\nYour trips:");
                    int tripNumber = 1;

                    for (TripBooking booking : planner.getBookings()) {
                        Trip savedTrip = booking.getTrip();
                        Destination destination = savedTrip.getDestination();

                        System.out.printf(
                                "%d. %s, %s%n",
                                tripNumber++,
                                destination.getCity(),
                                destination.getCountry()
                        );

                        System.out.printf(
                                "   Destination cost: %.2f%n",
                                destination.getEstimatedCost()
                        );

                        double activitiesCost = 0;
                        for (Activity savedActivity : savedTrip.getActivities()) {
                            activitiesCost += savedActivity.getCost();
                        }

                        System.out.printf(
                                "   Activities: %.2f%n",
                                activitiesCost
                        );

                        for (Activity savedActivity : savedTrip.getActivities()) {

                            if (savedActivity instanceof ActivityGroup group) {
                                for (Activity child : group.getActivities()) {
                                    showActivity(child, "     ");
                                }
                            } else {
                                showActivity(savedActivity, "     ");
                            }
                        }

                        for (PriceItem item : booking.getPrice().getBreakdown()) {
                            System.out.printf(
                                    "   %s: %.2f%n",
                                    item.description(),
                                    item.cost()
                            );
                        }

                        System.out.printf(
                                "   Total trip cost: %.2f%n",
                                booking.getPrice().getCost()
                        );
                    }
                    break;

                case 6:
                    if (planner.getBookings().isEmpty()) {
                        System.out.println("No trips created yet.");
                        break;
                    }

                    System.out.println("\nChoose a booking:");

                    for (int i = 0; i < planner.getBookings().size(); i++) {
                        Destination bookingDestination =
                                planner.getBookings().get(i).getTrip().getDestination();

                        System.out.printf(
                                "%d. %s, %s%n",
                                i + 1,
                                bookingDestination.getCity(),
                                bookingDestination.getCountry()
                        );
                    }

                    System.out.print("Choose booking number: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid booking number.");
                        scanner.nextLine();
                        break;
                    }

                    int bookingNumber = scanner.nextInt();
                    scanner.nextLine();

                    if (bookingNumber < 1 || bookingNumber > planner.getBookings().size()) {
                        System.out.println("Invalid booking number.");
                        break;
                    }

                    TripBooking selectedBooking = planner.getBookings().get(bookingNumber - 1);
                    boolean choosingPricing = true;

                    while (choosingPricing) {
                        System.out.println("\nChoose booking pricing strategy:");
                        System.out.println("1. Standard pricing");
                        System.out.println("2. Student discount (10%)");
                        System.out.println("0. Return to main menu");
                        System.out.print("Choose an option: ");

                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid calculation method.");
                            scanner.nextLine();
                            continue;
                        }

                        int pricingChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (pricingChoice == 0) {
                            choosingPricing = false;
                        } else if (pricingChoice == 1) {
                            planner.setBookingPricingStrategy(new StandardBookingPricing());
                        } else if (pricingChoice == 2) {
                            planner.setBookingPricingStrategy(new StudentBookingPricing());
                        } else {
                            System.out.println("Invalid calculation method.");
                            continue;
                        }

                        if (choosingPricing) {
                            System.out.printf(
                                    "%s price: %.2f%n",
                                    planner.getBookingPricingStrategyName(),
                                    planner.calculateBookingPrice(selectedBooking)
                            );
                        }
                    }
                    break;

                case 7:
                    if (notificationsEnabled) {
                        planner.removeBookingObserver(consoleObserver);
                        notificationsEnabled = false;
                        System.out.println("Booking notifications disabled.");
                    } else {
                        planner.addBookingObserver(consoleObserver);
                        notificationsEnabled = true;
                        System.out.println("Booking notifications enabled.");
                    }
                    break;

                case 8:
                    if (planner.undoLastBooking()) {
                        System.out.println("Last trip creation undone.");
                    } else {
                        System.out.println("No trip creation to undo.");
                    }
                    break;

                case 0:
                    System.out.println("Program finished.");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
    private static void showActivity(Activity activity, String indent) {
        System.out.printf(
                "%s%s - %.2f%n",
                indent,
                activity.getName(),
                activity.getCost()
        );

        if (activity instanceof ActivityGroup group) {
            for (Activity child : group.getActivities()) {
                showActivity(child, indent + "  ");
            }
        }
    }
}
