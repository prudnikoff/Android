package com.worlds.prudnikoff.cipher;

class Geffe extends Cipher {

    private String log1;
    private String log2;
    private String log3;
    private String geffe;
    private String inputFilePath;
    private LFSR lfsr1;
    private LFSR lfsr2;
    private LFSR lfsr3;
    private String binaryRep1;
    private String binaryRep2;
    private static final int[] polynomial1 = {30, 16, 15, 1};
    private static final int[] polynomial2 = {38, 6, 5, 1};
    private static final int[] polynomial3 = {28, 3};

    Geffe(String inputFilePath, String key) {
        String[] keys = key.split(" ");
        this.inputFilePath = inputFilePath;
        log1 = "";
        log2 = "";
        log3 = "";
        geffe = "";
        binaryRep1 = "";
        binaryRep2 = "";
        lfsr1 = new LFSR(30, polynomial1, prepareKey(keys[0]));
        lfsr2 = new LFSR(38, polynomial2, prepareKey(keys[1]));
        lfsr3 = new LFSR(28, polynomial3, prepareKey(keys[2]));
    }

    @Override
    void crypt() {
        lfsr1.fillRegister();
        lfsr2.fillRegister();
        lfsr3.fillRegister();
        byte[] inputBuffer = readFile(inputFilePath);
        byte[] outputBuffer = new byte[inputBuffer.length];
        for (int i = 0; i < inputBuffer.length; i++) {
            byte inputByte = inputBuffer[i];
            byte outputByte = 0;
            for (int j = 0; j < 8; j++) {
                byte keyBit1 = lfsr1.getNext();
                byte keyBit2 = lfsr2.getNext();
                byte keyBit3 = lfsr3.getNext();
                if (log1.length() < 100) log1 += String.valueOf(keyBit1);
                if (log2.length() < 100) log2 += String.valueOf(keyBit2);
                if (log3.length() < 100) log3 += String.valueOf(keyBit3);
                byte finalKeyBit = (byte)((keyBit1 & keyBit2) | (~keyBit1 & keyBit3));
                byte inputBit = (byte)((inputByte >> j) & 1);
                byte outputBit = (byte)(inputBit ^ finalKeyBit);
                if (binaryRep1.length() < 100) binaryRep1 += String.valueOf(inputBit);
                if (binaryRep2.length() < 100) binaryRep2 += String.valueOf(outputBit);
                if (geffe.length() < 100) geffe += String.valueOf(finalKeyBit);
                if (outputBit == 1) outputByte = (byte)(outputByte | (1 << j));
            }
            outputBuffer[i] = outputByte;
        }
        writeBinaryRep(binaryRep1 + "\n\n\n" + binaryRep2);
        writeFile(outputBuffer, inputFilePath.substring(inputFilePath.lastIndexOf('/') + 1));
    }

    @Override
    String getLog() {
        return log1 + "\n\n" + log2 + "\n\n" + log3 + "\n\n" + geffe;
    }

    @Override
    String prepareKey(String key) {
        String preparedKey = "";
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch == '0' || ch == '1') {
                preparedKey += ch;
            }
        }
        return preparedKey;
    }
}
