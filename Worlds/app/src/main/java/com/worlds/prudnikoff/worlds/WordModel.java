package com.worlds.prudnikoff.worlds;

import java.io.Serializable;

class WordModel implements Serializable {

    private String partOfSpeech;
    private String headWord;
    private String definition;
    private String examples;
    private String synonyms;
    private boolean isMemorized;

    WordModel(String partOfSpeech, String headWord, String definition, String examples, String synonyms) {
        this.partOfSpeech = partOfSpeech;
        this.headWord = headWord;
        this.definition = definition;
        this.examples = examples;
        this.synonyms = synonyms;
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

    String getExamples() {
        return examples;
    }

    String getSynonyms() {
        return synonyms;
    }

    void setData(String partOfSpeech, String headWord, String definition, String example, String synonyms) {
        this.partOfSpeech = partOfSpeech;
        this.headWord = headWord;
        this.definition = definition;
        this.examples = example;
        this.synonyms = synonyms;
    }
}
