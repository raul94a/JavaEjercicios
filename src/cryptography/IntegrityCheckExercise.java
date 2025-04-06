package cryptography;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HexFormat;

/**
 * The exercise tries to solve the following problem:
 * <img src="../../images/hash256_exercise.png" width="2100" height="1000"/>
 *
 * A video must be divided into 1024 bytes blocks. Starting from the last block,
 * the sha-256 hash (hn) must be computed for it . Then, the hn hash is added to the second last block (1024 bytes)
 * and then its sha-256 hash is computed. We repeat these steps until we reach the very first block.
 * The target is to get the h0 hash. In order words, the hash of the first block.
 *
 * <h2>How to check this works?</h2>
 *
 * compute the hash for <i>testvideo.mp4</i> by changing the <strong>VIDEO_PATH</strong>
 * variable. The correct value for h0 is: <strong>03c08f4ee0b576fe319338139c045c89c3e8e9409633bea29442e21425006ea8</strong>
 */
public class IntegrityCheckExercise {

    static final String VIDEO_PATH = "video.mp4";
    static final String TEST_VIDEO_PATH = "testvideo.mp4";
    static final int BLOCK_SIZE = 1024;
    static final int HASH_BYTES = 32;
    public static void main(String[] args) {

        computeHashForFile(VIDEO_PATH);
        computeHashForFile(TEST_VIDEO_PATH);



    }

    private static void computeHashForFile(String path){
        var file = new File(path);
        String currentHash = "";
        HexFormat hf = HexFormat.of();
        try{
            var reader = new FileInputStream(file);
            byte[] fileBytes = reader.readAllBytes();
            int length = fileBytes.length;
            int lastBlockSize = (length % BLOCK_SIZE);

            // leer el último bloque
            byte[] bytes = Arrays.copyOfRange(fileBytes, length - lastBlockSize,length);
            // obtener su hash sha 256 en hex
            var digest = MessageDigest.getInstance("SHA-256");
            digest.update(bytes);
            var hash = digest.digest();
            currentHash = hf.formatHex(hash);

            for (int i = length - lastBlockSize; i > 0; i-= BLOCK_SIZE){
                // We need this array to copy the current read block along with hash bytes.
                byte[] block = new byte[BLOCK_SIZE + HASH_BYTES];
                byte[] blockRead = Arrays.copyOfRange(fileBytes, i - BLOCK_SIZE, i);

                for(int k = 0; k < blockRead.length; k++){
                    block[k] = blockRead[k];
                }

                byte[] currentHashBytes = hf.parseHex(currentHash);

                for (int j = 0; j < currentHashBytes.length; j++){
                    int position = j + BLOCK_SIZE;
                    block[position] = currentHashBytes[j];
                }

                digest.update(block);
                hash = digest.digest();
                currentHash = hf.formatHex(hash);



            }
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.printf("Hash for file %-15s %s%n", path, currentHash);
    }




}
