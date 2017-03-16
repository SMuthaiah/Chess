package Model;

import java.util.ArrayList;

import UI.Square;

//extends Piece since This is a piece (Is-a relationship) 
public class Pawn extends Piece {

	public Pawn(String name,int color, String path){
		setColor(color);
		setName(name);
		setPath(path);
	}
	
	
	//Get all the possible moves of this piece given the current state of the board and location
	//of the piece 
	public ArrayList<Square> getPossibleMoves(int x, int y, Square currentState[][]){
		
		eligibleMoves.clear();
		
		//Since Pawn can move only in forward direction, we have to identify which color the
		//pawn is before moving it. 
		
		//If pawn is white
		if(getColor()==0){
			
			//White Pawn is already in the end of the board, so no possible moves.
			if (x==0){
				return eligibleMoves; 
			}
			if(currentState[x-1][y].getPiece()==null)
			{
				eligibleMoves.add(currentState[x-1][y]);
				//If the Pawn has not been moved yet, we have the option of moving two
				//positions forward. 
				if(x==6)
				{
					if(currentState[4][y].getPiece()==null)
						eligibleMoves.add(currentState[4][y]);
				}
			}
			
			//Check if the pawn can move diagonally in case of opponent coin present ahead. 
			if((y>0)&&(currentState[x-1][y-1].getPiece()!=null)&&(currentState[x-1][y-1].getPiece().getColor()!=this.getColor()))
				{
					eligibleMoves.add(currentState[x-1][y-1]);
				}
			if((y<7)&&(currentState[x-1][y+1].getPiece()!=null)&&(currentState[x-1][y+1].getPiece().getColor()!=this.getColor()))
				{
					eligibleMoves.add(currentState[x-1][y+1]);
				}
		}
		
		//If the color of the pawn is Black. 
		else
		{
			if(x==8)
				return eligibleMoves;
			if(currentState[x+1][y].getPiece()==null)
			{
				eligibleMoves.add(currentState[x+1][y]);
				if(x==1)
				{
					if(currentState[3][y].getPiece()==null)
						eligibleMoves.add(currentState[3][y]);
				}
			}
			if((y>0)&&(currentState[x+1][y-1].getPiece()!=null)&&(currentState[x+1][y-1].getPiece().getColor()!=this.getColor()))
				{
					eligibleMoves.add(currentState[x+1][y-1]);
				}
			if((y<7)&&(currentState[x+1][y+1].getPiece()!=null)&&(currentState[x+1][y+1].getPiece().getColor()!=this.getColor()))
				{
					eligibleMoves.add(currentState[x+1][y+1]);
				}
				
		}		
		
		return eligibleMoves;
	}
		
}

