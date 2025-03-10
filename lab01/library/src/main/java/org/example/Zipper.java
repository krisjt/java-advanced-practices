package org.example;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {
    private final MessageDigestAlgorithm messageDigestAlgorithm = new MessageDigestAlgorithm();

    public void zip(File fileToZip, String fileName) throws IOException, NoSuchAlgorithmException {

        if (fileToZip.isHidden()) {
            return;
        }

        String noExtension = getFilepathNoExtension(fileName);

        FileOutputStream fos = new FileOutputStream(noExtension + "_compressed.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        if (fileToZip.isDirectory()) {
            zipDirectory(fileName,zipOut,fileToZip);

            String hashValue = messageDigestAlgorithm.getHash(fileName);
            File hashFile = File.createTempFile("hash_", ".md5");

            try (FileWriter writer = new FileWriter(hashFile)) {
                writer.write(hashValue);
            }
            zipOneFile(hashFile, noExtension + "_hash.md5", zipOut);

        }else{
            zipOneFile(fileToZip,fileName,zipOut);

            String hashValue = messageDigestAlgorithm.getHash(fileName);
            File hashFile = File.createTempFile("hash_", ".md5");

            try (FileWriter writer = new FileWriter(hashFile)) {
                writer.write(hashValue);
            }
            zipOneFile(hashFile, noExtension + "_hash.md5", zipOut);
        }
        zipOut.closeEntry();
    }

    private void zipDirectory(String fileName, ZipOutputStream zipOut, File fileToZip) {
        if (!fileName.endsWith("/")) {
            fileName += "/";
        }

        File[] children = fileToZip.listFiles();
        if (children != null) {
            for (File childFile : children) {
                if(childFile.isDirectory())zipDirectory(fileName + childFile.getName(),zipOut,childFile);
              else zipOneFile(childFile, fileName + childFile.getName(), zipOut);
            }
        }
    }
    private void zipOneFile(File fileToZip, String fileName, ZipOutputStream zipOut) {
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void unzipFile(String filepath, String filename) throws IOException {

        String noExtension = filename;
        int slashIndex = noExtension.lastIndexOf('/');
        int dotIndex = noExtension.lastIndexOf('.');
        if (slashIndex > 0) {
            noExtension = noExtension.substring(slashIndex + 1, dotIndex);
        }
        filepath = filepath + "/" + noExtension;

        File destDir = new File(filepath);

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(filename));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile;

        if(zipEntry.isDirectory()){
            destFile = new File(destinationDir, zipEntry.getName());
        }
        else {
            String noExtension = zipEntry.getName();
            int slashIndex = noExtension.lastIndexOf('/');
            if (slashIndex > 0) {
                noExtension = noExtension.substring(slashIndex + 1);
            }
            System.out.println(noExtension);

            destFile = new File(destinationDir, noExtension);
        }

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    private String getFilepathNoExtension(String filepath){
        String noExtension = filepath;
        int dotIndex = noExtension.lastIndexOf('.');
        if (dotIndex > 0) {
            return noExtension.substring(0, dotIndex);
        }
        return filepath;
    }

}
