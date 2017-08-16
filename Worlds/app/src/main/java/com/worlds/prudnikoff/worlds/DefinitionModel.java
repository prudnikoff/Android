package com.worlds.prudnikoff.worlds;

import java.io.Serializable;

public class DefinitionModel implements Serializable {

    private String partOfSpeech;
    private String headWord;
    private String definition;
    private String example;
    private String americanPronunciations;
    private String britishPronunciations;
    private String soundAmericanPronunciationUrl;
    private String soundBritishPronunciationUrl;

    public DefinitionModel(String partOfSpeech, String headWord, String definition, String example,
                           String americanPronunciations, String britishPronunciations,
                           String soundAmericanPronunciationUrl, String soundBritishPronunciationUrl) {

        this.partOfSpeech = partOfSpeech;
        this.headWord = headWord;
        this.definition = definition;
        this.example = example;
        this.americanPronunciations = americanPronunciations;
        this.britishPronunciations = britishPronunciations;
        this.soundAmericanPronunciationUrl = soundAmericanPronunciationUrl;
        this.soundBritishPronunciationUrl = soundBritishPronunciationUrl;

    }

    public String getPartOfSpeech() {

        return partOfSpeech;

    }

    public String getSoundAmericanPronunciationUrl() {

        return soundAmericanPronunciationUrl;

    }

    public String getSoundBritishPronunciationUrl() {

        return soundBritishPronunciationUrl;

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
