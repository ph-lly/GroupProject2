import Games.mathGame;
import Games.inputHandler;

public class main {
    public static void main(String[] args) {
        inputHandler i = new inputHandler();
        System.out.println("Welcome to a Game Collection!");
        int choice = i.in(
                "\nWhich game would you like to play:\n1. Math Game\n2. Choice 2\n3. Choice 3\n4. Choice 4\n5. Choice 5\n 6. Exit\nChoice:");
        while (choice != 6) {
            switch (choice) {
                case 1:
                    mathGame math = new mathGame();
                    math.play();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                default:
                    System.out.println("Out of bounds. Choose again.\n");
                    break;
            }
            choice = i.in(
                    "Which game would you like to play next:\n1. Math Game\n2. Choice 2\n3. Choice 3\n4. Choice 4\n5. Choice 5\n 6. Exit\nChoice:");
        }
    }
}
