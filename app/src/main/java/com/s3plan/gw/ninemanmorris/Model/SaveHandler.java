package com.s3plan.gw.ninemanmorris.Model;

import com.s3plan.gw.ninemanmorris.Model.GameState.GameHandler;

import java.io.Serializable;

public class SaveHandler {

    public static SaveFile createSaveFile(GameHandler gameHandler) {
        return new SaveFile(gameHandler);
    }

    public static class SaveFile implements Serializable {
        private GameHandler gameHandler;

        public SaveFile(GameHandler gameHandler) {
            this.gameHandler = gameHandler;
        }

        public GameHandler getGameHandler() {
            return gameHandler;
        }
    }
}
