
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DestinationRepository repository = new InMemoryDestinationRepository();
        DestinationManager manager = new DestinationManager(repository);
        DestinationDisplay display = new DestinationDisplay();
        TravelCostService costService = new TravelCostService();
        TripManager tripManager = TripManager.getInstance();

        int choice = -1;

        do {
            System.out.println("\nTravel Planner");
            System.out.println("1. Add destination");
            System.out.println("2. View destinations");
            System.out.println("3. Calculate travel cost");
            System.out.println("4. Create trip");
            System.out.println("5. View trips");
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

                    manager.addDestination(
                            new Destination(city, country, cost)
                    );

                    System.out.println("Destination added successfully!");
                    break;

                case 2:
                    display.showDestinations(manager.getDestinations());
                    break;

                case 3:
                    if (manager.getDestinations().isEmpty()) {
                        System.out.println("No destinations available.");
                        break;
                    }

                    display.showDestinations(manager.getDestinations());
                    System.out.print("Choose destination number: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid destination number.");
                        scanner.nextLine();
                        break;
                    }

                    int destinationNumber = scanner.nextInt();
                    scanner.nextLine();

                    if (destinationNumber < 1 ||
                            destinationNumber > manager.getDestinations().size()) {
                        System.out.println("Invalid destination number.");
                        break;
                    }

                    Destination selectedDestination =
                            manager.getDestinations().get(destinationNumber - 1);

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

                    CostCalculator calculator;

                    if (calculationMethod == 1) {
                        calculator = new StandardCostCalculator();
                    } else if (calculationMethod == 2) {
                        calculator = new StudentCostCalculator();
                    } else {
                        System.out.println("Invalid calculation method.");
                        break;
                    }

                    double finalCost = costService.calculateCost(
                            selectedDestination.getEstimatedCost(),
                            calculator
                    );

                    System.out.printf("Final travel cost: %.2f%n", finalCost);
                    break;

                case 4:
                    if (manager.getDestinations().isEmpty()) {
                        System.out.println("Add a destination before creating a trip.");
                        break;
                    }

                    display.showDestinations(manager.getDestinations());
                    System.out.print("Choose destination number: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid destination number.");
                        scanner.nextLine();
                        break;
                    }

                    int tripDestinationNumber = scanner.nextInt();
                    scanner.nextLine();

                    if (tripDestinationNumber < 1 ||
                            tripDestinationNumber > manager.getDestinations().size()) {
                        System.out.println("Invalid destination number.");
                        break;
                    }

                    Destination tripDestination =
                            manager.getDestinations().get(tripDestinationNumber - 1);

                    Trip.Builder builder = new Trip.Builder(tripDestination);
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

                        ActivityCreator creator;

                        if (activityType == 1) {
                            creator = new SightseeingCreator();
                        } else if (activityType == 2) {
                            creator = new DiningCreator();
                        } else {
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

                        Activity activity =
                                creator.createActivity(activityName, activityCost);

                        builder.addActivity(activity);
                        activityCount++;

                        System.out.println("Activity added successfully!");
                    }

                    Trip trip = builder.build();
                    tripManager.addTrip(trip);

                    System.out.println("Trip created successfully!");
                    break;

                case 5:
                    if (tripManager.getTrips().isEmpty()) {
                        System.out.println("No trips created yet.");
                        break;
                    }

                    System.out.println("\nYour trips:");
                    int tripNumber = 1;

                    for (Trip savedTrip : tripManager.getTrips()) {
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

                        for (Activity savedActivity : savedTrip.getActivities()) {
                            System.out.printf(
                                    "   %s: %s - %.2f%n",
                                    savedActivity.getType(),
                                    savedActivity.getName(),
                                    savedActivity.getCost()
                            );
                        }

                        System.out.printf(
                                "   Total trip cost: %.2f%n",
                                savedTrip.getTotalCost()
                        );
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
}
