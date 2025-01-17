package cryptography;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class DesAlgorithm {

    private static Cipher encoder;
    private static Cipher decoder;

    private static final String OUTPUT_DIR = "output/";

    private static byte[] iv = {25, 38, 15, 43, 59, 92, 66, 73};



    public static void main(String[] args)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidAlgorithmParameterException,
            InvalidKeyException
    {
        // 1. Create Keys
        SecretKey secretKey  = KeyGenerator.getInstance("DES").generateKey();
        // 2. Vector de inicialización
        AlgorithmParameterSpec parameterSpec = new IvParameterSpec(iv);
        // 3. Enc/Desc algo
        final String algo = "DES/CBC/PKCS5Padding";
        encoder = Cipher.getInstance(algo);
        encoder.init(Cipher.ENCRYPT_MODE,secretKey,parameterSpec);
        decoder = Cipher.getInstance(algo);
        decoder.init(Cipher.DECRYPT_MODE,secretKey,parameterSpec);

        // 4. Encriptación del archivo
        var filePath  = "poem.txt";
        var dir = new File(OUTPUT_DIR);
        if(dir.mkdirs()){
            System.out.println("Directory " + dir.getPath() + " has been created." );
        }
        var file = new File(filePath);
        var name = file.getName();

        var outputFile = new File(dir.getPath() + "/encripted-" + name);
        System.out.println("Output file: " + outputFile);

        processFile(encoder,file,outputFile);
        var copiedEncriptedFilePointer = outputFile;
        outputFile  = new File(dir.getName() + "/decripted-"+name);
        processFile(decoder,copiedEncriptedFilePointer,outputFile);
    }

    static void processFile(Cipher cipher, File inputFile, File outputFile){
        try(InputStream in = new FileInputStream(inputFile);
            OutputStream out = new FileOutputStream(outputFile);
            CipherOutputStream cipherOut = new CipherOutputStream(out,cipher)
        ){
            byte[] buffer = new byte[512];
            int numRead;
            while   ((numRead = in.read(buffer)) >= 0){
                cipherOut.write(buffer,0,numRead);
            }
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
