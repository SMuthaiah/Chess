package Model;

import java.util.ArrayList;

import UI.Square;

//Rook extends Piece since rook is a piece (Is-a relationship) 
public class Rook extends Piece {
	
	private int xblockPiece = 0;
	private int yblockPiece = 0;

	public Rook(String name,int color, String path){
		setColor(color);
		setName(name);
		setPath(path);
	}
	
	
	//Get all the possible moves of this piece given the current state of the board and location
	//of the piece 
	public ArrayList<Square> getPossibleMoves(int x, int y, Square currentState[][]){
		
		
		
		eligibleMoves.clear();
		
		int tmpx=x-1;
		while(tmpx>=0)
		{
			if(currentState[tmpx][y].getPiece()==null)
				eligibleMoves.add(currentState[tmpx][y]);
			else if(currentState[tmpx][y].getPiece().getColor()==this.getColor())
				break;
			else
			{
				eligibleMoves.add(currentState[tmpx][y]);
				break;
			}
			tmpx--;
		}
		tmpx=x+1;
		while(tmpx<8)
		{
			if(currentState[tmpx][y].getPiece()==null)
				eligibleMoves.add(currentState[tmpx][y]);
			else if(currentState[tmpx][y].getPiece().getColor()==this.getColor())
				break;
			else
			{
				eligibleMoves.add(currentState[tmpx][y]);
				break;
			}
			tmpx++;
		}
		int tmpy=y-1;
		while(tmpy>=0)
		{
			if(currentState[x][tmpy].getPiece()==null)
				eligibleMoves.add(currentState[x][tmpy]);
			else if(currentState[x][tmpy].getPiece().getColor()==this.getColor())
				break;
			else
			{
				eligibleMoves.add(currentState[x][tmpy]);
				break;
			}
			tmpy--;
		}
		tmpy=y+1;
		while(tmpy<8)
		{
			if(currentState[x][tmpy].getPiece()==null)
				eligibleMoves.add(currentState[x][tmpy]);
			else if(currentState[x][tmpy].getPiece().getColor()==this.getColor())
				break;
			else
			{
				eligibleMoves.add(currentState[x][tmpy]);
				break;
			}
			tmpy++;
		}
		
		return eligibleMoves;
	}
	
	
}
