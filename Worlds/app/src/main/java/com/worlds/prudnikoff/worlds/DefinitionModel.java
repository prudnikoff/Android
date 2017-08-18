package com.worlds.prudnikoff.worlds;

import java.io.Serializable;

public class DefinitionModel implements Serializable {

    private String partOfSpeech;
    private String headWord;
    private String definition;
    private String example;

    public DefinitionModel(String partOfSpeech, String headWord, String definition, String example) {

        this.partOfSpeech = partOfSpeech;
        this.headWord = headWord;
        this.definition = definition;
        this.example = example;

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

}
