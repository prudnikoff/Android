package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;

public class WordModel implements Serializable {

    private String query;
    private ArrayList<DefinitionModel> definitions;

    public WordModel(String query, ArrayList<DefinitionModel> definitions) {
        this.query = query;
        this.definitions = definitions;
    }

    public String getQuery() {
        return query;
    }

    public ArrayList<DefinitionModel> getDefinitions() {
        return definitions;
    }
}
