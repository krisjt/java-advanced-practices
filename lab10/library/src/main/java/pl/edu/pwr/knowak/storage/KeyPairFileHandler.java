package pl.edu.pwr.knowak.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.*;

public class KeyPairFileHandler {

    public void generateKey(Path publicKeyPath, Path privateKeyPath) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        saveKey(privateKey,privateKeyPath);
        saveKey(publicKey,publicKeyPath);
    }

    private void saveKey(Key key, Path path){
        try (FileOutputStream fos = new FileOutputStream(path.toString())) {
            fos.write(key.getEncoded());
        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
        }
    }

    public PublicKey readPublicKey(Path path) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        File publicKeyFile = new File(path.toString());
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public PrivateKey readPrivateKey(Path path) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        File privateKeyFile = new File(path.toString());
        byte[] publicKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
