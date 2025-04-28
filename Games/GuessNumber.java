package Games;

import java.util.Random;
import java.util.Scanner;

public class GuessNumber {

    public GuessNumber() {
        inputHandler i = new inputHandler(); // Create an instance of inputHandler
        Random random = new Random();
        int score = 0;
        int totalRounds = 5;

        System.out.println(" Welcome to Guess the Number!");
        System.out.println("Guess a number between 1 and 10\n");

        boolean playing = true;
        while (playing) {
            playing = play(totalRounds, score, i, random);
        }

        System.out.printf("Game over! Final score: %d/%d\n", score, totalRounds);

        // CWE-356: Product UI warning User of Unsafe Actions
        System.out.println(
                "WARNING: You are about to exit the game. All progress will be lost. Are you sure you want to quit? (y/n)");
        String confirmation = i.in().trim();

        if (confirmation.equalsIgnoreCase("y")) {
            System.out.println("Thanks for playing!");
        } else if (confirmation.equalsIgnoreCase("n")) {
            playing = true;
            while (playing) {
                playing = play(totalRounds, score, i, random);
            }
        }
    }

    public static boolean play(int totalRounds, int score, inputHandler i, Random random) {
        for (int round = 1; round <= totalRounds; round++) {
            int number = random.nextInt(10) + 1;

            // Input loop to ensure valid number input
            int guess = -1;
            boolean validInput = false;
            while (!validInput) {
                System.out.printf("Round %d: Enter your guess: ", round);
                String temp = i.in(); // Use inputHandler for input
                try {
                    guess = Integer.parseInt(temp);
                    if (guess >= 1 && guess <= 10) {
                        validInput = true; // valid guess
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 10.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
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
            System.out.printf("Do you want to continue playing? (y/n): ");
            String response = i.in().trim();

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
