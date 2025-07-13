package pl.edu.pwr.knowak.encryption;

import javax.crypto.*;
import javax.crypto.spec.ChaCha20ParameterSpec;
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

    public static void encryptSHAFile(SecretKey key, Path filePath, Path newFilePath) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        int count = 1;
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);
        ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 1);
        Cipher cipher = Cipher.getInstance("ChaCha20");
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

        byte[] counter = {(byte) count};
        byte[] plaintext = Files.readAllBytes(filePath);
        byte[] ciphertext = cipher.doFinal(plaintext);
        byte[] combined = new byte[counter.length + nonce.length + ciphertext.length];


        System.arraycopy(counter, 0, combined, 0, counter.length);
        System.arraycopy(nonce, 0, combined, counter.length, nonce.length);
        System.arraycopy(ciphertext, 0, combined, counter.length+nonce.length, ciphertext.length);

        Files.write(newFilePath, combined);
    }
}
