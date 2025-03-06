package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;

public class MessageDigestAlgorithm {

    protected String getHash(String filepath) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        File file = new File(filepath);

        if (!file.exists()) {
            System.out.println("Specified file or directory does not exist: " + filepath);
            return null;
        }

        if (file.isFile()) {

            try (FileInputStream fis = new FileInputStream(file);
                 DigestInputStream dis = new DigestInputStream(fis, md)) {
                byte[] buffer = new byte[1024];
                while (dis.read(buffer) != -1) {}
            } catch ( IOException exception) {
                System.out.println("An issue occurred: ");
                exception.printStackTrace();
            }

        } else if (file.isDirectory()) {

            File[] files = file.listFiles();
            if (files != null) {
                Arrays.sort(files, Comparator.comparing(File::getName));
                for (File f : files) {
                    String fileHash = getHash(f.getAbsolutePath());
                    if (fileHash != null) {
                        md.update(fileHash.getBytes());
                    }
                }
            }
        }

        byte[] hash = md.digest();
        return hex(hash);
    }

    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }

    public boolean isEqual(String filepath1, String filepath2) throws NoSuchAlgorithmException {
        String hash2 = getHash(filepath2);
        String hash1 = getHash(filepath1);

        System.out.println(hash2);
        System.out.println(hash1);

        return hash1.equals(hash2);
    }

}
