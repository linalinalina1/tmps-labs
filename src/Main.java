import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DestinationRepository repository = new InMemoryDestinationRepository();
        DestinationManager manager = new DestinationManager(repository);
        DestinationDisplay display = new DestinationDisplay();
        TravelCostService costService = new TravelCostService();

        int choice = -1;

        do {
            System.out.println("\nTravel Planner");
            System.out.println("1. Add destination");
            System.out.println("2. View destinations");
            System.out.println("3. Calculate travel cost");
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
