package UI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

import Model.Piece;

public class Square extends JPanel implements Comparable<Square>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isSelected=false;
	private JLabel sticker;
	private Piece piece; 
	int x,y;
	private boolean ispossibledestination;
	
	Square(int x, int y,Piece p){
		
		
		this.x = x;
		this.y = y;
		
		setLayout(new BorderLayout());
		
		if((x+y)%2==0){
			  setBackground(Color.white);
		}	
			 else{
			  setBackground(Color.GREEN);
			 }
		
		if(p!=null){
			setPiece(p);
		}
		
		
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece p) {
		piece=p;
		ImageIcon image=new ImageIcon(p.getPath());
		sticker = new JLabel(image);
		this.add(sticker);
	}
	
	public void removePiece()     
	{
			piece=null;
			this.remove(sticker);
		
	}

	//Function to highlight a cell to indicate that it is a possible valid move
	public void setpossibledestination()     
	{
		this.setBorder(BorderFactory.createLineBorder(Color.ORANGE,4));
		this.ispossibledestination=true;
	}
	
	//Remove the cell from the list of possible moves
	public void removepossibledestination()      
	{
		this.setBorder(null);
		this.ispossibledestination=false;
	}
	
	public boolean ispossibledestination()    //Function to check if the cell is a possible destination 
	{
		return this.ispossibledestination;
	}
	
	public void select()      
	{
		this.setBorder(BorderFactory.createLineBorder(Color.black,6));
		this.isSelected=true;
	}
	
	public void deselect()     
	{
		this.setBorder(null);
		this.isSelected=false;
	}
	
	 //Function to return if the cell is under selection
	public boolean isselected()  
	{
		return this.isSelected;
	}

	@Override
	public int compareTo(Square arg0) {
		// TODO Auto-generated method stub
		if(this.y > arg0.y){
		return 1;
		}
		
		else if(this.y < arg0.y){
		return 0;
		}
		
		else if(this.y == arg0.y){
			if (this.x < arg0.x){
				return 1;
			}
			else if (this.x > arg0.x){
				return 0;
			}
			else{
				return 3;
			}
		}
		
		return 0;
	}
	
}
