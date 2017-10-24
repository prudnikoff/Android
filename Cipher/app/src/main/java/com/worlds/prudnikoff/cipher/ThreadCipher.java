package com.worlds.prudnikoff.cipher;

class ThreadCipher extends Cipher {

    private String log;
    private LFSR lfsr;
    private String binaryRep1;
    private String binaryRep2;
    private static final int[] polynomial = {30, 16, 15, 1};
    private String inputFilePath;

    ThreadCipher(String inputFilePath, String key) {
        this.inputFilePath = inputFilePath;
        lfsr = new LFSR(30, polynomial, prepareKey(key));
        log = "";
        binaryRep1 = "";
        binaryRep2 = "";
    }

    @Override
    void crypt() {
        lfsr.fillRegister();
        byte[] inputBuffer = readFile(inputFilePath);
        byte[] outputBuffer = new byte[inputBuffer.length];
        for (int i = 0; i < inputBuffer.length; i++) {
            byte inputByte = inputBuffer[i];
            byte outputByte = 0;
            for (int j = 0; j < 8; j++) {
                byte keyBit = lfsr.getNext();
                if (log.length() < 500) log += String.valueOf(keyBit);
                byte inputBit = (byte)((inputByte >> j) & 1);
                byte outputBit = (byte)(inputBit ^ keyBit);
                if (binaryRep1.length() < 100) binaryRep1 += String.valueOf(inputBit);
                if (binaryRep2.length() < 100) binaryRep2 += String.valueOf(outputBit);
                if (outputBit == 1) outputByte = (byte)(outputByte | (1 << j));
            }
            outputBuffer[i] = outputByte;
        }
        writeBinaryRep(binaryRep1 + "\n\n\n" + binaryRep2);
        writeFile(outputBuffer, inputFilePath.substring(inputFilePath.lastIndexOf('/') + 1));
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

    @Override
    String getLog() {
        return log;
    }
}
