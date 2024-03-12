package propensist.salamMitra.service;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

    private static SecureRandom random = new SecureRandom();

    public static String generateRandomPassword(int length) {
        if (length < 4) throw new IllegalArgumentException("Password length must be at least 4 characters.");

        StringBuilder password = new StringBuilder();

        // 2 lowercase characters
        password.append(randomChar(CHAR_LOWER));
        password.append(randomChar(CHAR_LOWER));

        // 2 uppercase characters
        password.append(randomChar(CHAR_UPPER));
        password.append(randomChar(CHAR_UPPER));

        // 2 digits
        password.append(randomChar(NUMBER));
        password.append(randomChar(NUMBER));

        // 2 special characters
        password.append(randomChar(OTHER_CHAR));
        password.append(randomChar(OTHER_CHAR));

        // Fill the rest of the password with random characters
        int remainingLength = length - 8;
        for (int i = 0; i < remainingLength; i++) {
            password.append(randomChar(PASSWORD_ALLOW_BASE));
        }

        // Shuffle the characters
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        return new String(passwordArray);
    }

    private static char randomChar(String characterSet) {
        int randomIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }

    public static void main(String[] args) {
        // Generate a random password with length 12
        String password = generateRandomPassword(12);
        System.out.println("Generated password: " + password);
    }
}
