package com.s3plan.gw.ninemanmorris.Model;

import android.app.Activity;
import android.util.Log;

import com.s3plan.gw.ninemanmorris.IO.FileManager;
import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;
import com.s3plan.gw.ninemanmorris.R;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class SaveHandler {

    public static void createSaveFile(Activity activity, String filePath) {
        GameHandler gameHandler = GameHandler.getInstance();
        FileManager.Write write = new FileManager.Write(activity, new SaveFile(gameHandler), filePath);
        write.run();
    }

    public static void readSaveFile(Activity activity, String filePath) {
        GameHandler gameHandler = GameHandler.getInstance();
        FileManager.Result result = FileManager.readFile(activity, filePath);
        if (result.msg != null) {
            SaveHandler.SaveFile saveFile = (SaveHandler.SaveFile)result.msg;
            gameHandler.setGameHandler(saveFile.getGameHandler());
            Log.i("Main", "Pos: " + gameHandler.typeOfCheckerAtPosition(1));
        }
        else {
            gameHandler.restartGame();
            Log.i("Main", "Could not load savefile");
        }
    }

    public static void createSavedGamesFile(Activity activity, String filePath) {
        SavedGames savedGames = SavedGames.getInstance();
        if (savedGames.getSavedGames() != null) {
            FileManager.Write write = new FileManager.Write(activity, savedGames.getSavedGames(), filePath);
            write.run();
        }
    }

    public static void readSavedGames(Activity activity, String filePath) {
        SavedGames savedGames = SavedGames.getInstance();
        FileManager.Result result = FileManager.readFile(activity, filePath);
        if (result.msg != null) {
            ArrayList<String> list = (ArrayList<String>) result.msg;
            savedGames.setSavedGames(list);
        }
        else {
            savedGames.setSavedGames(new ArrayList<String>());
            Log.i("Main", "no saved games");
        }
    }

    private static class SaveFile implements Serializable {
        private GameHandler gameHandler;

        public SaveFile(GameHandler gameHandler) {
            this.gameHandler = gameHandler;
        }

        public GameHandler getGameHandler() {
            return gameHandler;
        }
    }
}
