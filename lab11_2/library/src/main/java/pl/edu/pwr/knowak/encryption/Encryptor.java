package pl.edu.pwr.knowak.encryption;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;

public class Encryptor {
    public Encryptor() {
    }

    public static void encryptFile(PublicKey publicKey, Path filePath, Path newFilePath) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] fileBytes = Files.readAllBytes(filePath);
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedFileBytes = encryptCipher.doFinal(fileBytes);

        try (FileOutputStream stream = new FileOutputStream(newFilePath.toFile())) {
            stream.write(encryptedFileBytes);
        }
    }

    public static void encryptSHAFile(SecretKey key, Path filePath, Path newFilePath)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {

        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

        byte[] plaintext = Files.readAllBytes(filePath);
        byte[] ciphertext = cipher.doFinal(plaintext);

        byte[] combined = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);

        Files.write(newFilePath, combined);
    }
}
