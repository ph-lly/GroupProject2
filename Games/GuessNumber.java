package Games;

import java.util.Random;
import java.util.Scanner;

public class GuessNumber {

    public GuessNumber() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int score = 0;
        int totalRounds = 5;

        System.out.println(" Welcome to Guess the Number!");
        System.out.println("Guess a number between 1 and 10\n");

        boolean playing = true;
        while (playing) {
            playing = play(totalRounds, score, scanner, random);
        }

        System.out.printf("Game over! Final score: %d/%d\n", score, totalRounds);

        // CWE-356: Product UI warning User of Unsafe Actions
        System.out.println(
                "WARNING: You are about to exit the game. All progress will be lost. Are you sure you want to quit? (y/n)");
        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("y")) {
            System.out.println("Thanks for playing!");
        } else if (confirmation.equalsIgnoreCase("n")) {
            playing = true;
            while (playing) {
                playing = play(totalRounds, score, scanner, random);
            }
        }

        scanner.close();
    }

    public static boolean play(int totalRounds, int score, Scanner scanner, Random random) {
        for (int round = 1; round <= totalRounds; round++) {
            int number = random.nextInt(10) + 1;

            // Input loop to ensure valid number input
            int guess = -1;
            boolean validInput = false;
            while (!validInput) {
                System.out.printf("Round %d: Enter your guess: ", round);
                if (scanner.hasNextInt()) {
                    guess = scanner.nextInt(); // Read the number
                    validInput = true; // Break the loop if valid input is received
                } else {
                    System.out.println("Invalid input. Please enter a valid number between 1 and 10.");
                    scanner.next(); // Consume the invalid input to avoid an infinite loop
                }
            }

            // CWE-480: Using '==' for comparison (not '=' assignment)
            if (guess == number) {
                System.out.println("Correct! +1 point");
                score++;
            } else {
                // CWE-134: format string is controlled by the developer
                // We're not using any user input directly in the format string
                System.out.printf("Wrong! The number was %d\n", number);
            }

            // CWE-134: Again, we use a fixed format string with placeholder
            System.out.printf("Current score: %d/%d\n\n", score, round);
        }

        while (true) {
            System.out.println("Do you want to continue playing? (y/n)");
            scanner.nextLine();
            String response = scanner.nextLine().trim();

            if (response.equalsIgnoreCase("y")) {
                return true;
            } else if (response.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}
