package com.worlds.prudnikoff.worlds;

public class DefinitionModel {

    private String partOfSpeech;
    private String headWord;
    private String definition;
    private String[] examples;
    private String[] pronunciations;

    public DefinitionModel(String partOfSpeech, String headWord, String definition, String[] examples, String[] pronunciations) {
        this.partOfSpeech = partOfSpeech;
        this.headWord = headWord;
        this.definition = definition;
        this.examples = examples;
        this.pronunciations = pronunciations;
    }
}
