package com.worlds.prudnikoff.worlds;

public class WordModel {

    private String query;
    private DefinitionModel[] definitions;

    public WordModel(String query, DefinitionModel[] definitions) {
        this.query = query;
        this.definitions = definitions;
    }
}
