package org.example;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

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
        System.out.println(filepath + " hash is " + hex(hash));
        return hex(hash);
    }

    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }

    public boolean isEqual(String filepath1, String filepath2) throws NoSuchAlgorithmException, IOException {
        String hash2;
        String hash1;

        String extension1 = null;
        String extension2 = null;

        int slashIndex1 = filepath1.lastIndexOf('.');
        int slashIndex2 = filepath2.lastIndexOf('.');

        if (slashIndex1 > 0)
            extension1 = filepath1.substring(slashIndex1);

        if (slashIndex2 > 0)
            extension2 = filepath2.substring(slashIndex2);

        if(Objects.equals(extension1, ".md5"))hash1 = getMD5FromFile(filepath1);
        else hash1 = getHash(filepath1);
        if(Objects.equals(extension2, ".md5"))hash2 = getMD5FromFile(filepath2);
        else hash2 = getHash(filepath2);

        return hash1.equals(hash2);
    }


    private String getMD5FromFile(String filepath) throws IOException {
        File file = new File(filepath);
        String data = null;
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();
        return data;
    }
}
