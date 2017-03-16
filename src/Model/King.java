package Model;

import java.util.ArrayList;

import UI.Square;

//extends Piece since This is a piece (Is-a relationship) 
public class King extends Piece {

	public King(String name,int color, String path){
		setColor(color);
		setName(name);
		setPath(path);
	}
	
	
	//Get all the possible moves of this piece given the current state of the board and location
	//of the piece 
public ArrayList<Square> getPossibleMoves(int x, int y, Square currentState[][]){
		
		eligibleMoves.clear();
		
		//King moves in all directions but just one step at a time. 
		int xLoc[]={x,x,x+1,x+1,x+1,x-1,x-1,x-1};
		int yLoc[]={y-1,y+1,y-1,y,y+1,y-1,y,y+1};
		for(int i=0;i<8;i++)
			if((xLoc[i]>=0&&xLoc[i]<8&&yLoc[i]>=0&&yLoc[i]<8))
				if((currentState[xLoc[i]][yLoc[i]].getPiece()==null||currentState[xLoc[i]][yLoc[i]].getPiece().getColor()!=this.getColor()))
					eligibleMoves.add(currentState[xLoc[i]][yLoc[i]]);
		return eligibleMoves;
	}
	
	
}
