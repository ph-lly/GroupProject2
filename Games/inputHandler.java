static public int in(String prompt) {
    System.out.print(prompt); // Display the prompt message
    String temp = in(); // Get the input from the user
    boolean cont = true; // Flag to control the loop until valid input is received
    Double d = 0.0; // Temporary variable to store the parsed double value
    while (cont) {
        try {
            d = Double.parseDouble(temp); // Try to parse the input as a double
            cont = false; // Exit loop if parsing is successful
        } catch (NumberFormatException e) {
            // If the input cannot be parsed as a double, display an error message and
            // prompt again
            System.out.println("Invalid Input.");
            System.out.print(prompt); // Prompt the user again
            temp = in(); // Get new input from the user
        }
    }
    return d.intValue(); // Return the valid double value
}

static public final String in() {
    Scanner scan = new Scanner(System.in);
    boolean cont = true; // Flag to control the loop until valid input is received
    String temp = ""; // Temporary variable to hold user input
    while (cont) {
        temp = scan.nextLine(); // Read the line of input from the user
        temp = temp.strip(); // Strip any leading or trailing spaces from the input
        temp = Normalizer.normalize(temp, Form.NFKC); // Normalize input to prevent encoding issues
        if (!Pattern.matches("[A-Za-z0-9_\\.\\-]+", temp))
            System.out.print("Invalid, try again: ");
        else
            cont = false;

    }
    return temp;
}