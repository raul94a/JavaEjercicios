package cryptography;

public class CaesarCipher {

    // ASCII
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String encrypt(String text, int positions){
        StringBuilder stringBuilder = new StringBuilder();
        final long textLength = text.length();
        for (int i = 0; i < textLength; i++){
            char character = text.toLowerCase().charAt(i);
            if (character == ' '){
                continue;
            }
            character += (char) positions;
            if (character > 'z'){
                character -= (char) ALPHABET.length();
            }

            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }

    public static String decrypt(String text, int positions){
        StringBuilder stringBuilder = new StringBuilder();
        final long textLength = text.length();
        for (int i = 0; i < textLength; i++){
            char character = text.charAt(i);
            if (character == ' '){
                continue;
            }
            character -= (char) positions;
            if (character < 'a'){
                character += (char) ALPHABET.length();
            }

            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        int positions = 6;
        String text  = "My name is Raul";
        String cipheredText = encrypt(text,positions);
        String decipheredText = decrypt(cipheredText,positions);
        System.out.printf("%-20s %10s%n", "Texto Cifrado", "Texto Descifrado");
        System.out.println("-----------------------------------------");
        System.out.printf("%-20s %10s%n",  cipheredText, decipheredText);

    }
}
