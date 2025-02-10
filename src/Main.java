import java.util.Scanner;

public class Main {
    /**
     * Looks up the corresponding character in the Vigenère table.
     * This method is used for both encryption and decryption.
     *
     * @param row     The row character (uppercase letter A-Z).
     * @param col     The column character (uppercase letter A-Z).
     * @param encrypt If true, performs encryption; otherwise, performs decryption.
     * @return The resulting character from the Vigenère table.
     * @throws IllegalArgumentException If row or col is not an uppercase letter A-Z.
     */
    private static char vigenereTableLookup(char row, char col, boolean encrypt) {
        // Make sure we are only accepting uppercase letters
        if (!Character.isLetter(row) || !Character.isLetter(col) || !Character.isUpperCase(row) || !Character.isUpperCase(col)) {
            throw new IllegalArgumentException("Both row and col must be letters A-Z.");
        }

        // Compute the corresponding character in the Vigenère table
        int rowIndex = row - 'A';
        int colIndex = col - 'A';

        // Encryption: shift forward by colIndex places
        // Decryption: shift backward by colIndex places
        int cipherIndex = (encrypt) ? (rowIndex + colIndex) % 26 : (rowIndex - colIndex + 26) % 26;

        // Return as a character rather than an index
        return (char) ('A' + cipherIndex);
    }

    /**
     * Prepares a string for encryption or decryption by converting it to uppercase
     * and removing any non-alphabetic characters.
     *
     * @param input The input string.
     * @return A processed string containing only uppercase letters A-Z.
     */
    private static String vigenerePrepareString(String input) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = Character.toUpperCase(input.charAt(i));

            if (!Character.isLetter(c)) {
                continue;
            }

            output.append(c);
        }
        return output.toString();
    }

    /**
     * Encrypts a message using the Vigenère cipher.
     *
     * @param inputMessage The plaintext message to encrypt.
     * @param key          The encryption key.
     * @return The encrypted ciphertext.
     */
    private static String vigenereEncrypt(String inputMessage, String key) {
        String preparedMessage = vigenerePrepareString(inputMessage);
        String preparedKey = vigenerePrepareString(key);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < preparedMessage.length(); i++) {
            out.append(vigenereTableLookup(preparedKey.charAt(i % preparedKey.length()), preparedMessage.charAt(i), true));
        }
        return out.toString();
    }

    /**
     * Decrypts a message using the Vigenère cipher.
     *
     * @param inputMessage The ciphertext message to decrypt.
     * @param key          The decryption key.
     * @return The decrypted plaintext.
     */
    private static String vigenereDecrypt(String inputMessage, String key) {
        String preparedMessage = vigenerePrepareString(inputMessage);
        String preparedKey = vigenerePrepareString(key);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < preparedMessage.length(); i++) {
            out.append(vigenereTableLookup(preparedMessage.charAt(i), preparedKey.charAt(i % preparedKey.length()), false));
        }
        return out.toString();
    }

    /**
     * Main method that provides a command-line interface for encrypting and decrypting messages
     * using the Vigenère cipher.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("0: Encrypt\n1: Decrypt");
        int choice = scanner.nextInt();

        if (choice == 0) {
            // Accept decrypted input
            scanner.nextLine();
            System.out.println("Enter the message to encrypt.");
            String message = scanner.nextLine();
            System.out.println("Enter the plaintext to encrypt.");
            String key = scanner.nextLine();

            // Print encrypted output
            System.out.println("The ciphertext is:");
            System.out.println(vigenereEncrypt(message, key));
        } else if (choice == 1) {
            // Accept encrypted input
            scanner.nextLine();
            System.out.println("Enter the ciphertext to decrypt.");
            String message = scanner.nextLine();
            System.out.println("Enter the key to decrypt.");
            String key = scanner.nextLine();

            // Print decrypted output
            System.out.println("The plaintext is:");
            System.out.println(vigenereDecrypt(message, key));
        } else {
            System.out.println("Invalid option!!!");
        }
    }
}
