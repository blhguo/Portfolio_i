import java.util.List;
import java.util.Map;

import treenode.SlogoNode;
import turtle.Turtle;

public class Remainder extends SlogoNode{

	public Remainder() {
		numchildren = 2;
	}

	@Override
	public double getExecute(Map<String, Double> VarMap,  Map<String, SlogoNode> FunctMap, Map<Integer, Turtle> turtleMap) {
		double step = getValue(VarMap, FunctMap, turtleMap);
		return step;  //returns the final value of the node
	}
	
	@Override
	private double getValue(Map<String,Double> VarMap, Map<String, SlogoNode> FunctMap, Map<Integer, Turtle> turtleMap) {
		List<SlogoNode> leaf = this.getChildren();
		double x = leaf.get(0).getExecute(VarMap, FunctMap, turtleMap);
		double y = leaf.get(1).getExecute(VarMap, FunctMap, turtleMap);
		return x % y;
		}
}