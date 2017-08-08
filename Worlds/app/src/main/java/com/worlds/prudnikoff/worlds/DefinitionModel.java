package com.worlds.prudnikoff.worlds;

import java.io.Serializable;

public class DefinitionModel implements Serializable {

    private String partOfSpeech;
    private String headWord;
    private String definition;
    private String example;
    private String americanPronunciations;
    private String britishPronunciations;

    public DefinitionModel(String partOfSpeech, String headWord, String definition, String example, String americanPronunciations, String britishPronunciations) {
        this.partOfSpeech = partOfSpeech;
        this.headWord = headWord;
        this.definition = definition;
        this.example = example;
        this.americanPronunciations = americanPronunciations;
        this.britishPronunciations = britishPronunciations;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getHeadWord() {
        return headWord;
    }

    public String getDefinition() {
        return definition;
    }

    public String getExample() {
        return example;
    }

    public String getAmericanPronunciations() {
        return americanPronunciations;
    }

    public String getBritishPronunciations() {
        return britishPronunciations;
    }
}
