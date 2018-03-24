import turtle.Turtle;

import java.util.Map;

//interface for setting the structure of a Command Node
public interface Command {
	
	/*
	 * getExecute() performs the function of the Node, encapsulates function within each Node
	 * returns a double file for prior nodes to access its value.
	 */
	public double getExecute(Map<String, Double> VarMap,  Map<String, SlogoNode> FunctMap, Map<Integer, Turtle> turtleMap);
	

}
