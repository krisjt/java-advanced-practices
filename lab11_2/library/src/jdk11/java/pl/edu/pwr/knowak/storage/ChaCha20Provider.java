package pl.edu.pwr.knowak.storage;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class ChaCha20Provider {

    KeyStoreProvider keyStoreProvider;

    public ChaCha20Provider(KeyStoreProvider keyStoreProvider) {
        this.keyStoreProvider = keyStoreProvider;
    }

    public void generateKey(char[] keyPassword, String alias) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        KeyGenerator keyGen = KeyGenerator.getInstance("ChaCha20");
        SecretKey key = keyGen.generateKey();

        keyStoreProvider.saveKey(key, keyPassword, alias);
    }

    public SecretKey loadKey(char[] keyPassword, String alias) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
        try (FileInputStream fis = new FileInputStream(keyStoreProvider.getFilename())) {
            keyStoreProvider.loadKeyStore().load(fis, keyPassword);
        } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return (SecretKey) keyStoreProvider.loadKeyStore().getKey(alias, keyPassword);
    }

}
