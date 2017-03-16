package Model;

import java.util.ArrayList;

import UI.Square;

//extends Piece since This is a piece (Is-a relationship) 
public class Queen extends Piece {

	public Queen(String name,int color, String path){
		setColor(color);
		setName(name);
		setPath(path);
	}
	
	
	//Get all the possible moves of this piece given the current state of the board and location
	//of the piece 
public ArrayList<Square> getPossibleMoves(int x, int y, Square currentState[][]){
		
		eligibleMoves.clear();
		int tempx;
		int tempy;
		
		//Check for eligibleMoves in the direction ability of rook. 
		
				tempx=x-1;
				while(tempx>=0)
				{
					if(currentState[tempx][y].getPiece()==null)
						eligibleMoves.add(currentState[tempx][y]);
					else if(currentState[tempx][y].getPiece().getColor()==this.getColor())
						break;
					else
					{
						eligibleMoves.add(currentState[tempx][y]);
						break;
					}
					tempx--;
				}
				
				tempx=x+1;
				while(tempx<8)
				{
					if(currentState[tempx][y].getPiece()==null)
						eligibleMoves.add(currentState[tempx][y]);
					else if(currentState[tempx][y].getPiece().getColor()==this.getColor())
						break;
					else
					{
						eligibleMoves.add(currentState[tempx][y]);
						break;
					}
					tempx++;
				}
				
				tempy=y-1;
				while(tempy>=0)
				{
					if(currentState[x][tempy].getPiece()==null)
						eligibleMoves.add(currentState[x][tempy]);
					else if(currentState[x][tempy].getPiece().getColor()==this.getColor())
						break;
					else
					{
						eligibleMoves.add(currentState[x][tempy]);
						break;
					}
					tempy--;
				}
				
				tempy=y+1;
				while(tempy<8)
				{
					if(currentState[x][tempy].getPiece()==null)
						eligibleMoves.add(currentState[x][tempy]);
					else if(currentState[x][tempy].getPiece().getColor()==this.getColor())
						break;
					else
					{
						eligibleMoves.add(currentState[x][tempy]);
						break;
					}
					tempy++;
				}
				
				//Check for eligible directions in the direction ability of Bishop
				tempx=x+1;tempy=y-1;
				while(tempx<8&&tempy>=0)
				{
					if(currentState[tempx][tempy].getPiece()==null)
						eligibleMoves.add(currentState[tempx][tempy]);
					else if(currentState[tempx][tempy].getPiece().getColor()==this.getColor())
						break;
					else
					{
						eligibleMoves.add(currentState[tempx][tempy]);
						break;
					}
					tempx++;
					tempy--;
				}
				
				tempx=x-1;tempy=y+1;
				while(tempx>=0&&tempy<8)
				{
					if(currentState[tempx][tempy].getPiece()==null)
						eligibleMoves.add(currentState[tempx][tempy]);
					else if(currentState[tempx][tempy].getPiece().getColor()==this.getColor())
						break;
					else
					{
						eligibleMoves.add(currentState[tempx][tempy]);
						break;
					}
					tempx--;
					tempy++;
				}
				
				tempx=x-1;tempy=y-1;
				while(tempx>=0&&tempy>=0)
				{
					if(currentState[tempx][tempy].getPiece()==null)
						eligibleMoves.add(currentState[tempx][tempy]);
					else if(currentState[tempx][tempy].getPiece().getColor()==this.getColor())
						break;
					else
					{
						eligibleMoves.add(currentState[tempx][tempy]);
						break;
					}
					tempx--;
					tempy--;
				}
				
				tempx=x+1;tempy=y+1;
				while(tempx<8&&tempy<8)
				{
					if(currentState[tempx][tempy].getPiece()==null)
						eligibleMoves.add(currentState[tempx][tempy]);
					else if(currentState[tempx][tempy].getPiece().getColor()==this.getColor())
						break;
					else
					{
						eligibleMoves.add(currentState[tempx][tempy]);
						break;
					}
					tempx++;
					tempy++;
				}
				
				return eligibleMoves;
		
		
	}
	
	
}
