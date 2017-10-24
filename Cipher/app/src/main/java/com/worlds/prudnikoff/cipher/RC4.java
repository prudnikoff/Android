package com.worlds.prudnikoff.cipher;

import java.util.ArrayList;

class RC4 extends Cipher {

    private String log;
    private String inputFilePath;
    private int[] preparedKey;
    private int[] box;
    private String binaryRep;
    private int i;
    private int j;

    RC4(String inputFilePath, String key) {
        this.inputFilePath = inputFilePath;
        prepareKey(key);
        log = "";
        binaryRep = "";
    }

    byte getNext() {
        i = (i + 1) % 256;
        j = (j + box[i]) % 256;
        int temp = box[i];
        box[i] = box[j];
        box[j] = temp;
        return (byte)box[(box[i] + box[j]) % 256];
    }

    private void fillBox() {
        i = 0;
        j = 0;
        box = new int[256];
        for (int i = 0; i < box.length; i++) {
            box[i] = i;
        }
        int j = 0;
        for (int i = 0; i < box.length; i++) {
            j = (j + box[i] + preparedKey[i % preparedKey.length]) % 256;
            int temp = box[i];
            box[i] = box[j];
            box[j] = temp;
        }
    }

    @Override
    void crypt() {
        fillBox();
        byte[] inputBuffer = readFile(inputFilePath);
        byte[] outputBuffer = new byte[inputBuffer.length];
        for (int i = 0; i < inputBuffer.length; i++) {
            byte inputByte = inputBuffer[i];
            byte keyByte = getNext();
            if(log.length() < 1000) log += String.valueOf(keyByte + " ");
            byte outputByte = (byte)(inputByte ^ keyByte);
            if (binaryRep.length() < 5000) binaryRep += String.valueOf(outputByte + " ");
            outputBuffer[i] = outputByte;
        }
        writeBinaryRep(binaryRep);
        writeFile(outputBuffer, inputFilePath.substring(inputFilePath.lastIndexOf('/') + 1));
    }

    @Override
    String getLog() {
        return log;
    }

    @Override
    String prepareKey(String key) {
        ArrayList<Integer> preparedKeyList = new ArrayList<>();
        String preparedKeyStr = "";
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch >= '0' && ch <= '9' || ch == ' ') {
                preparedKeyStr += ch;
            }
        }
        String[] keyArr = preparedKeyStr.split(" ");
        for (i = 0; i < keyArr.length; i++) {
            int item = Integer.parseInt(keyArr[i]);
            if (item >= 0 && item < 256) {
                preparedKeyList.add(item);
            }
        }
        if (preparedKeyList.isEmpty()) {
            preparedKeyList.add(42);
            preparedKeyList.add(228);
        }
        preparedKey = buildIntArray(preparedKeyList);
        return null;
    }

    private int[] buildIntArray(ArrayList<Integer> integers) {
        int[] ints = new int[integers.size()];
        int i = 0;
        for (Integer n: integers) {
            ints[i++] = n;
        }
        return ints;
    }
}
