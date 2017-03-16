package Model;

import java.util.ArrayList;

import UI.Square;

//extends Piece since This is a piece (Is-a relationship) 
public class Bishop extends Piece {

	public Bishop(String name,int color, String path){
		setColor(color);
		setName(name);
		setPath(path);
	}
	
	
	//Get all the possible moves of this piece given the current state of the board and location
	//of the piece 
public ArrayList<Square> getPossibleMoves(int x, int y, Square currentState[][]){
		
		eligibleMoves.clear();
		
		//Bishop moves diagonally in all directions 
		
		//Check for one direction first and see if there is any eligible moves
		int tmpx=x+1,tmpy=y-1;
		while(tmpx<8&&tmpy>=0)
		{
			if(currentState[tmpx][tmpy].getPiece()==null)
			{
				eligibleMoves.add(currentState[tmpx][tmpy]);
			}
			else if(currentState[tmpx][tmpy].getPiece().getColor()==this.getColor())
				break;
			else
			{
				eligibleMoves.add(currentState[tmpx][tmpy]);
				break;
			}
			tmpx++;
			tmpy--;
		}
		
		//Another direction here
		tmpx=x-1;tmpy=y+1;
		while(tmpx>=0&&tmpy<8)
		{
			if(currentState[tmpx][tmpy].getPiece()==null)
				eligibleMoves.add(currentState[tmpx][tmpy]);
			else if(currentState[tmpx][tmpy].getPiece().getColor()==this.getColor())
				break;
			else
			{
				eligibleMoves.add(currentState[tmpx][tmpy]);
				break;
			}
			tmpx--;
			tmpy++;
		}
		
		//Another New directions here
		tmpx=x-1;tmpy=y-1;
		while(tmpx>=0&&tmpy>=0)
		{
			if(currentState[tmpx][tmpy].getPiece()==null)
				eligibleMoves.add(currentState[tmpx][tmpy]);
			else if(currentState[tmpx][tmpy].getPiece().getColor()==this.getColor())
				break;
			else
			{
				eligibleMoves.add(currentState[tmpx][tmpy]);
				break;
			}
			tmpx--;
			tmpy--;
		}
		
		//Last direction
		tmpx=x+1;tmpy=y+1;
		while(tmpx<8&&tmpy<8)
		{
			if(currentState[tmpx][tmpy].getPiece()==null)
				eligibleMoves.add(currentState[tmpx][tmpy]);
			else if(currentState[tmpx][tmpy].getPiece().getColor()==this.getColor())
				break;
			else
			{
				eligibleMoves.add(currentState[tmpx][tmpy]);
				break;
			}
			tmpx++;
			tmpy++;
		}
		return eligibleMoves;
		
	}
	
	
}
