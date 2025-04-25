import java.util.*;
/*
 * Hangman Program for Group Project 2
 * Author: Phillip Nguyen
 */

public class Hangman {
    public static final int MAX_WRONG = 6;
    public static final int EXPECTED_WORD_COUNT = 3;
    public static String[] words = {"closed", "water", "bottle"};
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[]args){
        // CWE-494** (see verifyWordListIntegrity function desc)
        if(!verifyWordListIntegrity(words)){
            System.out.println("Word list integrity check failed!");
            return;
        }

        String secret = words[new Random().nextInt(words.length)];
        char[] display = new char[secret.length()];
        Arrays.fill(display, '_');
        Set<Character> guessed = new HashSet<>();
        Set<Character> correct = new HashSet<>();
        int wrong = 0;

        System.out.println("Welcome to Hangman!");

        //CWE-835 Loop with unreachable exit condition (this one has an exit condition)
        while (wrong < MAX_WRONG && new String(display).contains("_")){
        	boolean flag = false;
            print(display, guessed, wrong);
            char guess = getValidGuess();

            //CWE-484 Do not omit break in switch statement
            switch(Character.toLowerCase(guess)){
                default:
                    if(guessed.contains(guess)){
                        System.out.println("Already guessed " + guess);
                        flag = true;
                        break; // <----------
                    }
            guessed.add(guess);
            if(secret.contains(String.valueOf(guess))){
                for(int i = 0; i < secret.length(); i++){
                    if(secret.charAt(i) == guess){
                        display[i] = guess;
                        correct.add(guess);
                    }
                }
                if(!flag) {
                	System.out.println("Correct!");
                	
                }
                
            }
            else{
            	if(!(correct.contains(guess) && (guessed.contains(guess)))) {
            		wrong++;
            	}
                System.out.println("Wrong! Wrong guesses: " + wrong);
            }
            break;
            }
        }

        if(new String(display).equals(secret)){
            System.out.println("You win! The word was: " + secret);
        }
        else{
        	printHangman(MAX_WRONG);
            System.out.println("You lose! The word was: " + secret);
        }

        scan.close();
    }


// CWE-606 input validation for loop
public static char getValidGuess(){
    while(true){
        System.out.print("Guess a letter: ");
        String input = scan.nextLine().toLowerCase();
        if(input.length() == 1 && input.matches("[a-z]")){ //CWE-184 - incomplete list of disallowed inputs (avoiding blacklists and using whitelists instead) 
            return input.charAt(0);
        }
        System.out.println("Please enter a single letter from a-z.");
    }
}
// CWE-494 sort of not really.. assume that some sort of function is downloaded 
// from a library that generated a word from a server that isn't directly
// verified, or that function was bundled with other untrusted code that
// wasn't verified
public static boolean verifyWordListIntegrity(String[] words){
    return words.length == EXPECTED_WORD_COUNT && 
            Arrays.asList(words).contains("closed") &&
            Arrays.asList(words).contains("water") &&
            Arrays.asList(words).contains("bottle");
}

public static void print(char[] display, Set<Character> guessed, int wrong){
    System.out.println("\nWord: " + String.valueOf(display));
    System.out.println("Guessed letters: " + guessed);
    System.out.println("Guesses left: " + (MAX_WRONG - wrong));
    printHangman(wrong);
}

public static void printHangman(int wrong) {
    String[] hangman = new String[] {
    		
            // 0
            "  +---+\n" +
            "  |   |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "=========",

            // 1 
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "=========",

            // 2
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            "  |   |\n" +
            "      |\n" +
            "      |\n" +
            "=========",

            // 3
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|   |\n" +
            "      |\n" +
            "      |\n" +
            "=========",

            // 4 
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|\\  |\n" +
            "      |\n" +
            "      |\n" +
            "=========",

            // 5
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|\\  |\n" +
            " /    |\n" +
            "      |\n" +
            "=========",

            // 6 
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|\\  |\n" +
            " / \\  |\n" +
            "~~ ~ ~ |\n" +
            "========="
        };

        System.out.println(hangman[Math.min(wrong, MAX_WRONG)]);
    }
}
