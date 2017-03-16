package Model;

import java.util.ArrayList;

import UI.Square;

//extends Piece since This is a piece (Is-a relationship) 
public class Knight extends Piece {

	public Knight(String name,int color, String path){
		setColor(color);
		setName(name);
		setPath(path);
	}
	
	
	//Get all the possible moves of this piece given the current state of the board and location
	//of the piece 
public ArrayList<Square> getPossibleMoves(int x, int y, Square currentState[][]){
		
		eligibleMoves.clear();
		
		//Knight moves as per the assignment appendix, adding all the xy moves into 
		//two individual arrays and iterate over it to get the eligible moves. 
		int tmpx[]={x+1,x+1,x+2,x+2,x-1,x-1,x-2,x-2};
		int tmpy[]={y-2,y+2,y-1,y+1,y-2,y+2,y-1,y+1};
		for(int i=0;i<8;i++)
			if((tmpx[i]>=0&&tmpx[i]<8&&tmpy[i]>=0&&tmpy[i]<8))
				if((currentState[tmpx[i]][tmpy[i]].getPiece()==null||currentState[tmpx[i]][tmpy[i]].getPiece().getColor()!=this.getColor()))
				{
					eligibleMoves.add(currentState[tmpx[i]][tmpy[i]]);
				}
		
		return eligibleMoves;
		
	}
	
	
}
