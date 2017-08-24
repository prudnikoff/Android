package com.worlds.prudnikoff.worlds;

import java.io.Serializable;

public class WordModel implements Serializable {

    private String partOfSpeech;
    private String headWord;
    private String definition;
    private String example;
    private boolean isMemorized;

    public WordModel(String partOfSpeech, String headWord, String definition, String example) {
        this.partOfSpeech = partOfSpeech;
        this.headWord = headWord;
        this.definition = definition;
        this.example = example;
        this.isMemorized = false;
    }

    String getPartOfSpeech() {
        return partOfSpeech;
    }

    boolean isMemorized() {
        return isMemorized;
    }

    void setMemorized(boolean isMemorized) {
        this.isMemorized = isMemorized;
    }

    String getHeadWord() {
        return headWord;
    }

    String getDefinition() {
        return definition;
    }

    String getExample() {
        return example;
    }
}
