package pl.edu.pwr.knowak.storage;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

public class KeyStoreProvider {

    private final KeyStore ks;
    private String filename;
    private char[] pwd;

    public KeyStoreProvider(String keyStoreType) throws KeyStoreException {
        ks = KeyStore.getInstance(keyStoreType);
    }

    public KeyStoreProvider(String keyStoreType, String filename, char[] pwd) throws KeyStoreException {
        ks = KeyStore.getInstance(keyStoreType);
        this.filename = filename;
        this.pwd = pwd;
    }

    public void createKeyStore() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        ks.load(null, pwd);
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            ks.store(fos, pwd);
        }
    }

    public KeyStore loadKeyStore() throws IOException, CertificateException, NoSuchAlgorithmException {
//        char[] pwdArray = "password".toCharArray();
        ks.load(new FileInputStream(filename), pwd);
        return ks;
    }

    public void saveSymmetricKey(char[] keyPassword, SecretKey secretKey, String alias) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore.SecretKeyEntry secret
                = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter password
                = new KeyStore.PasswordProtection(keyPassword);
        ks.setEntry(alias, secret, password);
        saveKeyStore(filename,pwd);
    }

    public void savePrivateKey(char[] keyPassword, PrivateKey privateKey, String alias, X509Certificate clientCert) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        X509Certificate[] chain = new X509Certificate[] { clientCert };
        ks.setKeyEntry(alias, privateKey, keyPassword, chain);
        saveKeyStore(filename,pwd);
    }

    public void saveCertificate(String alias, Certificate certificate) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        ks.setCertificateEntry(alias, certificate);
        saveKeyStore(filename,pwd);
    }

    public void saveKey(SecretKey secretKey, char[] keyPassword, String alias) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(keyPassword);
        ks.setEntry(alias, entry, protParam);
        saveKeyStore(filename,pwd);
    }
    public Key loadKey(char[] keyPassword, String alias) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        return ks.getKey(alias, keyPassword);
    }

    public Certificate loadCertificate(String alias) throws KeyStoreException {
        return ks.getCertificate(alias);
    }

    public void saveKeyStore(String filename, char[] pwdArray) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            ks.store(fos, pwdArray);
        }
    }

    private static final String SEPARATOR = "========================================\n";
    public String listAllItems() throws IOException {
        try (InputStream is = new FileInputStream(filename)) {
            ks.load(is, pwd);
            StringBuilder sb = new StringBuilder();
            sb.append(SEPARATOR);
            sb.append("=================JDK17===================\n");
            sb.append("=================KEYS===================\n");
            for (String alias : Collections.list(ks.aliases())) {
                if (ks.isKeyEntry(alias)) {
                    KeyStore.Entry entry = ks.getEntry(alias, new KeyStore.PasswordProtection(pwd));
                    sb.append(SEPARATOR);
                    sb.append("Alias: ").append(alias).append("\n");
                    if (entry instanceof KeyStore.PrivateKeyEntry privateKeyEntry) {
                        sb.append("Type: PrivateKey\n");
                        sb.append(privateKeyEntry.getPrivateKey().getAlgorithm());
                        sb.append("\n");
                    } else if (entry instanceof KeyStore.SecretKeyEntry secretKeyEntry) {
                        sb.append("Type: SecretKey\n");
                        sb.append(secretKeyEntry.getSecretKey().getAlgorithm());
                        sb.append("\n");
                    }
                }
            }
            sb.append(SEPARATOR);
            return sb.toString();
        } catch (CertificateException | KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String getFilename() {
        return filename;
    }
}
