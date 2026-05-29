import java.util.Scanner;

public class InputValidator{

    public static Integer getValidIntOrBack(Scanner scanner, int min, int max) {

        while (true) {
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

            } catch (NumberFormatException e) {
            }

            System.out.println("\n  Invalid input.");
            System.out.println("  1) Try again");
            System.out.println("  2) Go back");
            System.out.print("  Enter choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("2")) {
                return null;
            }
        }
    }

    public static String getNonEmptyStringOrBack(
            Scanner scanner, String prompt) {

        while (true) {

            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("\n  This field cannot be blank.");
            System.out.println("  1) Try again");
            System.out.println("  2) Go back");
            System.out.print("  Enter choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("2")) {
                return null;
            }
        }
    }

    public static Boolean getYesNoOrBack(Scanner scanner, String prompt) {

        while (true) {

            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                return true;
            }

            if (input.equals("no") || input.equals("n")) {
                return false;
            }

            System.out.println("\n  Invalid input.");
            System.out.println("  1) Try again");
            System.out.println("  2) Go back");
            System.out.print("  Enter choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("2")) {
                return null;
            }
        }
    }

    public static int getValidInt(
            Scanner scanner, int min, int max) {

        while (true) {

            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            try {

                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println(
                        "  Please enter a number between "
                                + min + " and " + max + ".");

            } catch (NumberFormatException e) {

                System.out.println(
                        "  Invalid input. Please enter a number.");
            }
        }
    }

    public static String getNonEmptyString(Scanner scanner) {

        while (true) {

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field cannot be blank.");
        }
    }

    public static boolean getYesNo(Scanner scanner) {

        while (true) {

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                return true;
            }

            if (input.equals("no") || input.equals("n")) {
                return false;
            }

            System.out.println("  Please enter yes or no.");
        }
    }
}