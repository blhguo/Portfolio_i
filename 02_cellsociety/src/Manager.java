package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import cell.Cell;
import cell.sugarsim.AgentCell;
import grid.FireSimGrid;
import grid.GOLSimGrid;
import grid.Grid;
import grid.SegregationSimGrid;
import grid.SugarSimGrid;
import grid.WatorSimGrid;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene; 
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import xml.SimSetups.FireSimSetup;
import xml.SimSetups.GOLSimSetup;
import xml.SimSetups.SegregationSimSetup;
import xml.SimSetups.SugarSimSetup;
import xml.SimSetups.WatorSimSetup;
import xml.makers.FireXMLmaker;
import xml.makers.GOLXMLmaker;
import xml.makers.SegXMLmaker;
import xml.makers.SugarXMLmaker;
import xml.makers.WatorXMLmaker;
import xml.readers.FireXMLreader;
import xml.readers.GOLXMLreader;
import xml.readers.SegregationXMLreader;
import xml.readers.SugarXMLreader;
import xml.readers.WatorXMLreader;

/**
 * Simulation visualization and management
 * @author Brandon Guo, Marcus Oertle, Yashas Manjunatha
 * 
 * input:
 * @param No input parameters, all inputs are given by user
 * @return No return object, presents simulation
 * 
 * Use: 
 * Graphically presents simulation
 * Allows for User selected files
 * Double speed simulation, half speed simulation
 * Call next cell layout
 * 
 */

public class Manager extends Application {

	int width;
	int height;
	int initialW;
	int initialH;
	int initialCW;
	int initialCH;
	Grid myGrid;
	int cell_Width;
	int cell_Height;
	Integer stepcount = 0;
	String shapetype;
	int graphbufferH = 400;
	int graphbufferW = 500;	
	double animspeed = 1.0;
	//probably should have made this a map
	ArrayList<XYChart.Series<Number, Number>> datapoints = new ArrayList<XYChart.Series<Number, Number>>();
	public static final Paint BACKGROUND = Color.WHITE;
	private Stage TheStage;
	private static final String TITLE = "CA SIMULATION";
	private static final String DEFAULT_FILENAME = "data/game_of_life.xml";
	private String fileName = DEFAULT_FILENAME;
	private int fileType = 0;
	public double SECOND_DELAY = 2000.0;
	private KeyFrame frame;
	private Timeline animation;	
	private static final int MENU_PAD = 10;
	private static final int GUIDE_SIZE = 310;
	private static final int MAKER_SIZE = 400;
	private static final int SLIDERLENGTH = 100;
	private boolean inMenu = true;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final String DEFAULT_RESOURCE_FILE = "defaultText";
	private ResourceBundle myResources;
	private static final String FileS = "FileS";
	private static final String FileW = "FileW";
	private static final String FileF = "FileF";
	private static final String FileGOL = "FileGOL";
	private static final String FileSug = "FileSug";
	private static final int SLIDERHEIGHT = 120;
	private static final int RIGHTPAD = 50;
	private static final double ERRFADE = 1;
	private TextField probcell = null;
	private TextField probx = null;
	private TextField probo = null;
	private TextField thresh = null;
	private TextField probfish = null;
	private TextField probshark = null;
	private TextField rtshark = null;
	private TextField rtfish = null;
	private TextField eShark = null;
	private TextField geShark = null;
	private TextField probfire = null;
	private TextField problight = null;
	private TextField probnewtree = null;
	private TextField agentProb = null;
	private String neighbourType = "all";
	private String edgeType = "finite";
	private String shape = "square";
	
	

	@Override
	public void start(Stage stage) throws Exception {

		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_RESOURCE_FILE);

		TheStage = stage;
		Scene menuScene = setupMenu(width, height, BACKGROUND, TheStage);
		menuScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		TheStage.setScene(menuScene);
		TheStage.setTitle(TITLE);
		TheStage.show();

		frame = new KeyFrame(Duration.millis(SECOND_DELAY), e1 -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	/**
	 * @throws Exception
	 * @Param None
	 * Sets scene to be the menu scene, and sets stage to that scene
	 */
	private void returnMenu() throws Exception {
		inMenu = true;
		fileType = 0;
		animspeed = 1;
		animation.setRate(1);
		fileName = DEFAULT_FILENAME;
		Scene menuScene = setupMenu(width, height, BACKGROUND, TheStage);
		menuScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		TheStage.setScene(menuScene);
		TheStage.setTitle(TITLE);
		TheStage.show();
	}

	
	
	/**
	 * @param code - an input code from the keyboard
	 * Handles key input, takes in a keycode and matches it to one of the following, then acts on the simulation. No return value
	 */
	private void handleKeyInput(KeyCode code) {
		if (code == KeyCode.S) {
			step();
		}
		if (code == KeyCode.D) {
			animspeed = animspeed * 2;
			animation.setRate(animspeed);
		}
		else if (code == KeyCode.A) {
			animspeed = animspeed * 0.5;
			animation.setRate(animspeed);
		}
		else if (code == KeyCode.P) {
			if (animation.getStatus() == Status.PAUSED) {
				animation.play();
				animation.setRate(1);
			}
			else if (animation.getStatus() == Status.RUNNING) {
				animation.pause();
			}
		}
		else if (code == KeyCode.M) {
			// return menu
			try {
				datapoints.clear();
				stepcount = 0;
				returnMenu();
			} catch (Exception e) {
				throw new Errors(myResources.getString("NoMenu"));
			}
		}
		
		else if (code == KeyCode.Q) {
			System.exit(0);
		}
	}
	
	// Computes next "scene", takes in no parameters and returns nothing
	
	/**
	 * Computes next "scene", takes in no parameters and returns nothing
	 */
	private void step () {
		if (!inMenu) {
			Scene myScene_Buffer;
			stepcount = stepcount + 1;
			try {
				myGrid.updateGrid();
				myScene_Buffer = setupScene(width, height, BACKGROUND, myGrid);
				myScene_Buffer.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
				TheStage.setScene(myScene_Buffer);
				TheStage.show();

			} catch (Exception e1) {
				throw new Errors(e1, myResources.getString("NoUpdate"), setupScene(width, height, BACKGROUND, myGrid));
			}
		}
	}
	
	/**
	 * @param file - file that we are trying to read
	 * Calls XML parser and stores the values from the parser

	 */
	public void callXMLreader(String file){
		if(file.equals(myResources.getString(FileS))) {
			callSegXMLreader(file);
		}
		else if(file.equals(myResources.getString(FileW))) {
			callWatorXMLreader(file);
		}
		else if(file.equals(myResources.getString(FileF))) {
			callFireXMLreader(file);
		}
		else if(file.equals(myResources.getString(FileSug))){
			callSugarXMLreader(file);
		}
		else {
			if(fileType == 0) {
				callGOLXMLreader(file);
			}
			else if(fileType == 1) {
				callSegXMLreader(file);
			}
			else if(fileType == 2) {
				callWatorXMLreader(file);
			}
			else if(fileType == 3) {
				callFireXMLreader(file);
			}
			else if(fileType == 4) {
				callSugarXMLreader(file);
			}
			//TODO throw error if not one of those shapes^^
		}
	}
	

	/**
	 * @param file - file to be read
	 * Reads the file, looking for specific parameters based on the type of simulation represented in the file. Each of the following represents a speciic simulation
	 */
	public void callGOLXMLreader(String file){
		GOLXMLreader xml_reader = new GOLXMLreader();
		GOLSimSetup simInfo = xml_reader.read(file);
		initialW = simInfo.getGridX();
		initialH = simInfo.getGridY();
		initialCW = simInfo.getCellX();
		initialCH = simInfo.getCellY();
		width = simInfo.getGridX();
		height = simInfo.getGridY();
		int simWidth = simInfo.getGridX()/simInfo.getCellX();
		int simHeight = simInfo.getGridY()/simInfo.getCellY();
		myGrid = new GOLSimGrid(simWidth, simHeight, simInfo.getShape(), simInfo.getNeighbourType(), simInfo.getEdgeType(), simInfo.getArray());
		cell_Width = simInfo.getCellX();
		cell_Height = simInfo.getCellY();
	}

	public void callSegXMLreader(String file){
		SegregationXMLreader xml_reader = new SegregationXMLreader();
		SegregationSimSetup simInfo = xml_reader.read(file);
		initialW = simInfo.getGridX();
		initialH = simInfo.getGridY();
		initialCW = simInfo.getCellX();
		initialCH = simInfo.getCellY();
		width = simInfo.getGridX();
		height = simInfo.getGridY();
		int simWidth = simInfo.getGridX()/simInfo.getCellX();
		int simHeight = simInfo.getGridY()/simInfo.getCellY();
		myGrid = new SegregationSimGrid(simWidth, simHeight, simInfo.getShape(), simInfo.getNeighbourType(), simInfo.getEdgeType(), simInfo.getArray(), simInfo.getThreshold());
		cell_Width = simInfo.getCellX();
		cell_Height = simInfo.getCellY();
	}
	
	public void callSugarXMLreader(String file){
		SugarXMLreader xml_reader = new SugarXMLreader();
		SugarSimSetup simInfo = xml_reader.read(file);
		width = simInfo.getGridX();
		height = simInfo.getGridY();
		int simWidth = simInfo.getGridX()/simInfo.getCellX();
		int simHeight = simInfo.getGridY()/simInfo.getCellY();
		myGrid = new SugarSimGrid(simWidth, simHeight, simInfo.getShape(), simInfo.getNeighbourType(), 
				simInfo.getEdgeType(), simInfo.getAgentArray(), simInfo.getPatchSugarArray(), 
				simInfo.getPatchMaxSugarArray(), simInfo.getGrowBackRate(), simInfo.getGrowBackInterval(), 
				simInfo.getPatchTickArray(), simInfo.getAgentSugarArray(), simInfo.getAgentMetabolismArray(), 
				simInfo.getAgentVisionArray());
		cell_Width = simInfo.getCellX();
		cell_Height = simInfo.getCellY();
	}

	public void callWatorXMLreader(String file){
		WatorXMLreader xml_reader = new WatorXMLreader();
		WatorSimSetup simInfo = xml_reader.read(file);
		initialW = simInfo.getGridX();
		initialH = simInfo.getGridY();
		initialCW = simInfo.getCellX();
		initialCH = simInfo.getCellY();
		width = simInfo.getGridX();
		height = simInfo.getGridY();
		int simWidth = simInfo.getGridX()/simInfo.getCellX();
		int simHeight = simInfo.getGridY()/simInfo.getCellY();
		myGrid = new WatorSimGrid(simWidth, simHeight, simInfo.getShape(), simInfo.getNeighbourType(), simInfo.getEdgeType(), simInfo.getArray(), simInfo.getReproduction(), simInfo.getEnergy(), simInfo.getGainedEnergy());
		cell_Width = simInfo.getCellX();
		cell_Height = simInfo.getCellY();
	}

	public void callFireXMLreader(String file){
		FireXMLreader xml_reader = new FireXMLreader();
		FireSimSetup simInfo = xml_reader.read(file);
		initialW = simInfo.getGridX();
		initialH = simInfo.getGridY();
		initialCW = simInfo.getCellX();
		initialCH = simInfo.getCellY();
		width = simInfo.getGridX();
		height = simInfo.getGridY();
		int simWidth = simInfo.getGridX()/simInfo.getCellX();
		int simHeight = simInfo.getGridY()/simInfo.getCellY();
		myGrid = new FireSimGrid(simWidth, simHeight, simInfo.getShape(), simInfo.getNeighbourType(), simInfo.getEdgeType(), simInfo.getArray(), simInfo.getFireProb(), simInfo.getLightningProb(), simInfo.getProbGrow());
		cell_Width = simInfo.getCellX();
		cell_Height = simInfo.getCellY();
	}
	
	/**
	 * @param width - width of the scene
	 * @param height - height of the scene
	 * @param background - color of background
	 * @return Scene representing the guide
	 * Builds the guide for the scene
	 */
	private Scene setupGuide(int width, int height, Paint background) {
		// set all text for guide
		Text g1 = new Text();
		g1.setText(myResources.getString("Guide1"));
		Text g2 = new Text();
		g2.setText(myResources.getString("Guide2"));
		Text line = new Text();
		line.setText(myResources.getString("Line"));
		Text line2 = new Text();
		line2.setText(myResources.getString("Line"));
		Text g3 = new Text();
		g3.setText(myResources.getString("Guide3"));
		Text g4 = new Text();
		g4.setText(myResources.getString("Guide4"));
		Text g5 = new Text();
		g5.setText(myResources.getString("Guide5"));
		Text g6 = new Text();
		g6.setText(myResources.getString("Guide6"));
		Text g7 = new Text();
		g7.setText(myResources.getString("Guide7"));
		Text g8 = new Text();
		g8.setText(myResources.getString("Guide8"));
		Text g15 = new Text();
		g15.setText(myResources.getString("Guide9"));
		Text g155 = new Text();
		g155.setText(myResources.getString("Guide10"));
		Text g16 = new Text();
		g16.setText(myResources.getString("Guide11"));
		Text g17 = new Text();
		g17.setText(myResources.getString("Guide12"));
		Text g18 = new Text();
		g18.setText(myResources.getString("Guide13"));

		// initialize GridPane and settings
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(width, height);
		gridPane.setPadding(new Insets(10,10,10,10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setStyle("-fx-background-color: THISTLE;");
		//gridPane.setAlignment(Pos.CENTER);

		// add text to GridPane
		gridPane.add(g1, 0, 0);
		gridPane.add(g2, 0, 1);
		gridPane.add(line, 0, 2);
		gridPane.add(g3, 0, 3);
		gridPane.add(g4, 0, 4);
		gridPane.add(g5, 0, 5);
		gridPane.add(g6, 0, 6);
		gridPane.add(g7, 0, 7);
		gridPane.add(g8, 0, 8);
		gridPane.add(line2, 0, 9);
		gridPane.add(g15, 0, 10);
		gridPane.add(g155, 0, 11);
		gridPane.add(g16, 0, 12);
		gridPane.add(g17, 0, 13);
		gridPane.add(g18, 0, 14);	

		// create a place to see the shapes
		Scene scene = new Scene(gridPane, width, height, background);

		// set what happens on key press
		scene.setOnKeyPressed(e -> {
			// return to menu scene
			try {
				returnMenu();
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
		});
		return scene;
	}

	
	/**
	 * @param width - width of grid (gets updated to represent width of window)
	 * @param height - height of grid (gets updated to represent height of window)
	 * @param background - background color
	 * @param cellArray - Grid object of all cells, passed to Manager by Grid
	 * @return Scene - represents the scene, including the chart, sliders, and simulation itself
	 * @throws Exception
	 * Sets up scene for the actual simulation
	 */
	public Scene setupScene (int width, int height, Paint background, Grid cellArray) throws Exception {
		Map<String, Double[]> MapofParam = cellArray.getCurrentParameters();
		VBox box = new VBox(GenerateLineChart(cellArray.getNumberOfCells()));
		int SliderCount = 0;
		
		for (String s : MapofParam.keySet()) {
		    Text text = new Text();
		    text.setFont(new Font("sans-serif", 10));
			Slider slider = generateSlider(MapofParam.get(s)[0], MapofParam.get(s)[1], MapofParam.get(s)[2]);
			slider.setOrientation(Orientation.HORIZONTAL);
			slider.setPrefHeight(SLIDERLENGTH);
			slider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
				double d = newvalue.doubleValue();
				Double[] doublearray = {MapofParam.get(s)[0], MapofParam.get(s)[1], d};
				MapofParam.put(s, doublearray);
				cellArray.setCurrentParameters(MapofParam);
				slider.setValue(d);
				text.setText(s + d);
			});
			text.setText(s + " = " + MapofParam.get(s)[2]);
			box.getChildren().add(slider);
			box.getChildren().add(text);
			SliderCount = SliderCount + 1;
		}
		cellArray.setCurrentParameters(MapofParam);
		
		shapetype = cellArray.getShape();
		int cell_width = (int) (width) / cellArray.getCellArray().length;
		int cell_height = (int) (height) / cellArray.getCellArray()[0].length;
		
		int gridW = width;
		int gridH = height;
		
		if (width < gridW + graphbufferW) {
			width = graphbufferW + gridW;
		}
		if (gridH < ((graphbufferH) + SLIDERHEIGHT * SliderCount)) {
			height = ((graphbufferH) + SLIDERHEIGHT * SliderCount);
		}
		Group root = new Group (CreateRoot(cellArray, cell_width, cell_height));
		
		root.getChildren().add(box);
		root.getChildren().add(GenerateSaveButton(cellArray, initialW, initialH, initialCW, initialCH));
		Scene scene = new Scene(root, width + RIGHTPAD, height, background);
		return scene;	
	}

	/**
	 * @param width - width of window
	 * @param height - height of window
	 * @param background - background color
	 * @param stage - stage upon which buttons are added
	 * @return - returns a scene with navigation buttons and options
	 * @throws Exception
	 * Sets up menu screen
	 */
	public Scene setupMenu (int width, int height, Paint background, Stage stage) throws Exception {
		VBox splash = new VBox ();
		splash.setPadding(new Insets(MENU_PAD, MENU_PAD, MENU_PAD, MENU_PAD));
		splash.setSpacing(MENU_PAD);
		Label lbl = new Label(myResources.getString("Title"));
		lbl.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
		splash.getChildren().add(lbl);

		Button openButton = GenerateFileButton(stage);
		Button startButton = GenerateStartButton(stage);
		ChoiceBox<String> fileChoiceBox = GenerateSimChoiceBox();
		Button guideButton = GenerateGuideButton(stage);
		Button makerButton = GenerateMakerButton(stage);

		splash.getChildren().add(startButton);
		splash.getChildren().add(fileChoiceBox);
		splash.getChildren().add(guideButton);
		splash.getChildren().add(openButton);
		splash.getChildren().add(makerButton);

		Scene scene = new Scene(splash);
		return scene;
	}
	
	/**
	 * @param s - stage to which button calls changes on
	 * @return Button - Button with the following effects
	 * Button selects file
	 */
	public Button GenerateFileButton(Stage s) {
		Button openButton = new Button(myResources.getString("OpenFile"));
		FileChooser fileChooser = new FileChooser();
		openButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						File file = fileChooser.showOpenDialog(s);
						if (file != null) {
							fileName = file.getName();
							fileName = "data/" + fileName;		
							try {
								animation.play();
								callXMLreader(fileName);
								s.setScene(setupScene(width, height, BACKGROUND, myGrid));
								inMenu = false;
							} catch (Exception e1) {
							}
						}
					}
				});
		return openButton;
	}
	/**
	 * @param s - stage to which button calls changes on
	 * @return Button - Button with the following effects
	 * Button starts sim
	 */	public Button GenerateStartButton(Stage s) {
		Button startButton = new Button(myResources.getString("StartCommand"));

		startButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {	
						try {
							animation.play();
							callXMLreader(fileName);
							s.setScene(setupScene(width, height, BACKGROUND, myGrid));
							inMenu = false;
						} catch (Exception e1) {
						}
					}

				});
		return startButton;
	}
		/**
		 * @param s - stage to which button calls changes on
		 * @return Button - Button with the following effects
		 * Button creates choice menu
		 */
	public ChoiceBox GenerateSimChoiceBox() {
		ChoiceBox<String> fileChoiceBox = new ChoiceBox<String>();
		fileChoiceBox.getItems().add(myResources.getString("DropDown1"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown2"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown3"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown4"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown15"));
		fileChoiceBox.getSelectionModel().selectFirst();
		fileChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {	
				if((int) new_value == 0){
					fileName = myResources.getString(FileGOL);
					fileType = 0;
				}
				if((int) new_value == 1){
					fileName = myResources.getString(FileS);
					fileType = 1;
				}
				if((int) new_value == 2){
					fileName = myResources.getString(FileW);
					fileType = 2;
				}
				if((int) new_value == 3){
					fileName = myResources.getString(FileF);
					fileType = 3;
				}
				if((int) new_value == 4){
					fileName = myResources.getString(FileSug);
					fileType = 4;
				}
			}
		});
		return fileChoiceBox;
	}
	/**
	 * @param s - stage to which button calls changes on
	 * @return Button - Button with the following effects
	 * Button generates choice box
	 */
	// Drop down menu to select type of simulation
	public ChoiceBox GenerateNeighbourChoiceBox() {
		ChoiceBox<String> fileChoiceBox = new ChoiceBox<String>();
		fileChoiceBox.getItems().add(myResources.getString("DropDown5"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown6"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown7"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown8"));
		fileChoiceBox.getSelectionModel().selectFirst();
		fileChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {	
				if((int) new_value == 0){
					neighbourType = "all";
				}
				if((int) new_value == 1){
					neighbourType = "cardinal";
				}
				if((int) new_value == 2){
					neighbourType = "diagonal";
				}
				if((int) new_value == 3){
					neighbourType = "3-next";
				}
			}
		});
		return fileChoiceBox;
	}
	/**
	 * @param s - stage to which button calls changes on
	 * @return Button - Button with the following effects
	 * Button selects file
	 * Drop down menu to select type of simulation
	*/
	public ChoiceBox GenerateEdgeChoiceBox() {
		ChoiceBox<String> fileChoiceBox = new ChoiceBox<String>();
		fileChoiceBox.getItems().add(myResources.getString("DropDown9"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown10"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown11"));
		fileChoiceBox.getSelectionModel().selectFirst();
		fileChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {	
				if((int) new_value == 0){
					edgeType = "finite";
				}
				if((int) new_value == 1){
					edgeType = "toroidal";
				}
				if((int) new_value == 2){
					edgeType = "infinite";
				}
			}
		});
		return fileChoiceBox;
	}

	
	/**
	 * @param s - stage to which button calls changes on
	 * @return Button - Button with the following effects
	 * Button selects file
	 * Drop down menu to select type of simulation
	 */	
	public ChoiceBox GenerateShapeChoiceBox() {
		ChoiceBox<String> fileChoiceBox = new ChoiceBox<String>();
		fileChoiceBox.getItems().add(myResources.getString("DropDown12"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown13"));
		fileChoiceBox.getItems().add(myResources.getString("DropDown14"));
		fileChoiceBox.getSelectionModel().selectFirst();
		fileChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {	
				if((int) new_value == 0){
					shape = "square";
				}
				if((int) new_value == 1){
					shape = "triangle";
				}
				if((int) new_value == 2){
					shape = "hexagon";
				}
			}
		});
		return fileChoiceBox;
	}
	/**
	 * @param s - stage to which button calls changes on
	 * @return Button - Button with the following effects
	 * Button Generates button to allow saving of current state
	*/
	public Button GenerateSaveButton(Grid cellArray, int gridx, int gridy, int cellx, int celly) {
		Button SaveButton = new Button(myResources.getString("SaveFile"));
		
		SaveButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						try {
							if (cellArray instanceof FireSimGrid) {
								new FireXMLmaker( (FireSimGrid) cellArray, gridx, gridy, cellx, celly);
							}
							else if (cellArray instanceof GOLSimGrid) {
								new GOLXMLmaker( (GOLSimGrid) cellArray, gridx, gridy, cellx, celly);
							}
							else if (cellArray instanceof SegregationSimGrid) {
								new SegXMLmaker( (SegregationSimGrid) cellArray, gridx, gridy, cellx, celly);
							}
							else if (cellArray instanceof WatorSimGrid) {
								new WatorXMLmaker( (WatorSimGrid) cellArray, gridx, gridy, cellx, celly);
							}
						}
						catch (Exception e1){
						}
					}
					
				}
				);
		return SaveButton;
	}
	/**
	 * @param s - stage to which button calls changes on
	 * @return Button - Button with the following effects
	 * Button  Generates button to start the maker scene
	 * */
	public Button GenerateMakerButton(Stage s) {
		Button makerButton = new Button(myResources.getString("MakerButton"));

		makerButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {	
						try {
							s.setScene(setupXMLmaker(MAKER_SIZE, MAKER_SIZE, BACKGROUND));
							inMenu = true;
						} catch (Exception e1) {
							
						}
					}

				});
		return makerButton;
	}
	/**
	 * @param s - stage to which button calls changes on
	 * @return Button - Button with the following effects
	 * Button Generates button that opens the user manual
	 */
	public Button GenerateGuideButton(Stage s) {
		Button guideButton = new Button(myResources.getString("Guide"));

		guideButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {	
						try {
							s.setScene(setupGuide(GUIDE_SIZE, GUIDE_SIZE + 40, BACKGROUND));
							inMenu = true;
						} catch (Exception e1) {
						}
					}

				});
		return guideButton;
	}

	public Button GenButton(GridPane grid, String name, int x, int y) {
		Button temp = new Button(name);
		GridPane.setConstraints(temp, x, y);
		grid.getChildren().add(temp);
		return temp;
	}

	/**
	 * Creates the XMLmaker scene
	 * @param width - width of XMLMaker
	 * @param height - ''
	 * @param background - ''
	 * @return Scene - represents the scene which allows the user to build a custom file
	 */
	private Scene setupXMLmaker(int width, int height, Paint background) {
		// make gridpane
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);

		// add text entry fields
		final TextField file = makeTextField(grid, myResources.getString("NameField"), 0, 1);
		final TextField gridx = makeTextField(grid, myResources.getString("GridXField"), 0, 2);
		final TextField gridy = makeTextField(grid, myResources.getString("GridYField"), 0, 3);
		final TextField cellx = makeTextField(grid, myResources.getString("CellXField"), 0, 4);
		final TextField celly = makeTextField(grid, myResources.getString("CellYField"), 0, 5);
		
		// add choiceboxes
		final ChoiceBox neighbourChoiceBox = GenerateNeighbourChoiceBox();
		final ChoiceBox edgeChoiceBox = GenerateEdgeChoiceBox();
		final ChoiceBox shapeChoiceBox = GenerateShapeChoiceBox();
		grid.add(neighbourChoiceBox, 0, 7);
		grid.add(edgeChoiceBox, 0, 8);
		grid.add(shapeChoiceBox, 0, 6);
		
		// add simulation specific text fields
		if(fileType == 0) {
			probcell = makeTextField(grid, myResources.getString("CellProbField"), 1, 1);
		}
		else if(fileType == 1) {
			probx = makeTextField(grid, myResources.getString("XProbField"), 1, 1);
			probo = makeTextField(grid,myResources.getString("OProbField"), 1, 2);
			thresh = makeTextField(grid, myResources.getString("ThreshField"), 1, 3);
		}
		else if(fileType == 2) {
			probfish = makeTextField(grid, myResources.getString("ProbFishField"), 1, 1);
			probshark = makeTextField(grid, myResources.getString("ProbSharkField"), 1, 2);
			rtshark = makeTextField(grid, myResources.getString("SharkThreshField"), 1, 3);
			rtfish = makeTextField(grid, myResources.getString("FishThreshField"), 1, 4);
			eShark = makeTextField(grid, myResources.getString("SharkEnergyField"), 1, 5);
			geShark = makeTextField(grid, myResources.getString("SharkEnergyPerFishField"), 1, 6);
		}
		else if(fileType == 3) {
			probfire = makeTextField(grid, myResources.getString("FireProbField"), 1, 1);
			problight = makeTextField(grid, myResources.getString("LightProbField"), 1, 2);
			probnewtree = makeTextField(grid, myResources.getString("NewTreeProbField"), 1, 3);
		}
		else if(fileType == 4){
			agentProb = makeTextField(grid, myResources.getString("AgentProbField"), 1, 1);
		}
		
		// add menu button
		Button menu = GenButton(grid, myResources.getString("MenuButton"), 0, 0);
		menu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					returnMenu();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		// add create button
		Button create = GenButton(grid, myResources.getString("CreateButton"), 1, 0);
		create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				boolean isError = false;
				int gridxval = 400;
				int gridyval = 400;
				int cellxval = 40;
				int cellyval = 40;
				double probcellval = 0.3;
				double probxval = 0.3;
				double proboval = 0.3;
				double threshval = 0.3;
				double probfishval = 0.3;
				double probsharkval = 0.3;
				int rtsharkval = 5;
				int rtfishval = 5;
				int esharkval = 5;
				int gesharkval = 3;
				double probfireval = 0.5;
				double problightval = 0.01;
				double probnewtreeval = 0.01;
				double agentprobval = 0.05;
				String filename = file.getText();
				
				// filename error handling
				if(filename.contains(".")) {
					isError = true;
					displayMessage(grid, myResources.getString("MakerError6"), 3, 0,11);
				}
				else if(filename.equals("game_of_life") || filename.equals("segregation") 
						|| filename.equals("wator") || filename.equals("fire") || filename.equals("sugar")) {
					isError = true;
					displayMessage(grid, myResources.getString("MakerError7"), 3, 0,12);
				}
				
				// shape error handling
				if(shape.equals("square") && neighbourType.equals("3-next")){
					isError = true;
					displayMessage(grid, myResources.getString("MakerError8"), 3, 0, 13);
				}
				else if (shape.equals("triangle") 
						&& (neighbourType.equals("cardinal") || neighbourType.equals("diagonal"))){
					isError = true;
					displayMessage(grid, myResources.getString("MakerError8"), 3, 0, 13);
				}
				else if (shape.equals("hexagon") && 
						(neighbourType.equals("cardinal") || neighbourType.equals("diagonal") || neighbourType.equals("3-next"))){
					isError = true;
					displayMessage(grid, myResources.getString("MakerError8"), 3, 0, 13);
				}

				// parsing error checking
				try {
					gridxval = Integer.parseInt(gridx.getText());
					gridyval = Integer.parseInt(gridy.getText());
					cellxval = Integer.parseInt(cellx.getText());
					cellyval = Integer.parseInt(celly.getText());
				} catch(NumberFormatException ea) {
					displayMessage(grid, myResources.getString("MakerError1"), 3, 0, 9);
					isError = true;
					//ea.printStackTrace();
				}
				
				// grid size error handling
				if(cellxval > gridxval/2 || cellyval > gridyval/2){
					isError = true;
					displayMessage(grid, myResources.getString("MakerError1"), 3, 0, 9);
				}

				// specific files parsing error handling
				try {
					if(fileType == 0) {
						try {
							probcellval = Double.parseDouble(probcell.getText());
						} catch(NumberFormatException ea) {
							displayMessage(grid, myResources.getString("MakerError2"), 3, 0, 10);
							isError = true;
							//ea.printStackTrace();
						}
						if(!isError) {
							new GOLXMLmaker(filename, shape, neighbourType, edgeType, gridxval, gridyval, cellxval, cellyval, probcellval);
							displayMessage(grid, filename + ".xml created!", 3, 1, 8);
						}		
					}
					else if(fileType == 1) {
						try {
							probxval = Double.parseDouble(probx.getText());
							proboval = Double.parseDouble(probo.getText());
							threshval = Double.parseDouble(thresh.getText());
						} catch(NumberFormatException ea) {
							displayMessage(grid, myResources.getString("MakerError3"), 3, 0, 10);
							isError = true;
							//ea.printStackTrace();
						}
						if(!isError) {
							new SegXMLmaker(filename, shape, neighbourType, edgeType, gridxval, gridyval, cellxval, cellyval, probxval, proboval, threshval);
							displayMessage(grid, filename + ".xml created!", 3, 1, 8);
						}	
					}
					else if(fileType == 2) {
						try {
							probfishval = Double.parseDouble(probfish.getText());
							probsharkval = Double.parseDouble(probshark.getText());
							rtsharkval = Integer.parseInt(rtshark.getText());
							rtfishval = Integer.parseInt(rtfish.getText());
							esharkval = Integer.parseInt(eShark.getText());
							gesharkval = Integer.parseInt(geShark.getText());
						} catch(NumberFormatException ea) {
							displayMessage(grid, myResources.getString("MakerError4"), 3, 0, 10);
							isError = true;
							//ea.printStackTrace();
						}
						if(!isError) {
							new WatorXMLmaker(filename, shape, neighbourType, edgeType, gridxval, gridyval, cellxval, cellyval, probfishval, 
									probsharkval, rtsharkval, rtfishval, esharkval, gesharkval);
							displayMessage(grid, filename + ".xml created!", 3, 1, 8);
						}	
					}
					else if(fileType == 3) {
						try {
							probfireval = Double.parseDouble(probfire.getText());
							problightval = Double.parseDouble(problight.getText());
							probnewtreeval = Double.parseDouble(probnewtree.getText());
						} catch(NumberFormatException ea) {
							displayMessage(grid, myResources.getString("MakerError5"), 3, 0, 10);
							isError = true;
							//ea.printStackTrace();
						}
						if(!isError) {
							new FireXMLmaker(filename, shape, neighbourType, edgeType, gridxval, gridyval, cellxval, cellyval, probfireval, problightval, probnewtreeval);
							displayMessage(grid, filename + ".xml created!", 3, 1, 8);
						}	
					}
					else if(fileType == 4){
						try {
							agentprobval = Double.parseDouble(agentProb.getText());
						} catch(NumberFormatException ea) {
							displayMessage(grid, myResources.getString("MakerError9"), 3, 0, 10);
							isError = true;
							//ea.printStackTrace();
						}
						if(!isError) {
							new SugarXMLmaker(filename, shape, neighbourType, edgeType, gridxval, gridyval, cellxval, cellyval, agentprobval);
							displayMessage(grid, filename + ".xml created!", 3, 1, 8);
						}	
					}
				} catch (FileNotFoundException e1) {
					displayMessage(grid, myResources.getString("MakerError"), 3, 0, 9);
					//e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					displayMessage(grid, myResources.getString("MakerError"), 3, 0, 9);
					//e1.printStackTrace();
				}
				//if(isError) {
				//displayMessage(grid, myResources.getString("MakerError"), 3, 0, 9);
				//}
			}
		});
		
		// create a place to see the shapes
		Scene scene = new Scene(grid, width, height, background);
		
		// set what happens on key press
		scene.setOnKeyPressed(ex -> {
			// return to menu scene
			try {
				if(ex.getCode() == KeyCode.M) {
					returnMenu();
				}		
			} catch (Exception e1) {
				displayMessage(grid, myResources.getString("MakerError"), 3, 0, 7);
				//e1.printStackTrace();
			}
		});
		return scene;
	}

	/**
	 * Show a message on a grid for the specified time at the given coordinates
	 * @param grid - pane to which message is added
	 * @param message - string representing error
	 * @param time - how long to let error last on screen
	 * @param x - position
	 * @param y - position
	 */
	private void displayMessage(GridPane grid, String message, int time, int x, int y) {
		Text display = new Text();
		grid.add(display,x,y);
		display.setText(message);
		FadeTransition ft = new FadeTransition(Duration.millis(time*1000), display);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setCycleCount(1);
		ft.play();
	}
	// creates a text field to display a message
	
	/**
	 * @param grid - pane to add textfield to
	 * @param prompt - string representing the prompt
	 * @param x - position
	 * @param y - position
	 * @return Textfield - represents the object we see
	 */
	private TextField makeTextField(GridPane grid, String prompt, int x, int y) {
		final TextField temp = new TextField();
		temp.setPromptText(prompt);
		temp.setPrefColumnCount(10);
		temp.getText();
		GridPane.setConstraints(temp, x, y);
		grid.getChildren().add(temp);
		return temp;
	}

	// Helper function of setupScene, returns a root with everything needed
	
	/**
	 * @param gridarray - Grid object representing the storage of all information
	 * @param width - width of each cell
	 * @param height - height of each cell
	 * @return Group - represents the actual simulation group of images. 
	 */
	public Group CreateRoot(Grid gridarray, int width, int height) {
		Cell[][] cellArray = gridarray.getCellArray();
		Group addition = new Group();
		for (int i = 0; i < cellArray.length; i++) {
			for (int j = 0; j < cellArray[0].length; j++) {
				if (shapetype.equals("square")) {
					addition.getChildren().add(GenerateRectangularCell(cellArray[i][j], width, height, i, j));
				}
				else if(shapetype.equals("triangle")) {
					addition.getChildren().add(GenerateTriangleCell(cellArray[i][j], width, height, i, j));
				}
				else if(shapetype.equals("hexagon")) {				
					addition.getChildren().add(GenerateHexagonCell(cellArray[i][j], width, height, i, j));
				}
			}
		}
		return addition;
	}
	/**
	 * @param BufferCell - Cell to be added
	 * @param width - width of cell
	 * @param height - height of cell
	 * @param j - position
	 * @param i - position
	 * @return Shape - to be added to root
	 */
	//Helper function for CreateRoot, generates one rectangular cell
	private Rectangle GenerateRectangularCell(Cell BufferCell, int width, int height, int i, int j) {
		Rectangle Image = new Rectangle((width * i + graphbufferW), (height * j), width, height);
		Image.setFill(BufferCell.getDisplayColor());
		Image.setStrokeWidth(1);
		Image.setStroke(Color.BLACK);
		if (BufferCell instanceof AgentCell) {
			Image.setStroke(Color.GREEN);
			Image.setStrokeWidth(5);

		}
		return Image;
	}
	/**
	 * @param BufferCell - Cell to be added
	 * @param width - width of cell
	 * @param height - height of cell
	 * @param j - position
	 * @param i - position
	 * @return Shape - to be added to root
	 */
	//Helper function for CreateRoot, generates one hexagonal cell
	private Polygon GenerateHexagonCell(Cell BufferCell, int width, int height, int j, int i) {
		Polygon Image = new Polygon();
		Double[] points;
		height = 3*height/4;
		if ((i % 2 )== 0) {
			points = new Double[] {
					width * j + width/4.0 + graphbufferW, 1.0*height * i, 
					width * j + width/2.0 + graphbufferW, 1.0*height * i, 
					width * j + 3*width/4.0 + graphbufferW, height * i + 1.0*height,
					width * j + width/2.0 + graphbufferW, height * i + 2.0*height,
					width * j + width/4.0 + graphbufferW, height * i + 2.0*height,
					width * j + 1.0*graphbufferW, height * i + 1.0*height
			};
		}
		else {
			points = new Double[] {
					width * j + 3*width/4.0 + graphbufferW, 1.0*height * i, 
					width * j + width + graphbufferW/1.0, 1.0*height * i, 
					width * j + 5*width/4.0 + graphbufferW, height * i + 1.0*height,
					width * j + width + graphbufferW/1.0, height * i + 2.0*height,
					width * j + 3*width/4.0 + graphbufferW, height * i + 2.0*height,
					width * j + width/2.0 + graphbufferW, height * i + 1.0*height
			};
		}
		Image.getPoints().addAll(points);
		Image.setFill(BufferCell.getDisplayColor());
		Image.setStrokeWidth(1);
		Image.setStroke(Color.BLACK);
		if (BufferCell instanceof AgentCell) {
			Image.setStroke(Color.GREEN);
			Image.setStrokeWidth(5);
		}
		return Image;
	}
	//Helper function for CreateRoot, generates a single triangular cell
	
	/**
	 * @param BufferCell - Cell to be added
	 * @param width - width of cell
	 * @param height - height of cell
	 * @param j - position
	 * @param i - position
	 * @return Shape - to be added to root
	 */
	private Polygon GenerateTriangleCell(Cell BufferCell, int width, int height, int j, int i) {
		Polygon Image = new Polygon();
		Double[] points;
		if ((i % 2 )== 0) {
			points = new Double[] {
					width * j + width/1.0 + graphbufferW, height * (j % 2) + height/1.0 * i, 
					width * j + width/0.5 + graphbufferW, height * ((j + 1) % 2) + height/1.0 * i, 
					width * j + graphbufferW/1.0, height * ((j + 1)% 2) + height/1.0 * i
			};
		}
		else {
			points = new Double[] {
					width * j + width/1.0 + graphbufferW, height * ((j + 1) % 2) + height/1.0 * i, 
					width * j + width/0.5 + graphbufferW, height * (j % 2) + height/1.0 * i, 
					width * j + graphbufferW/1.0, height * (j % 2) + height/1.0 * i
			};
		}
		Image.getPoints().addAll(points);
		Image.setFill(BufferCell.getDisplayColor());
		Image.setStrokeWidth(1);
		Image.setStroke(Color.BLACK);
		if (BufferCell instanceof AgentCell) {
			Image.setStroke(Color.GREEN);
			Image.setStrokeWidth(5);
		}
		return Image;
	}

	//Takes in a map of population values and adds them to the lineChart
	
	/**
	 * @param init_map - Map of strings to doubles representing parameter values
	 * @return LineChart - Plotted data
	 */
	private LineChart<Number, Number> GenerateLineChart(Map<String, Number> init_map) {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Population counts");
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);
		lineChart.setTitle("Population Plots");
		int count = 0;
		for (String s : init_map.keySet()) {
			if (datapoints.size() == count) {
				XYChart.Series<Number, Number> buffer = new XYChart.Series();
				buffer.getData().add(new XYChart.Data(stepcount, init_map.get(s)));
				buffer.setName(s);
				datapoints.add(buffer);
			}
			datapoints.get(count).getData().add(new XYChart.Data(stepcount, init_map.get(s)));
			count = count + 1;
		}
		for (int k = 0; k < count; k++) {
			lineChart.getData().add(datapoints.get(k));
		}
		return lineChart;
	}
	//Generates slider
	
	/**
	 * @param double1 - min
	 * @param double2 - max
	 * @param double3 - current
	 * @return Slider - conforming to the parameters
	 */
	private Slider generateSlider(Double double1, Double double2, Double double3) {
		Slider slider = new Slider(double1, double2, double3);
		slider.showTickMarksProperty();
		return slider;
	}

	//Launches game
	public static void main(String[] args) {
		Application.launch(args);
	}
}
