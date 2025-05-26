package pl.edu.pwr.knowak.encryption;

import javax.crypto.*;
import javax.crypto.spec.ChaCha20ParameterSpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Arrays;

public class Decryptor {

    public static String decryptionFile(PrivateKey privateKey, Path filePath) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedFileBytes = Files.readAllBytes(filePath);
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes);

        try (FileOutputStream stream = new FileOutputStream(filePath.toFile())) {
            stream.write(decryptedFileBytes);
        }
        return Files.readString(filePath);
    }

    public static String decryptChaChaFile(SecretKey secretKey, Path filePath) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] fileBytes = Files.readAllBytes(filePath);
        byte[] counter = Arrays.copyOfRange(fileBytes, 0, 1);
        byte[] nonce = Arrays.copyOfRange(fileBytes, 1, 13);
        byte[] ciphertext = Arrays.copyOfRange(fileBytes, 13, fileBytes.length);

        ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, counter[0]);
        Cipher decipher = Cipher.getInstance("ChaCha20");
        decipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);

        byte[] decryptedByte = decipher.doFinal(ciphertext);

        try (FileOutputStream stream = new FileOutputStream(filePath.toFile())) {
            stream.write(decryptedByte);
        }

        return new String(decryptedByte);
    }

}
