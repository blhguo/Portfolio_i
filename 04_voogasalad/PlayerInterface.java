package gameData;

import java.util.Map;

import game_engine.Engine;
//This class simply documents for the Player the available methods and their input parameters, for ease of reference. In other words, it presents to Authoring a list of what Game data can do for Player, while hiding how it does those things
public interface PlayerInterface {
	public void saveData(Engine engine, String gameName, String saveFileName, boolean isPlayer) throws SaveDataException;
	public Engine loadData(String filePath) throws LoadDataException;
	public Map<String, String> openMeta(String filePath) throws LoadDataException;
	public Map<String, String> openConfig(String filePath);
}