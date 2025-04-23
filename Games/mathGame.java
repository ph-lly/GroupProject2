package Games;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import Games.inputHandler;

public class mathGame {
    private int size;
    private ArrayList<Integer> arr;
    private int randomNumStart;
    private int randomNumEnd;
    private int moves;
    private inputHandler i = new inputHandler();

    public mathGame() {
        arr = null;
        randomNumStart = 0;
        randomNumEnd = 0;
        size = 0;
        moves = 0;
    }

    public mathGame(int size) {
        this.size = size;
        arr = new ArrayList<>();
        Random rand = new Random();
        randomNumStart = rand.nextInt(size);
        randomNumEnd = rand.nextInt(size);
        moves = 0;
    }

    public String getSize() {
        return String.valueOf(size);
    }

    public String getCur() {
        return String.valueOf(randomNumStart);
    }

    public String getEnd() {
        return String.valueOf(randomNumEnd);
    }

    public void split() {
        String temp = String.valueOf(randomNumStart);
        arr = new ArrayList<>();
        // Splits
        for (int i = 0; i < temp.length(); i++) {
            arr.add(Integer.parseInt(temp.substring(i, i + 1)));
        }
        int keepGoing = 1;
        while (keepGoing == 1) {
            this.moves++;
            System.out.println("\n" + arr.toString());
            int index = i.in("Which place would you like to modify:");

            while (index <= 0 || index > arr.size()) {
                System.out.println("Out of Bounds.");
                index = i.in("Which place would you like to modify:");
            }

            // CWE-193 Off-by-one Error
            index--; // Changes index to be in program's index form.

            int mod = i.in("1. Add 1\n2. Subtract 1\nChoice: ");
            switch (mod) {
                case 1:
                    if (arr.get(index) == 9)
                        arr.set(index, 0);
                    else
                        arr.set(index, arr.get(index) + 1);
                    break;
                case 2:
                    if (arr.get(index) == 0)
                        arr.set(index, 9);
                    else
                        arr.set(index, arr.get(index) - 1);
                    break;
                default:
                    break;
            }
            keepGoing = i.in("\n1. Contuine \n2. Quit\nChoice:");
        }
        // Reassembles
        int tem = 0;
        for (Integer in : arr)
            tem = tem * 10 + in;
        this.randomNumStart = tem;
    }

    public boolean equal() {
        return randomNumEnd == randomNumStart;
    }

    public void play() {
        int choice = i.in("1. Easy \n2. Medium \n3. Hard\nChoice:");

        /*
         * CWE-839 Numeric Range Comparison without a minimum check
         */

        if (choice <= 0 || choice > 3) {
            System.out.println("Out of Bounds. Try again");
            choice = i.in("1. Easy \n2. Medium \n3. Hard\nChoice:");
        }
        mathGame math = new mathGame((int) Math.pow(10, choice + 1));

        while (choice != 8 && !math.equal()) {
            System.out.println(
                    "\nCurrent Number: " + math.getCur() + " Target Number: " + math.getEnd());
            choice = i.in(
                    "1. Add 1 \n2. Subtract 1  \n3. Double \n4. Halve \n5. Convert to Array of single digits \n" +
                            "6. Integer Bitshift \n7. Change to Hexidecimal \n8. Quit\nChoice:");

            if (choice <= 0 || choice > 8) {
                System.out.println("Out of Bounds. Try again");
            } else
                math.choose(choice);
        }

        if (math.equal()) {
            System.out.println("You win! Moves: " + math.moves);
        }
        System.out.println("\nGoodbye");

    }

    public void choose(int choice) {

        switch (choice) {
            case 1:
                randomNumStart++;
                moves++;
                break;
            case 2:
                this.randomNumStart--;
                moves++;
                break;
            case 3:
                this.randomNumStart *= 2;
                moves++;
                break;
            case 4:
                this.randomNumStart *= 0.5;
                moves++;
                break;
            case 5:
                this.split();
                break;
            case 6:

                break;
            case 7:

                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        mathGame math = new mathGame();
        math.play();
    }
}