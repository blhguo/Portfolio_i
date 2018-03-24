package views;

import javafx.application.Application;
import javafx.stage.Stage;
import treenode.CommandFactory;
import treenode.NodeBuilder;
import treenode.SlogoNode;
import turtle.Turtle;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import TreeBuilding.TreeBuilder;
import TreeReader.TreeReader;

public class Main extends Application implements Observer{
	
	private static final String TITLE = "SLogo";
	private static Stage mainStage;
	private SlogoView simulation;

	private Turtle turtle;
    private Map<String, Double> variables = new HashMap<>();
    private Map<String, SlogoNode> functions = new HashMap<>();;
    private Map<Integer, Turtle> TurtleMap = new HashMap<>();

    public Main() {
    	//constructor
    }
    
	public static void main(String[] args) {
		launch(args);
		}
		
	@Override
	public void start(Stage primaryStage) throws Exception {
		simulation = new SlogoView();
		primaryStage.setResizable(false);
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(simulation.initializeStartScene(TurtleMap));
		primaryStage.show();
		simulation.addObserver(this);
        variables = new HashMap<>();
        functions = new HashMap<>();
		updateVarView();
	}
	
	public String[] sanitize(String[] array) {
		ArrayList<String> list = new ArrayList<String>();
		for (String s : array) {
				if (!s.matches("#(.*)"))
			        list.add(s);
		}
			return list.toArray(new String[list.size()]);
	}

	@Override
	public void update(Object o) {
		//data flow
		if (!TurtleMap.containsValue((Turtle) o)) {
		TurtleMap.put(TurtleMap.size(), (Turtle) o);
		}
		TreeBuilder Builder = new TreeBuilder(variables, functions, TurtleMap);
		CommandFactory factory = new CommandFactory(functions) {};
		TreeReader reader = new TreeReader();
		SlogoNode[] BufferArray = factory.convertStringtoNode(sanitize(simulation.getPassValue()), functions);
		SlogoNode Head = Builder.buildTree(BufferArray);
        simulation.setConsole(reader.evaluate(Head, variables, functions, TurtleMap));
        simulation.updateScreen();
		updateVarView();

	}
	
    public static void openWebPage(String url) {
	    try {
            Desktop.getDesktop().browse(new java.net.URI(url));
        }
        catch (URISyntaxException e){
	        System.out.println("DONE");
        }
        catch (IOException e){
	        System.out.println("DONE");
        }
    }
	public void updateVarView(){
	    simulation.updateVarView(variables);
    }
}