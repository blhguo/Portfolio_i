package gameData;

import java.util.Map;

import game_engine.Engine;
//This class simply documents for the Authoring Environment the available methods and their input parameters, for ease of reference. In other words, it presents to Authoring a list of what Game data can do for Authoring, while hiding how it does those things
public interface AuthorInterface {
	public void saveData(Engine engine, String gameFolderName, String saveFileName, Map<String, String> metaMap, Map<String, String> ConfigMap) throws SaveDataException;
	public Engine loadData(String filePath) throws LoadDataException;
	public Map<String, String> openMeta(String filePath) throws LoadDataException;	
	public Map<String, String> openConfig(String filePath);
}