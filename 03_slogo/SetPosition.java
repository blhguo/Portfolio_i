import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;
import treenode.SlogoNode;
import turtle.Turtle;
import views.SlogoView;

public class SetPosition extends SlogoNode{
//	
//	private double value = 0;
//	private double distance = 0;
//	public Forward() {
//		this.val = getValue();
//	}

	private double[] location = new double[3];

    public SetPosition() {
        numchildren = 2;
    }

    private void setpos(Map<Integer, Turtle> turtleMap, Map<Integer, Double[]> map) {
        for (Integer i : turtleMap.keySet()) {
            if (turtleMap.get(i).isActive()) {
                turtleMap.get(i).setAbsoluteLocation(new Point2D(map.get(i)[0], map.get(i)[1]));
            }
        }

	}

	@Override
	public double getExecute(Map<String, Double> VarMap,  Map<String, SlogoNode> FunctMap, Map<Integer, Turtle> turtleMap) {
		double step = 0;
		HashMap<Integer, Double[]> map = new HashMap<>();
		for (int n : turtleMap.keySet()) {
			if (turtleMap.get(n).isActive()) {
				VarMap.put("ID_RESERVED", (double) n);
				step = getValue(VarMap, FunctMap, turtleMap);
				map.put(n, new Double[]{location[0], location[1], location[2]});
			}
		}
//        for (Integer i : map.keySet()){
//            System.out.println("Key: " + i + " Value: " + map.get(i));
//        }
		setpos(turtleMap, map);
		return location[2];  //returns the final value of the node
	}
	
	@Override
	private double getValue(Map<String,Double> VarMap, Map<String, SlogoNode> FunctMap, Map<Integer, Turtle> turtleMap) {
		// TODO Auto-generated method stub
		//TODO: Update according to Jamie's stuff
		double distance = 0;
		List<SlogoNode> leaf = this.getChildren();
		double xpos = leaf.get(0).getExecute(VarMap, FunctMap, turtleMap);
		double ypos = leaf.get(1).getExecute(VarMap, FunctMap, turtleMap);
		location[0] = xpos;
		location[1] = ypos;
		
		for (int n : turtleMap.keySet()) {
			if (turtleMap.get(n).isActive()) {
		double CurX = turtleMap.get(n).getLocation().getX() - SlogoView.TURTLEVIEWX - .5 * SlogoView.TURTLEVIEWWIDTH
				+ .5 * Turtle.TURTLESIZE;
		double CurY = turtleMap.get(n).getLocation().getY() - SlogoView.TURTLEVIEWY - .5 * SlogoView.TURTLEVIEWHEIGHT
				+ .5 * Turtle.TURTLESIZE;

		distance = Math.sqrt(Math.pow(xpos - CurX, 2) + Math.pow(ypos - CurY, 2));
		location[2] = distance;
		//setpos(turtleMap.get(n), location);
	}}
		return distance;
	
}}
