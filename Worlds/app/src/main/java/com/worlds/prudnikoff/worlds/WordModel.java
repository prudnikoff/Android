package com.worlds.prudnikoff.worlds;

import java.io.Serializable;

public class WordModel implements Serializable {

    private String query;
    private DefinitionModel[] definitions;

    public WordModel(String query, DefinitionModel[] definitions) {
        this.query = query;
        this.definitions = definitions;
    }

    public String getQuery() {
        return query;
    }

    public DefinitionModel[] getDefinitions() {
        return definitions;
    }
}
