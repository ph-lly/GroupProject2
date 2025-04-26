//Game for group project 2
//Author Delaney Vandegraft

import java.security.SecureRandom;
import java.util.*;

public class WordGuessingGame{
     //CWE-331, fixed entopy issues
    private static final SecureRandom rng = new SecureRandom();

    // CWE-766: Critical Data Element Declared Public, everything is private
    private final List<WordEntry> wordList = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    private int totalScore = 0; //Score tracker
    private boolean gameOver = false; //Game status tracker

    public void start() { //game runner
        System.out.println("Welcome to the word guessing game!");
        addCustomWords(); 
        loadDefualtWord();

        System.out.println("\nStarting Word Guessing Game...");
        System.out.println("Type 'Q' anytime to quit.");

        while(!gameOver && !wordList.isEmpty()) {
            WordEntry wordEntry = getRandomWord();
            playRound(wordEntry);
        }

        if(wordList.isEmpty() && !gameOver){
            System.out.println("No more words left!  You completed the game!");
        }

        System.out.println("Game Over! Final Score: " + totalScore);
    }
    
    private void addCustomWords(){
        System.out.println("Do you want to add custom words to the game? (Y or N) ");
        String response = scanner.nextLine().trim();
        while (response.equalsIgnoreCase("Y")) { 
            //CWE-579, correct equals when comparing strings
            System.out.print("Enter a word: ");
            String word = scanner.nextLine().trim();

            String[] hints = new String[3];
            for(int i = 1; i<4; i++){
                System.out.print("Enter hint " + (i) + " : ");
                hints[i-1] = scanner.nextLine().trim();
            }

            wordList.add(new WordEntry(word, hints));
            System.out.print("Add another word? (Y or N) ");
            response = scanner.nextLine().trim();
        }

    }

    private void loadDefualtWord() { //defualt words
        wordList.add(new WordEntry("wizard", new String[]{"magic user", "Has a beard", "Wears a robe"}))
        wordList.add(new WordEntry("dragon", new String[]{"Breathes fire", "Has wings", "Hoards gold"}));
        wordList.add(new WordEntry("castle", new String[]{"Medieval building", "Has towers", "Protected by walls"}));
        wordList.add(new WordEntry("potion", new String[]{"Comes in bottles", "Used in magic", "Can heal or harm"}));
        wordList.add(new WordEntry("scroll", new String[]{"Ancient writing", "Used in spells", "Made of parchment"}));
        wordList.add(new WordEntry("crown", new String[]{"Fancy", "Headwear", "Worn by kings and queens"}));
        wordList.add(new WordEntry("flower", new String[]{"plant", "colorful", "Can make a crown out of them"}));
        wordList.add(new WordEntry("tree", new String[]{"plant", "tall", "Many make a forest"}));
        wordList.add(new WordEntry("riddle", new String[]{"fun", "tricky", "question"}));
        wordList.add(new WordEntry("farm", new String[]{"Useful", "plants", "Grows food"}));
        wordList.add(new WordEntry("pillow", new String[]{"comfort", "sleep", "Lay your head on it"}));
        wordList.add(new WordEntry("vampire", new String[]{"Noctornal", "immortal", "blood sucker"}));
    }

    private WordEntry getRandomWord(){//picks random words, with no repeats
        int index = rng.nextInt(wordList.size()); //CWE-192 used nextInt, so not interger conversion needed
        return wordList.remove(index);
    }

    private void playRound(WordEntry wordEntry) {//Plays a round
        char[] revealed = new char[wordEntry.word.length()];
        Arrays.fill(revealed, '_');
        Set<Integer> revealedIndices = new HashSet<>();

        boolean guessedCorrectly = false;

        for(int i=0; i<3; i++){
            System.out.println("\nHint " + (1+i) + " : " + hints[i]);
            System.out.println("Word so far: " + String.valueOf(revealed));
            System.out.print("Your guess: ");
            String guess = scanner.nextLine().trim();

            if(guess.equalsIgnoreCase("Q")) { 
                //CWE-579, correct use of equals when comparing strings
                gameOver = true;
                return;
            }

            if (word.equalsIgnoreCase(guess)) {
                guessedCorrectly = true;

                int remainingLetters = wordEntry.word.length() - revealedIndices.size();
                int remainingHints = 3 - (i+1);
                totalScore += remainingLetters * 5 + remainingHints * 10;
                System.out.println("Correct!  Total Points: " + totalScore);
            }else{
                revealRandomLetter(wordEntry.word, revealed, revealedIndices);
            }

            if(!guessedCorrectly){
                System.out.println("Out of hints! The word was: " + wordEntry.word);
                gameOver = true;
            }
        }
    }
    //reveals a random letter of the word
    private void revealRandomLetter(String word, char[] revealed, Set<Integer> revealedIndices) {
        List<Integer> unrevealed = new ArrayList<>();
        for(int i=0; i<word.length(); i++) 
            {if(!revealedIndices.contains(i)){
                unrevealed.add(i);
            }
        }

        if (!unrevealed.isEmpty()){
            int indexToReveal = unrevealed.get(rng.nextInt(unrevealed.size()));
            revealed[indexToReveal] = word.charAt(indexToReveal);
            revealedIndices.add(indexToReveal);
        }
    }
    
}

//Class for the words and the hints
//CWE-1062: Parent Class with References to Child Class, no child, no problem
class WordEntry{
    public String word;
    public final String[] hints;

    public WordEntry(string word, String[] hints){
        this.word = word;
        this.hints = hints;
    }
}
