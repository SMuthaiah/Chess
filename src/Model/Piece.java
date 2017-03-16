package Model;
import java.util.ArrayList;

import UI.Square;

public abstract class Piece{

	private String path;
	private int color; 
	private String name = null;
	protected ArrayList<Square> eligibleMoves = new ArrayList<Square>();
	
	//Abstract method
	public abstract ArrayList<Square> getPossibleMoves(int x,int y,Square pos[][]);
	
	//Getters and Setters for the datamembers of the piece class 
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	
	
}
