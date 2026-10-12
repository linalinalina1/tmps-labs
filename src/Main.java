
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println("\nTRAVEL PLANNER: ");
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
                    System.out.println("Add destination - coming soon!");
                    break;

                case 2:
                    System.out.println("View destinations - coming soon!");
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
