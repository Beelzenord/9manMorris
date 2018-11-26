package com.s3plan.gw.ninemanmorris.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SavedGames implements Serializable {
    private List<String> savedGames;
    private static SavedGames model;

    private SavedGames() {
        savedGames = new ArrayList<>();
    }

    public static SavedGames getInstance() {
        if (model == null)
            model = new SavedGames();
        return model;
    }

    public List<String> getSavedGames() {
        return savedGames;
    }

    public void setSavedGames(List<String> savedGames) {
        this.savedGames = savedGames;
    }

    public void add(String name) {
        savedGames.add(name);
    }

    public void remove(String name) {
        savedGames.remove(name);
    }
}
