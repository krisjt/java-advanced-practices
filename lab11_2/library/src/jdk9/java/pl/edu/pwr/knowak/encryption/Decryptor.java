package pl.edu.pwr.knowak.encryption;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class Decryptor {

    public static String decryptionFile(PrivateKey privateKey, Path filePath) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedFileBytes = Files.readAllBytes(filePath);
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes);

        try (FileOutputStream stream = new FileOutputStream(filePath.toFile())) {
            stream.write(decryptedFileBytes);
        }

        byte[] fileBytes = Files.readAllBytes(filePath);
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    public static String decryptChaChaFile(SecretKey secretKey, Path filePath)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        byte[] fileBytes = Files.readAllBytes(filePath);

        byte[] iv = new byte[12];
        byte[] ciphertext = new byte[fileBytes.length - 12];

        System.arraycopy(fileBytes, 0, iv, 0, 12);
        System.arraycopy(fileBytes, 12, ciphertext, 0, ciphertext.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

        byte[] decryptedBytes = cipher.doFinal(ciphertext);

        try (FileOutputStream stream = new FileOutputStream(filePath.toFile())) {
            stream.write(decryptedBytes);
        }

        return new String(decryptedBytes);
    }

}
