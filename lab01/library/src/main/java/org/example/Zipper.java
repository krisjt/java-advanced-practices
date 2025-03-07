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

        String noExtension = fileName;
        int dotIndex = noExtension.lastIndexOf('.');
        if (dotIndex > 0) {
            noExtension = noExtension.substring(0, dotIndex);
        }

        FileOutputStream fos = new FileOutputStream(noExtension + "_compressed.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        if (fileToZip.isDirectory()) {
            zipDirectory(fileName,zipOut,fileToZip);

            String hashValue = messageDigestAlgorithm.getHash(fileName);
            File hashFile = File.createTempFile("hash_", ".md5", fileToZip.getParentFile());

            try (FileWriter writer = new FileWriter(hashFile)) {
                writer.write(hashValue);
            }
            zipOneFile(hashFile, noExtension + "_hash.md5", zipOut);

        }else{
            zipOneFile(fileToZip,fileName,zipOut);

            String hashValue = messageDigestAlgorithm.getHash(fileName);
            File hashFile = File.createTempFile("hash_", ".md5", fileToZip.getParentFile());

            try (FileWriter writer = new FileWriter(hashFile)) {
                writer.write(hashValue);
            }
            zipOneFile(hashFile, noExtension + "_hash.md5", zipOut);
        }
        zipOut.closeEntry();
    }

    private void zipDirectory(String fileName, ZipOutputStream zipOut, File fileToZip) throws IOException {
        if (!fileName.endsWith("/")) {
            fileName += "/";
        }
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        zipOut.closeEntry();

        File[] children = fileToZip.listFiles();
        if (children != null) {
            for (File childFile : children) {
                zipOneFile(childFile, fileName + childFile.getName(), zipOut);
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
            throw new RuntimeException(e);
        }
    }


    public void unzipFile(String filepath, String filename) throws IOException {

        //katalog do ktorego rozpakowane zosatnie
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
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
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

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        String noExtension = zipEntry.getName();

        int slashIndex = noExtension.lastIndexOf('/');
        if (slashIndex > 0) {
            noExtension = noExtension.substring(slashIndex+1);
        }

        System.out.println(noExtension);

        File destFile = new File(destinationDir, noExtension);

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }







//    public void unzipFile(String filepath, String filename) throws IOException {
//
//        System.out.println("File name: " + filename);
//        System.out.println("File path: " + filepath);
//
//        //katalog do ktorego rozpakowane zosatnie
//        File destDir = new File(filepath);
//        System.out.println("Unpacking to: " + destDir.getAbsolutePath());
//
//        byte[] buffer = new byte[1024];
//        ZipInputStream zis = new ZipInputStream(new FileInputStream(filename));
//        ZipEntry zipEntry = zis.getNextEntry();
//        while (zipEntry != null) {
//            File newFile = newFile(destDir, zipEntry);
//            if (zipEntry.isDirectory()) {
//                if (!newFile.isDirectory() && !newFile.mkdirs()) {
//                    throw new IOException("Failed to create directory " + newFile);
//                }
//            } else {
//                // fix for Windows-created archives
//                File parent = newFile.getParentFile();
//                if (!parent.isDirectory() && !parent.mkdirs()) {
//                    throw new IOException("Failed to create directory " + parent);
//                }
//
//                // write file content
//                FileOutputStream fos = new FileOutputStream(newFile);
//                int len;
//                while ((len = zis.read(buffer)) > 0) {
//                    fos.write(buffer, 0, len);
//                }
//                fos.close();
//            }
//            zipEntry = zis.getNextEntry();
//        }
//        zis.closeEntry();
//        zis.close();
//    }


//    public void unzipFile(String directoryName, String filename) throws IOException {
//
//        System.out.println("Directory name: " + directoryName);
//        System.out.println("filename: " + filename);
//
//        File zipFile = new File(filename);
//        File parentDir = zipFile.getParentFile();
//
//        //katalog do ktorego rozpakowane zosatnie
//        File destDir = new File(parentDir, directoryName);
//
//        if (!destDir.exists() && !destDir.mkdirs()) {
//            throw new IOException("Failed to create directory " + destDir);
//        }
//
//        System.out.println("Unpacking to: " + destDir.getAbsolutePath());
//
//
//        byte[] buffer = new byte[1024];
//        ZipInputStream zis = new ZipInputStream(new FileInputStream(filename));
//        ZipEntry zipEntry = zis.getNextEntry();
//        while (zipEntry != null) {
//            File newFile = newFile(destDir, zipEntry);
//            if (zipEntry.isDirectory()) {
//                if (!newFile.isDirectory() && !newFile.mkdirs()) {
//                    throw new IOException("Failed to create directory " + newFile);
//                }
//            } else {
//
//                File parent = newFile.getParentFile();
//                if (!parent.isDirectory() && !parent.mkdirs()) {
//                    throw new IOException("Failed to create directory " + parent);
//                }
//
//                FileOutputStream fos = new FileOutputStream(newFile);
//                int len;
//                while ((len = zis.read(buffer)) > 0) {
//                    fos.write(buffer, 0, len);
//                }
//                fos.close();
//            }
//            zipEntry = zis.getNextEntry();
//        }
//        zis.closeEntry();
//        zis.close();
//    }
//
//    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
//        String noExtension = zipEntry.getName();
//
//        int slashIndex = noExtension.lastIndexOf('/');
//        if (slashIndex > 0) {
//            noExtension = noExtension.substring(slashIndex+1);
//        }
//
//        System.out.println(noExtension);

//        File destFile = new File(destinationDir, zipEntry.getName());
//
//        String destDirPath = destinationDir.getCanonicalPath();
//        String destFilePath = destFile.getCanonicalPath();
//
//        if (!destFilePath.startsWith(destDirPath + File.separator)) {
//            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
//        }
//
//        return destFile;
//    }
}
