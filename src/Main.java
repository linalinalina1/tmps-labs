
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DestinationManager manager = new DestinationManager();
        DestinationDisplay display = new DestinationDisplay();

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
                    System.out.println("Calculate travel cost - coming soon!");
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
