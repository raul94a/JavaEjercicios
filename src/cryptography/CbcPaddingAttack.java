package cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;

public class CbcPaddingAttack {

    final String URL = "https://crypto-class.appspot.com/po?er=";
    final String TEST_PARAM = "f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4";
    private final byte[] KEY = "1234567890123456".getBytes(StandardCharsets.UTF_8); // Clave de 16 bytes

    final int MAX_WORD_LENGTH  = 256;
    final HexFormat hf = HexFormat.of();
    final int BLOCK_SIZE = 16;



    private java.net.http.HttpResponse request(String word){
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + word))
                .GET()
                .build();
        try{
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            System.out.println(e);

        }
        return null;
    }






    public void paddingAttack(){
        byte[] testParamBytes = hexStringToBytes(TEST_PARAM);
        int testParamLength = testParamBytes.length;
        byte lastBlockByte = testParamBytes[testParamLength - 1];

        System.out.println("TAMAÑO DE CIFRADO: " + testParamBytes.length);
        System.out.println("Número de bloques: " + testParamLength / 16);
        var lastBlockBytes = Arrays.copyOfRange(testParamBytes,48, testParamLength);

        System.out.println(lastBlockBytes.length);
        System.out.println(hf.formatHex(lastBlockBytes));

        for (Byte b: lastBlockBytes){
            System.out.println(b);
        }


        List<String> permutations = getPaddingAttackVariants(lastBlockByte);
        File register = new File("request_register.txt");

        try(FileWriter writer = new FileWriter(register)) {
            register.createNewFile();
            for(String str: permutations){
                byte[] bytes = hf.parseHex(str);
                // substitution of the last byte for the current permutation
                testParamBytes[testParamLength - 1] = bytes[0];
                var hex = hf.formatHex(testParamBytes);
                System.out.println("Calling " + str);
                var response = request(hex);
                if(response != null){
                    StringBuilder builder = new StringBuilder();
                    builder.append(hex);
                    builder.append("\t").append(response.statusCode()).append("\n");
                    writer.write(builder.toString());
                    writer.flush();

                }
            }
            writer.close();
        }catch (Exception e) {
            System.out.println(e);
        }




    }

    /**
     *
     * @param str hex encoded
     */
    public byte extractLastBlock(String str, boolean analyzeBlocks){
        byte[] bytes = hf.parseHex(str);
        if(analyzeBlocks)
            analyzeByteBlocks(bytes);
        int length = bytes.length;
        var lastBlock = bytes[length - 1];
        analyzeLastBlock(lastBlock);
        return lastBlock;
    }

    public byte[] hexStringToBytes(String str){
       return hf.parseHex(str);
    }

    public List<String> getPaddingAttackVariants(byte lastBlockByte){
        List<String> permutations = new ArrayList<>();
        for (int i = -127 ; i < 128; i++){
            if(i == lastBlockByte)
                continue;
            permutations.add(hf.formatHex(new byte[]{(byte) i}));
        }
        return permutations;
    }
    public void analyzeLastBlock(byte lastBlock){
        String hex = hf.formatHex(new byte[]{lastBlock});
        System.out.printf("Hex value of last block %s\n",hex);
    }

    private void analyzeByteBlocks(byte[] bytes){
        for (int i = 0; i < bytes.length; i++){
            byte block = bytes[i];
            System.out.println("Block " + i  + ": "  + block);
        }
    }

    public int numberOfBlocks(String cipherText) {
        return cipherText.length() / BLOCK_SIZE;
    }
    // Descifra todo el texto cifrado
    public  byte[] decrypt(byte[] iv, byte[] ciphertext) {
        int numBlocks = ciphertext.length / BLOCK_SIZE;
        byte[] plaintext = new byte[ciphertext.length];
        byte[] prevBlock = iv;

        for (int i = 0; i < numBlocks; i++) {
            byte[] cipherBlock = Arrays.copyOfRange(ciphertext, i * BLOCK_SIZE, (i + 1) * BLOCK_SIZE);
            byte[] plainBlock = decryptBlock(prevBlock, cipherBlock);
            System.arraycopy(plainBlock, 0, plaintext, i * BLOCK_SIZE, BLOCK_SIZE);
            prevBlock = cipherBlock; // El bloque cifrado actual será el "previo" para el siguiente
        }
        return plaintext;
    }

    public byte[] decryptBlock(byte[] prevBlock, byte[] cipherBlock) {
        byte[] plaintext = new byte[BLOCK_SIZE];
        byte[] intermediate = new byte[BLOCK_SIZE]; // Estado intermedio D(cipherBlock)
        byte[] manipulatedPrev = prevBlock.clone();

        // Desciframos byte por byte, de derecha a izquierda
        for (int pos = BLOCK_SIZE - 1; pos >= 0; pos--) {
            int paddingValue = BLOCK_SIZE - pos; // Valor de padding esperado (1, 2, 3...)
            // Ajustamos los bytes posteriores para que tengan el padding correcto
            for (int i = BLOCK_SIZE - 1; i > pos; i--) {
                manipulatedPrev[i] = (byte) (intermediate[i] ^ paddingValue);
            }
            // Probamos valores para el byte actual hasta que el padding sea válido
            for (int guess = 0; guess < 256; guess++) {
                manipulatedPrev[pos] = (byte) guess;
                if (isPaddingValid(manipulatedPrev, cipherBlock)) {
                    intermediate[pos] = (byte) (guess ^ paddingValue);
                    plaintext[pos] = (byte) (intermediate[pos] ^ prevBlock[pos]);
                    break;
                }
            }
        }
        return plaintext;
    }

    public  boolean isPaddingValid(byte[] iv, byte[] ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(KEY, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            cipher.doFinal(ciphertext);
            return true; // Padding válido
        } catch (Exception e) {
            return false; // Padding inválido (e.g., BadPaddingException)
        }
    }

    // Método para XOR entre dos arrays de bytes
    public byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }



    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        var attacker = new CbcPaddingAttack();
        attacker.paddingAttack();
    }
}
