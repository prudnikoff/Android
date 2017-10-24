package com.worlds.prudnikoff.cipher;

import java.util.ArrayList;

class LFSR {

    private int registerSize;
    private int[] polynomial;
    private String key;
    private ArrayList<Byte> register;

    LFSR(int registerSize, int[] polynomial, String key) {
        this.registerSize = registerSize;
        this.polynomial = polynomial;
        this.key = key;
        register = new ArrayList<>();
    }

    void fillRegister() {
        register.clear();
        for (int i = 0; i < key.length(); i++) {
            register.add(i, Byte.parseByte(key.charAt(i) + ""));
        }
        while (register.size() < registerSize) {
            register.add((byte)1);
        }
        register.size();
    }

    byte getNext() {
        byte toInsert = 0;
        for (int i = 0; i < polynomial.length; i++) {
            toInsert += register.get(this.registerSize - polynomial[i]);
        }
        toInsert %= 2;
        register.add(toInsert);
        byte toPop = register.get(0);
        register.remove(0);
        return toPop;
    }
}

