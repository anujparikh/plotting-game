package org.unknown.plottingapp.gamengine.utils;

import org.unknown.plottingapp.gamengine.datatypes.GameState;
import org.unknown.plottingengine.gamerecorder.models.GameRecord;

public class GameStateConvertorUtil {
    public static GameRecord convertToGameRecord(GameState gameState) {
        GameRecord gameRecord = new GameRecord();
        gameRecord.setX(Math.round(gameState.getCurrentPosition().x));
        gameRecord.setY(Math.round(gameState.getCurrentPosition().y));
        gameRecord.setVelocity(gameState.getVelocity());
        gameRecord.setHeading(gameState.getTheta());
        return gameRecord;
    }
}
