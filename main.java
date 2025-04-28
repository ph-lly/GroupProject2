import Games.mathGame;
import Games.GuessNumber;
import Games.inputHandler;
import Games.Hangman;
import Games.Cube;
import Games.WordGuesser;

public class main {
    public static void main(String[] args) {
        inputHandler i = new inputHandler();
        System.out.println("Welcome to a Game Collection!");
        int choice = i.in(
                "\nWhich game would you like to play:\n1. Math Game\n2. Hangman\n3. Number Guess\n4. Cube Animation4\n5. Word Guesser \n 6. Exit\nChoice:");
        while (choice != 6) {

            // switch case avoids CWE-484: Omitted Break Statement in Switch by including a break in every case
            switch (choice) {
                case 1:
                    mathGame math = new mathGame();
                    math.play();
                    break;
                case 2:
                    Hangman hangman = new Hangman();
                    hangman.play();
                    break;
                case 3:
                    GuessNumber guessNumber = new GuessNumber();
                    break;
                case 4:
                    Cube cube = new Cube();
                    cube.run();
                    break;
                case 5:
                    WordGuesser WGG = new WordGuesser();
                    WGG.run();
                    break;
                default:
                    System.out.println("Out of bounds. Choose again.\n");
                    break;
            }
            choice = i.in(
                    "Which game would you like to play next:\n1. Math Game\n2. Hangman\n3. Number Guess\n4. Cube Animation\n5. Word Guesser 5\n 6. Exit\nChoice:");
        }
    }
}
