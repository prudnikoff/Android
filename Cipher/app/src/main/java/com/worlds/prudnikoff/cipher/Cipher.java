package com.worlds.prudnikoff.cipher;

import android.os.Environment;

import java.io.*;

abstract class Cipher {

    abstract void crypt();
    abstract String getLog();
    abstract String prepareKey(String key);

    byte[] readFile(String path) {
        byte[] buffer = null;
        FileInputStream fis = null;
        try {
            File backUp = new File(path);
            fis = new FileInputStream(backUp);
            int length = (int) backUp.length();
            buffer = new byte[length];
            fis.read(buffer, 0, length);
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return buffer;
    }

    void writeFile(byte[] buffer, String fileName) {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "Cipher/";
        FileOutputStream fos = null;
        try {
            File root = new File(path);
            if (!root.exists()) {
                root.mkdirs();
            }
            File backUp = new File(root, fileName);
            fos = new FileOutputStream(backUp);
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    void writeBinaryRep(String binaryRep) {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator;
        FileOutputStream fos = null;
        try {
            File backUp = new File(path, "binRep.txt");
            fos = new FileOutputStream(backUp);
            byte[] buffer = binaryRep.getBytes();
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
