CompSci 308: VOOGASalad Analysis
===================

> This is the link to the assignment: [VOOGASalad](http://www.cs.duke.edu/courses/compsci308/current/assign/04_voogasalad/)


Design Review
=======

### Overall Design
#### Authoring Environment
The Authoring Environment's design is, from a high level, a front-facing interface that allows the author of a game to essentially drag and drop elements onto a crafting platform. To accomplish this, they have several panes devoted to different aspects of the game authoring, for example events, entities, and level preferences. The process for adding a new entity (any sort of object) to the game is simple. When the user drags and drops the entity into the authoring screen, that entity is added to a list of entities representing a level, and each of those entities will have optional components that are added onto a list of components. Thus, the saving hierarchy looks something like:
- Game Engine Object contains a list of Level Objects
- Level Object contains a list of Entities
- Entity contains a list of components, representing the different properties each entitiy can take. 

As a result of our design, especially regarding engine, the job of Authoring and Playing becomes much easier. This is the power of the Entity-Component System. Authoring depends on the Engine to be able to accomodate whatever features they wish to add, and for the Engine to properly add them and instantiate the objects and instance variables. There isn't any dependency on Player, though there is a significant dependency on the reliability of loading a file to be edited. This means that Authoring is relying on Game Data to properly preserve and be able to reload that same game engine and all its component entities and components to be re-edited and changed later on. 

The Player's design is, from a high level, a simple presentation of the objects presented to the user. The Player keeps track of time by having an internal clock representing the time elapsed, and every fixed number of time it makes a call to engine for an update. Engine then presents the player with the new objects and their attributed data, and player, using a camera package within JavaFX, simply renders a certain portion of that screen. Several features, including pausing and play speed were added, with the simple change of changing the frequency and parameter of calling the Engine's update. As a result of ECS, the player does not really have to worry about how anything works or is saved; the Game Engine handles almost all of that. The biggest player dependency is on the Engine to handle the interactions and for the Systems design of the Engine to properly function. If something happens and an interaction is bugged, it is entirely an Engine issue, as Player simply displays it. Player is also dependent on Game Data to present reliable and editable Game Engine Objects (whether loading a clean game or a played game), and to that end the Player depends on the Game Data to function. In addition, because there is the case of user save files, the Player also relies upon Game Data to properly store and preserve new save game engines and their proper file paths; a feature was added to allow the user to define the file name however they would like.

Game Data's design is, from a high level, a simple serialization of the game engine object and the preservation of that data. In essence, the Game Data simply takes in an Engine object and saves it; later, when you want to reload that same Engine object, as a result of XStream serialization, Data is able to return to you the exact same object you passed it and instantiate it. More detail will be provided later. 

Game Engine's design is, from a high level, a focus on flexibility. Expanding on the ECS design pattern, Game Engine allows for new features to be added on the fly using the component system. For example, if I wanted to add a "ZPhysics" aspect to an entity, it would not be difficult to simply create that as a component. Little accomodations need to be made elsewhere to account for this, as Player, Authoring, and Data are effectively independent from the rest of the Engine. Because Engine is at the core of the game, it does not really have many dependencies, though internally it does. 

This design is tied to the current genre in that the "win" conditions are set in stone. There are a finite number of choices for any given entity and behavior, and that design choice as a whole limits the game genre. It is possible to create other genres such as Connect Four or BattleShip, but because there is no way to add win conditions native to those game types, nor a way to create a flexible, player-level set up, our current implementation does not allow for that genre. In addition, should you want to build a role-playing game, there is significant difficulty in the lack of a proper story implementation or player-level preferences. For example, there is currently no component to save anything you would really want to be saved in an RPG, ie things like item inventory and level, without doing some significant coding changes to the components and writing entirely new components all-together. To handle different genres, much greater variety in terms of win conditions and a very much greater variety in the components available. Some limitation, perhaps preset or "game type" limitation need to be made, as some win conditions do not apply to some games, and it is necessary to limit the accessibility of those win conditions (for example, "four in a row of the same color" should not be allowed to be added to a 2D platformer). 

The only things that needs to be present to represent a game are several resource files (things like images, configuration files, etc) and most importantly a Game Engine Object. No new code needs to be added by the author, but these files must be present for any game to be built. 
- Game Engine Object (saved in a .xml resource file)
- Game configuration files (several .properties files)
- Game Image Files and Assets (several images and music/sound files).

- > Package 1:  game_engine.event.actions.micro
This package is readable in that the methods are very straight-forward and easy to understand. For example, because every action has two parts to it, one part effectively behaving as a constructor and setting up the data to be performed on, and the other as actually changing the values within the game itself. More specifically, the "execute" method very clearly and comprehensibly performs the "action" required using the data set up by the constructor. A big part of the readability is the length, or lack of it, in the code; there simply is not much to go wrong. 
```java
	public void execute() {
		Component<Double> comp = myEntity.getComponent(myComponentClass);
		String expression = comp.getValue() + myExpression + myValue; 
		BigDecimal result =  new Expression(expression).eval();
		comp.setValue(result.doubleValue());
	}
``` 
- > This package is also more encapsulated because, as a smaller package, there are no real dependencies or anything to track within this package. This is one of the big advantages of the Entity Component System. The dependencies are consistent across all of the same level packages, in that the only dependencies are on the existence and implementation of ECS. For example, in the constructor, a clear dependency exists in terms of what you are passing in, and that dependency is consistent across same-level packages. 
```java
	public DataChangeAction(Entity entity, Class<? extends Component<Double>> componentClass, String expression, double value) {
		myEntity = entity;
		myComponentClass = componentClass;
		myExpression = expression;
		myValue = value;
	}
```
- > Reading this code has taught me to try to diversity and divide packages as much as I can in terms of function. In doing so, the code becomes much more readable, and the imports and purposes of each class becomes much clearer. The more classes we can create to handle different aspects of the code, the aesier it is for someone to be able to easily understand what has been written and how functionality has been divided up. 
- > Package 2: game_player
This package is much more difficult to understand and much less readable simply because the methods are longer and more complex. As a result of having many moving parts, it is not immediately comprehensible and where some method calls are linking to is not always apparent. In addition, the implementation and necessity of using JavaFX makes it such that a lot of the imported objects are not necessarily obvious as to what they do, or at least not intuitively so. As a result of all these factors, though the code is generally clear and logical, it is not really broken up into bite-sized pieces for someone to read and digest. The presence of magic nunmbers also inhibits understanding, as sometimes you question things like "why HBox(30)"
```java
	private Pane setObjects() {
		HBox center = new HBox(30);
		center.setAlignment(Pos.CENTER);

		gameBackground = new Image("gray.png");
		gameImageView = new ImageView();
		gameImageView.setImage(gameBackground);

		BackgroundImage back = new BackgroundImage(new Image("background.png"), BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		center.setBackground(new Background(back));

		order = new VBox(20);

		order.setAlignment(Pos.CENTER);
		center.getChildren().add(order);

		view = new Pane();
		view.setPrefSize(1000, 730);
		subRoot = new Group();
		subScene = new SubScene(subRoot, SUBSCENE_WIDTH, SUBSCENE_HEIGHT, false, null);
		game = new BackgroundImage(gameBackground, BackgroundRepeat.REPEAT, 
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		view.setBackground(new Background(game));

		order.getChildren().add(subScene);
		subRoot.getChildren().add(view);

		menu.addMenu(order);

		order.setBackground(new Background(new BackgroundFill(backColor,null,null)));
		return center;
	}

``` 
- > Unlike the first package, this package is more poorly organized and difficult to read. Because this package is likely referencing other classes and their operation is more hidden and more convoluted, there are more dependencies and challenges in the reading of the code. A large part of this challenge, I think, is the fact that too much functionality is encapsulated within this package; there's too much handled by this package alone, and that makes it difficult to read and understand. For example, data presentation and rendering is handled by this package, but other things like navigation, buttons, data management, and file selection are all put under the same package, making a non-homogenous mixture and making it difficult to read. 
``` java
DataManager.java
	public void initialize(InstanceStorage storage) {
		gameStage = storage.getStage();
		keyPrefs = new HashMap<KeyCode,String>();
		keyPrefsReversed = new HashMap<String,KeyCode>();
		engineMap = new HashMap<String,KeyCode>();
		gameInputs = new ArrayList<String>();
		
		String[] testInputs = {"left","right","up","down"}; //To be read in from data
		KeyCode[] engineInputs = {KeyCode.A,KeyCode.D,KeyCode.W,KeyCode.S}; //also defaults from data
		for(int i=0;i<testInputs.length;i++) {
			gameInputs.add(testInputs[i]);
			engineMap.put(testInputs[i],engineInputs[i]);
		}
		initializeInputs();
	}
```
```java 
ButtonMaker.java
	protected List<Button> makeMenuButton() {
		List<Button>buttonList = new ArrayList<Button>();

		for (int i=0; i<images.size(); i++) {
			Image im = new Image("game_player_resources/" + images.get(i));
			ImageView buttonImageView = new ImageView(im);

			buttonImageView.setFitHeight(30);
			buttonImageView.setFitWidth(30);

			Button button = buttons.get(i);
			button = new Button("", buttonImageView);
			button.getStyleClass().add("button-nav");
			String temp = ""+i;
			button.setOnAction(click -> {
				playerView.handleUI(Integer.decode(temp));
			});
			buttonList.add(button);

		}
		return buttonList;

	}
```
- > Reading this code has taught me to make good use of packages. Without having read this code, is it likely that I would have done the same thing; create all my classes and functionality within the same package. Now I realized that this technique and style is a hinderance to everyone reading the code, because it becomes difficult to understand all of it, and it becomes necessary to peruse all of the code in order to understand how it functions. Dividing up functionality is useful because it minimizes the amount of code needed to be understood to understand a specific function or use case. 
### Your Design
At a high level, Game Data consists of only one class, designed to manipulate data however the user prefers. Within that class, the biggest relationship is between the main methods (saveData, loadData, etc) and their respective helper functions. More specifically, the saveData class used by the Authoring Environment, pasted below, calls the generic saveData class (used by the player) and other methods. Because saveData is responsible for saving metadata and configuration data, it calls helper methods also responsible for those two things. This way, the methods used by the player (who does not need to save configuration data and meta data, only read them) and the authoring environment (access to saving and editing all data) are properly accessible. Unlike saving, loading functions differently, since saving does not need to return anything while loading needs to return different data structures, depending on the specific load function being called. For readability, it might be a good idea to split the functionality such that saving is in a single class, and loading is in a different class. Doing so would not be difficult, but for simplicity and ease of reference by player and authoring, I decided to merge them into one larger class. 
For Authoring to save:

```java 
ManipData.java
	public void saveData(Engine engine, String gameFolderName, String saveFileName, Map<String, String> metaMap, Map<String, String> ConfigMap) throws SaveDataException {
		saveData(engine, gameFolderName, saveFileName, false);

		saveConfig(gameFolderName, ConfigMap, saveFileName);
		
		saveMeta(gameFolderName, metaMap);
	}

```
For Player to save: 
```java 
public void saveData(Engine engine, String gameName, String saveFileName, boolean isPlayer) throws SaveDataException {
		File file = new File("games/"+gameName);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		file = new File("games/" + gameName + "/" + saveFileName +".xml");
		if (isPlayer) {
			file = new File("games/"+gameName+"/gameSaves");
			if(!file.exists()) {
				file.mkdirs();
			}
		file = new File("games/"+gameName+"/gameSaves/" + saveFileName + ".xml");
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new SaveDataException(e);
			}
		}
		if (file.exists()) {
			try {
				FileOutputStream writer = new FileOutputStream(file);
				writer.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
				writer.write(("<higher>").getBytes("UTF-8"));
				saveEngine(engine, writer);
				writer.write(("</higher>").getBytes("UTF-8"));

			} catch (IOException e) {
				throw new SaveDataException(e);
			}
		}
	}
```
Helper function to save configuration files:
```java 
	private void saveConfig(String configLoc, Map<String, String> configMap, String configName) throws SaveDataException {
		
		File file = new File("games/"+configLoc);
		if(!file.exists()) {
			file.mkdirs();
		}
		try {
			Properties param = new Properties();
			
			for (int i = 0; i < configMap.size(); i ++) {
				param.setProperty(AuthRes.getStringKeys("key" + i), configMap.get(AuthRes.getStringKeys("key" + i)));
			}
			
			file = new File("games/" + configLoc + "/" + configName + "config.properties");
			if (!file.exists()) {
				try {file.createNewFile();}
				catch (IOException e) {
					throw new SaveDataException(e);
				}
				
			}
			FileOutputStream fos = new FileOutputStream(file);
			param.store(fos, "Configuration file");
			fos.close();
		}
		catch(FileNotFoundException e) {
			throw new SaveDataException(e);
		}
		catch(IOException e) {
			throw new SaveDataException(e);
		}
		
	}
```
Helper method to save data that is unique to each game and completely mutable:
```java 

	private void saveMeta(String gameName, Map<String, String> metaMap) throws SaveDataException {
		File file = new File("games/" + gameName + "/metaData.xml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			}
			catch (IOException e) {
				throw new SaveDataException(e);
			}
		}
		try {
			FileOutputStream writer = new FileOutputStream(file);
			writer.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
			writer.write("<stuff>".getBytes("UTF-8"));
			int counter = 0;
			for (String k: metaMap.keySet()) {
				saveSingleEntry(metaMap.get(k), k, counter, true, writer);
				counter++;
			}
			writer.write("</stuff>".getBytes("UTF-8"));
		} catch (IOException e) {
			throw new SaveDataException(e);
		}
	}
```

- Features
 > One feature that I feel is well implemented is the file management. With saveData nested within a different saveData (with different parameters), the saving of file locations changes a bit. Looking at the code, however, it becomes clear that the files are well organized, regardless of what the author decides to name them. More specifically, the file hierarchy follows this general format:
- vooga/games/Mario/mario.xml
- vooga/games/Mario/metadata.xml
- vooga/games/Mario/Mario_configData.xml
- vooga/games/Mario/gameSaves/brandonsGame.xml
 > This hierarchy is neat because any "edited" files are saved in their own folders, while the original "clean" game is saved in its own unique folder. An advantaged posed here is that working on the "clean" game does not impact the saved games at all; they are completely and totally independent. In addition, as an extra safety precaution, player can only save to the "gameSaves" folder; it does not have access elsewhere. That way, it cannot edit the crucial metadata and configData files that are in a separate folder. Error checking was also built in to ensure that the folder that we are writing to exists, and that any "old" files will be intentionally overwritten. For example, if I named my game file "brandon", and I played to level 7, saved, and reloaded a week later, I would load "gameSaves/brandon.xml", and save to "brandon.xml", overwriting the old file. If, for instance, I did not want to overwrite the old file (similar struture to how Civilization 5 handles save files), I would simply have to name it something different. In player, it is possible and relatively easy to write functionality to have the file name default to the same as the loaded file name, and to overwrite automatically (unless asked not to by the gamer). This code does depend significantly on whether every object in the Entity Component System is serializable, and more importantly that no unique methods exist (ie, functionality is stored within the components). If these two assumptions cannot be made, then the perservation fails. Initially, we had to deal with the problem of if certain filepaths already existed or were edited (through File Explorer GUI or other means) by the user; this was solved by having the player or authoring environment extract the necessary information from the file chooser and passing it down. Now, the user can arbitrarily change the file names and folder names (but not the architecture, ie gameSaves must still exist and hold the "dirty" games) without impacting the code. 
```java 
	public Engine loadData(String filePath) throws LoadDataException {
		try {
			File load = new File(filePath);
			return openFile(load);
		} catch (ParserConfigurationException e) {
			throw new LoadDataException(e);
		}
		}
```
```java 
	private Engine openFile(File file) throws ParserConfigurationException, LoadDataException{
		Engine lilGuy = new Engine();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		String filePath = file.getAbsolutePath();
		String fileType = filePath.substring(filePath.length()-FILE_EXTENSION);

		if (!fileType.equals(".xml")) {
			System.out.println("You dun goofed");
		};
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc;

			try {
				doc = dBuilder.parse(file);

				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("higher");
				System.out.println();
				Node nNode = nList.item(0);
				Element eElement = (Element) nNode;			
				String s = nodeToString(eElement.getElementsByTagName("data").item(0).getFirstChild());
				lilGuy = (Engine) deserializer.fromXML(s);
				return lilGuy;
				
			} catch (SAXException e) {
				throw new LoadDataException(e);
			}
		} catch (IOException e) {
			throw new LoadDataException(e);
		}
		
	}
```

> A second feature I built that could definitely use improvement is neatness and cleanliness of the xml saving. While it does not necessarily have an impact on the functionality of the code (partially why it became so messy in the first place), the way I've saved to XML is quite illogical. Initially, with the initial foray into the design of the serialization and how it works, random tags were used to store and save information. A good example of this is the version of ManipData used during the midpoint demonstration (commit SHA 36f98743)
```java
 public void saveData(List<Abs> levels) {
		int counter = 0;
		try {
			//this writes only one file
			fos = new FileOutputStream("gameDataSave"+"someuniquefactor"+".xml");
	        try {
	        	//writes xml header and then the number of data objects inside
				fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
				String nums = Integer.toString(levels.size());
				fos.write(("<higher "+"info='"+nums+"'>").getBytes("UTF-8"));
				for(Abs l: levels) {
					saveLevel(l, counter);
					counter++;
				} 
				fos.write("</higher>".getBytes("UTF-8"));

	        } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if(fos != null){
	            try{
	                fos.close();
	            }catch (IOException e) {
	                e.printStackTrace(); //TODO
	            }
	        }
	    }
	}

```
> At that point, our design was to handle a list of levels and to serialize those. To that end, instead of serializing a list object, we iterated through the list and saved each individual level under the tag "dataN", with "N" as an integer representing the level number. A counter was implemented to iterate each time we saved a level, so that later on when we loaded that data would be stored in the xml, and the for-loop would have a set number of times to execute. 

```java 
	private void openFile(File file) throws ParserConfigurationException{
		System.out.println(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        String filePath = file.getAbsolutePath();
        String fileType = filePath.substring(filePath.length()-FILE_EXTENSION);
        if (!fileType.equals(".xml")) {
        	System.out.println("You dun goofed");
        };
        //maybe a func to check that the file is an xml extension
        try {
        	dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			try {
				doc = dBuilder.parse(file);

				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("higher");
				Node nNode = nList.item(0);
				Element eElement = (Element) nNode;
				int nums = Integer.parseInt(eElement.getAttribute("info"));
				for(int i=0;i<nums;i++) {

					String s = nodeToString(eElement.getElementsByTagName("data"+Integer.toString(i)).item(0).getFirstChild());
					System.out.println(s);
					Abs lilGuy = (Abs) deserializer.fromXML(s);
					System.out.println(lilGuy);
					levellist.add(lilGuy);
				}
				System.out.println(levellist);
				
			} catch (SAXException e) {
				System.out.println("here1");
				return; //TODO
			}
        } catch (IOException e) {
			System.out.println("here2");

        		return; //TODO
        }
	}
```
> When we shifted away from saving levels and to saving a single game engine object at the request of Game Authoring Environment, we changed the code to adapt to this case. It was a relatively simple update, but a lot of the internal funcitonality (level "counting", made up tags, etc) were preserved simply because they did not break the code. There was no real motivation to change it if they were functioning perfectly, and we had bigger challenges to move on to. As a result, a lot of unnecessary, frivolous code exists within the generate xml files simply due to laziness. Fortunately, since very rarely will you be looking at the serialized xml file, most of this antiquity is hidden by the deserialization, which was also written to accomodate these false tags. No real design changes had to be made. 

### Flexibilty
My final API balances power and simplicity quite well, in that it maximizes both. 
> Power:
- > Designed with flexibility at the forefront, the current design is able to allow the user to express even more creativity than otherwise. This can be seen in three main ways. The first is the fact that we serialize and save a game engine object. The power in this decision is that, from a game data point of view, the saving simply is responsible for preserving the game engine object and all the instance variables and components of it. As a result of this cascading effect, it is possible to accomodate a very wide variety of game types, represented by different game engines. In fact, any ECS type design can be serialized by the current data class. To further improve upon the flexibility, I decided to split Metadata into two types, mutable and consistent. Essentially, mutable refers to the parts of the metadata (data not contained within the game engine object) that are completely up to the user to define. If the user wants to add a copyright, they can type two strings, "copyright" and "Penguin Publishing", Should the author wish to add instructions, rules, or even a dedication, they can do so relatively easily, simply by telling the Authoring environment what to label the data and what to store. It is infinitely expandable, up to the size of the computer's free memory, and completely customizable. The one drawback is the inability to have two "dedication" entries; the people being honored must occupy the same space. In addition, another strength comes from the deicison to have consistent data. What this data encompasses is data such as "Game title", "author(s)", and thumbnail filepath. From the persepctive of game Data, because I read in these "possible and required parameters" from a resource file, editing the possible strings is extremely easy and flexible: in fact, from a data perspective, the only change I would need to make is to change the resource file by adding or changing key-value pairings. 

```java 
#Properties file for configuration mapping
key0 = Name
key1 = Thumbnail
key2 = Author
```
> Simplicity
- > The game data interface was designed to have the absolute simplest access from both player and authoring. To that end, as has already been enumerated, only one method exists (for each player and authoring) to save all the required data. This means that, implementaiton wise, all that needs to be done is to instantiate a new "ManipData" object (who's constructor takes in no arguments, i.e ManipData manipulation = new ManipData();), and to call manipulation.saveData(param), with the required param. In fact, in this regard the only difference for authoring and player is the input parameters; every other filepath and save file distintion is handled privately by Game Data. 

For Authoring to save:

```java 
ManipData.java
	public void saveData(Engine engine, String gameFolderName, String saveFileName, Map<String, String> metaMap, Map<String, String> ConfigMap) throws SaveDataException {
		saveData(engine, gameFolderName, saveFileName, false);

		saveConfig(gameFolderName, ConfigMap, saveFileName);
		
		saveMeta(gameFolderName, metaMap);
	}

```
For Player to save: 
```java 
public void saveData(Engine engine, String gameName, String saveFileName, boolean isPlayer) throws SaveDataException {
		File file = new File("games/"+gameName);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		file = new File("games/" + gameName + "/" + saveFileName +".xml");
		if (isPlayer) {
			file = new File("games/"+gameName+"/gameSaves");
			if(!file.exists()) {
				file.mkdirs();
			}
		file = new File("games/"+gameName+"/gameSaves/" + saveFileName + ".xml");
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new SaveDataException(e);
			}
		}
		if (file.exists()) {
			try {
				FileOutputStream writer = new FileOutputStream(file);
				writer.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
				writer.write(("<higher>").getBytes("UTF-8"));
				saveEngine(engine, writer);
				writer.write(("</higher>").getBytes("UTF-8"));

			} catch (IOException e) {
				throw new SaveDataException(e);
			}
		}
	}
```
- > Loading, though somewhat more complicated, is still very simple. Three methods are needed, one for each the Game Engine Object, MetaData Map, and Configuration Data Map. It is inevitable that we use such a combination simply because the return data types are unique. Each of these, however, is simple in that their parameters are identical: a simple file path to load from. The only mistake that can be made is that an incorrect file path is given, in which case an exception is thrown saying file not found. In addition, loading also checks to ensure that the input file is a .xml file. Any complex deserialization or reading/writing is hidden from the rest of the game, especially the Game Authoring Environment and the Game Player.
```java
	public Engine loadData(String filePath) throws LoadDataException {
		try {
			File load = new File(filePath);
			return openFile(load);
		} catch (ParserConfigurationException e) {
			throw new LoadDataException(e);
		}
		}
	
	public Map<String, String> openMeta(String filePath) throws LoadDataException {
		
		File file = new File(filePath);
		
		Map<String, String> metaMap = new HashMap<>();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			try {
				doc = dBuilder.parse(file);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("stuff");
				NodeList actualList = nList.item(0).getChildNodes();
				Node nNode = nList.item(0);
				Element eElement = (Element) nNode;
				String[] keyArr = new String[actualList.getLength() / 2];
				String[] valArr = new String[actualList.getLength() / 2];
				for (int i = 0; i < actualList.getLength() / 2; i++){
					String kTagName = "key" + i;
					String vTagName = "value" + i;
					String key = eElement.getElementsByTagName(kTagName).item(0).getTextContent();
					String value = eElement.getElementsByTagName(vTagName).item(0).getTextContent();
					keyArr[i] = key;
					valArr[i] = value;
				}
				for (int i = 0; i < keyArr.length; i++) {
					metaMap.put(keyArr[i], valArr[i]);
				}
			} catch (SAXException e) {
				throw new LoadDataException(e);
			} catch (IOException e) {
				throw new LoadDataException(e);
			}
		} catch (ParserConfigurationException e) {
			throw new LoadDataException(e);
		}
		
		return metaMap;
	}
	
	public Map<String, String> openConfig(String filePath) {
		Map<String, String> configMap = new HashMap<>();
		
		ResourceBundle configkeys = ResourceBundle.getBundle(filePath);
		System.out.println(configkeys);
		for (String s : configkeys.keySet()) {
			//String buff = configkeys.getString(AuthRes.getStringKeys(s));
			configMap.put(s, configkeys.getString(s));
		}
		
		return configMap;
	}
```

- Features:
> > One feature I think is well implemented and designed is the component system of the game engine. While designing and fleshing-out the ECS design with the Engine Team (I wanted to understand what I was serializing and to help as much as I could), I learned that the ECS design pattern is extremely flexible because it allows the author to dynamically add and customize objects at will. Essentially, since every "object" is made up of components and is almost exclusivevly only a collection of components, adding an attribute to an entity, say, adding an X-Physics aspect to that entity (things like horizontal acceleration and velocity) is extremely easy. The true power of this design, however, is the extremely flexibility of the components. For instance, if we wanted to enable the author to create a new game mechanic such as Z-Physics, adding that feature would be as simple as creating a new component that can be added to any entity. Some difficulty comes when that component should logically have interactions with the systems (collisions come to mind; how would a 3D collision be computed?), but for the majority of interactions and characteristics, simply creating a new component demonstrates flexibility. Even beyond this advantage, the author can create any custom entity at their will; entities that defy science containing only a Y-Physics and no X-Physics, entities that have special sound effects, etc can all be created at will. This power and flexibility makes learning about ECS extremely interesting and fun. 

> > Implementing ECS is a challenge because there are so many moving parts. The best way to visualize it is sort of an inverse inheritance hierarchy. In our design, we needed to create a Game Engine Object that would contain a list of levels. Each of those levels contains a list of entities, and each of these entities in turn containing a series of components (the order does not matter). It adheres to Closed Programming quite well in that components, once created, will not need to be edited, at least at a class level. Some edits may be made in terms of data storage, but that does not violate the Open/Closed principle. More importantly, the structure and design of the ECS ensures that the changing of project specification or the addtion of new features does not impact what has already been created; only new components need to be added. The critical information and behavior of each entity and therefore level and therefore game as a whole is contained within each component, with each component responsible for a small part of the larger whole. A very similar case can be made for the "Event" handling; endless combinations of trigger conditions and event actions is extremely flexible, and should new features or new mechanics want to be added, it is possible to add them through events (ie, new controls can be added through the event management) or to create new events entirely. 

> > A second feature that I think was relatively poorly implemented was the display of the Authoring environment, and more importantly the definition of coordinate axes. For example, the "xpos" and "ypos" of an object were defined to be the center of the object. While this decision does lead to some benefits, especially regarding collision calculations, a major downside is the editing of entities. Because the authoring environment only displays (0+, 0+), if you were to, say
1. Add entity to screen
2. Resize entity to be very long (like a platform)
3. load the game
> > you would find that the screen centers around the main character (regardless of position along the platform). However, in the Authoring Environment, the display is scrollable and forces 0+ on both axes; this mean that half of the platform isn't rendered or interactable from an authoring perspective (the stretching occurs equally in both directions). Should your main character find itself outside the (0+, 0+) defined boundaries, it becomes impossible to edit the main character or really any entity at that point. This normally would not be a huge problem, but for some game types, specifically linear "runner" games, it becomes difficult to design a game. This becomes an interesting dilemna because a choice needs to be made between this user experience and the easy collision computation. To fix this, however, I think a simple change is to have a buffer between the computation and the rendering of the object; more specifically, editing the object so that any scaling or shifting only occurs in the positive direction, rather than about the center. In addition, changing the positioning of objects such that no negative parts of the object may exist would also be a simple, easy solution. A third, potentially even better solution is to allow negative axes to display. 

### Alternate Designs
Over the course of the project, our API changed significantly in terms of actual method signature, but as a whole it remained mostly consistent, with new methods created to accomodate new information. For example, from the original design report, 

```java 
public SaveData implements DataManipulation{
    public void parseData(DataObject do);
    //parseData will call Serialize
    public void saveToFile();
}
```
The core of Game Data remained quite consistent, containing a way to create the file and a way to write to the file. The current and final implementation is a bit more complex to accomodate more information that needs to be saved, and more importantly to encompass greater functionality. SaveData was originally designed to save some sort of nebulous "game object", without much thought given to what that object actually is. However, as time went on, our design evolved from saving a list of game levels to a game engine to a game engine and metamap, to a game engine, metamap, and configuration map. The large quantity of data required by player and authoring caused expansion on this method to encompass all that was needed. In addition, changes were made to the saveData method in order to accomodate a file system heirarchy.
```java 
	public void saveData(Engine engine, String gameFolderName, String saveFileName, Map<String, String> metaMap, Map<String, String> ConfigMap) throws SaveDataException {
		saveData(engine, gameFolderName, saveFileName, false);

		saveConfig(gameFolderName, ConfigMap, saveFileName);
		
		saveMeta(gameFolderName, metaMap);
	}
```
```java 
	public void saveData(Engine engine, String gameName, String saveFileName, boolean isPlayer) throws SaveDataException {
		File file = new File("games/"+gameName);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		file = new File("games/" + gameName + "/" + saveFileName +".xml");
		if (isPlayer) {
			file = new File("games/"+gameName+"/gameSaves");
			if(!file.exists()) {
				file.mkdirs();
			}
		file = new File("games/"+gameName+"/gameSaves/" + saveFileName + ".xml");
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new SaveDataException(e);
			}
		}
		if (file.exists()) {
			try {
				FileOutputStream writer = new FileOutputStream(file);
				writer.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
				writer.write(("<higher>").getBytes("UTF-8"));
				saveEngine(engine, writer);
				writer.write(("</higher>").getBytes("UTF-8"));

			} catch (IOException e) {
				throw new SaveDataException(e);
			}
		}
	}
```
One of the bigger changes we made (aside from the method signature change) was the creation of two methods, one for player and one for authoring. During the initial design phase, not a lot of thought was given to multiple save files or game file hierarchy. When adding these features, it became necessary to create these separate and unique methods for different parts of the project to access; this way, Authoring will never overwrite what Player has written, and Player will never edit the orignial, base, "clean" version of the game. This protection, while not inherently necessary, prevents a lot of unnecessary hassle and makes for a more powerful and neater design. In addition, flexibility is given to the naming and file systems of the author/gamer. Should the folder be renamed or moved, everything would still load perfectly fine, assuming "gameSaves" and all the other requisite files still existed in the file system hierarchy without too much deviation. Initially, the only other proposed design decision was against merging the multiple needed save files into one method, opting to effectively mirror the decision taken with loading data. The trade off here would be to make the code more readable (saveData would not become so large and reliant on helper classes), but in exchange other parts of the project would have to make three successive method calls to save all the required data. Initially, I argued that it is more efficient to use three different methods, as this way not only is the code more readable but also and more importantly more efficient; the authoring environment would only have to detect what changed (for example, only an edit to the game title was made, ie metadata), and call the related method. However, after greater thought, I decided that this was a moot point; it's inordinately unnecessary and difficult to determine what the author changed and what needs to be resaved and much easier to resave everything, at little to no cost overall. As a whole, I prefer the conglomerate version of saveData, as it simplifies things for Authoring and for Game Player, though it does come with some costs. 

A similar approach was taken towards loading the game data. Initially, with little planning, we had anticipated only have to effectively do the reverse of saving: taking a file and creating a "Game Object". Because we were not really aware of the extent to which we needed to save and create files, a relatively simple approach was taken, though the core of it still exists as is today, even if the initial input parameter was rather misguided. 
```java 
public LoadData implements DataManipulation{
    public void parseData(DataObject do);
    //parseData will call DeserializeData
    public void instantiate();
    //instantiate will call the Instantiator
}
```
As is clear here, the initial design took in a Data Object for unknown reasons, and did not return anything. As we actually began coding the project, changes were immediately made, with an effectively finalized version of data loading being completed around the time of mid-point demo.
```java 
	public Engine loadData(String filePath) throws LoadDataException {
		try {
			File load = new File(filePath);
			return openFile(load);
		} catch (ParserConfigurationException e) {
			throw new LoadDataException(e);
		}
		}
```
```java 

	private Engine openFile(File file) throws ParserConfigurationException, LoadDataException{
		Engine lilGuy = new Engine();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		String filePath = file.getAbsolutePath();
		String fileType = filePath.substring(filePath.length()-FILE_EXTENSION);

		if (!fileType.equals(".xml")) {
			System.out.println("You dun goofed");
		};
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc;

			try {
				doc = dBuilder.parse(file);

				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("higher");
				System.out.println();
				Node nNode = nList.item(0);
				Element eElement = (Element) nNode;			
				String s = nodeToString(eElement.getElementsByTagName("data").item(0).getFirstChild());
				lilGuy = (Engine) deserializer.fromXML(s);
				return lilGuy;
				
			} catch (SAXException e) {
				throw new LoadDataException(e);
			}
		} catch (IOException e) {
			throw new LoadDataException(e);
		}
	}
```
Towards the second demo, it became necessary to preserve non-game data, strings such as copyright and tutorials. To that end, preservation, discussed earlier, was designed, and the corresponding loading methods were created. Because saving does not have a return value, one save method is sufficient to save all the necessary data in the game. However, because loading is extensive and has multiple return values, it is necessary to write multiple publically available methods to return all aspects of the game data, from the engine to the metadata to the configuration data. 
```java 
public Map<String, String> openMeta(String filePath) throws LoadDataException {
		
		File file = new File(filePath);
		
		Map<String, String> metaMap = new HashMap<>();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			try {
				doc = dBuilder.parse(file);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("stuff");
				NodeList actualList = nList.item(0).getChildNodes();
				Node nNode = nList.item(0);
				Element eElement = (Element) nNode;
				String[] keyArr = new String[actualList.getLength() / 2];
				String[] valArr = new String[actualList.getLength() / 2];
				for (int i = 0; i < actualList.getLength() / 2; i++){
					String kTagName = "key" + i;
					String vTagName = "value" + i;
					String key = eElement.getElementsByTagName(kTagName).item(0).getTextContent();
					String value = eElement.getElementsByTagName(vTagName).item(0).getTextContent();
					keyArr[i] = key;
					valArr[i] = value;
				}
				for (int i = 0; i < keyArr.length; i++) {
					metaMap.put(keyArr[i], valArr[i]);
				}
			} catch (SAXException e) {
				throw new LoadDataException(e);
			} catch (IOException e) {
				throw new LoadDataException(e);
			}
		} catch (ParserConfigurationException e) {
			throw new LoadDataException(e);
		}
		
		return metaMap;
	}
```
```java
	public Map<String, String> openConfig(String filePath) {
		Map<String, String> configMap = new HashMap<>();
		
		ResourceBundle configkeys = ResourceBundle.getBundle(filePath);
		System.out.println(configkeys);
		for (String s : configkeys.keySet()) {
			//String buff = configkeys.getString(AuthRes.getStringKeys(s));
			configMap.put(s, configkeys.getString(s));
		}
		
		return configMap;
	}
```
To write these methods, use of resource files became necessary to standardize some aspects of the methods; however, a built in flexibility here is that changing the standardization is simply a resource file level change; Game Data is robust enough to handle a wide range of configuration data. An alternative design was to lump the metadata and the configuration data together into a single file and map. However, after much discussion and argumentation, I decided it was easier and cleaner to use two files. The reasoning is that metadata and configuration data are fundamentally different things; metadata is mutable and completely at the whim of the author. The author can write his thesis in the metadata if he wanted to. As such, it varies widely from game to game, with very few similarities. In constrast, as defined by the resource file, the configuration file is not mutable; it is consistent across all games. By consistent, I mean that every game must have a Game Name or Title, Author(s), and a thumbnail filepath to display. Because these aspects, and any added aspects later on (mandatory liscensing, version number, etc), the configuration map reserves special treatment and merging the two maps causes significant conflicts, especially regarding key-value pairing (name vs Name vs NAME, etc). Ultimately, I made the decision to write to two separate files to preserve non-game data. 
### Conclusions
The best feature of the project's current design is by far the ECS design pattern. This design pattern, as I have described before, is extremely flexible because it allows the author to dynamically add and customize objects at will. Essentially, since every “object” is made up of components and is almost exclusivevly only a collection of components, adding an attribute to an entity, say, adding an X-Physics aspect to that entity (things like horizontal acceleration and velocity) is extremely easy. The true power of this design, however, is the extremely flexibility of the components. For instance, if we wanted to enable the author to create a new game mechanic such as Z-Physics, adding that feature would be as simple as creating a new component that can be added to any entity. Some difficulty comes when that component should logically have interactions with the systems (collisions come to mind; how would a 3D collision be computed?), but for the majority of interactions and characteristics, simply creating a new component demonstrates flexibility. Even beyond this advantage, the author can create any custom entity at their will; entities that defy science containing only a Y-Physics and no X-Physics, entities that have special sound effects, etc can all be created at will. 

A lackluster aspect of our design is the limitation on conditionals. While we tried our best to accomodate all possible event triggers and actions, at a certain point it is necessary to limit their prescence. For example, a unique use-case trigger might be to when the user makes his 10000th click; that is a perfectly valid trigger to cause an achievement to unlock or something like that. However, we decided against implementing something like that simply because its too niche. A more detrimental aspect of our design, however, is the difficulty in keeping track of a single character. For example, when switching between levels, there is no way for the authoring environment or the game engine or the player to know that the "main" character on level two is the exact same as the one on level one. Essentially, after completing level one, the player is progressed to level two, and all the "progress", i.e points, coins, etc are lost in the transition. The necessary fix is to save the "current" state as a buffer in between levels, and to use that buffer to carry over. However, a problem immediately arises if the Mario on level two does not have identical components as the Mario from level one. 

My biggest strength as a coder/designer is my determination to understand and plan out what I'm trying to accomplish before I begin, laying out the "data pipeline" of what's going where and why. I think that that habit and preference helps me code much better and more effectively. This simultaneously is my favorite part of coding; the design and the planning of the code. Diving into coding headfirst without much of plan is kind of like jumping off a diving board with a blindfold. I love the planning aspect and the evolution of a project's specification as the team continues to work and learn together. 

This semester, I've learned more from CS308 than from any class I've ever taken at Duke. I think my two biggest takeaways from this class are
1. Never code without thinking about how you might want to change it later. I've tried to work towards this goal as much as possible, and, looking back, I've definitely made significant improvement in the flexibility and quality of the code that I've written
2. Always ask for help. This semester, I've found that working in teams is fantastic because you are never stuck on a frustrating bug for too long. I've become more willing to ask for help and to accept and seek critique on my projects rather than create "patches" to workaround the problem. 