package UI;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ListIterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import Model.*;

public class ChessGUI extends JFrame implements MouseListener,Runnable {
	
	private static Rook wRookLeft, wRookRight, bRookLeft, bRookRight;
	private static Knight wKngtLeft, wKngtRight, bKngtLeft, bKngtRight;
	private static Bishop wBshpLeft, wBshpRight, bBshpLeft, bBshpRight;
	private static King wKing, bKing;
	private static Queen wQueen, bQueen;
	private static Pawn wPawn[], bPawn[];
	private JPanel chessBoard=new JPanel(new GridLayout(8,8));
	public static ChessGUI chessUI;
	private Container container;
	private Square currentState[][],threadSqrState[][];
	private Square sqr, sqrFlag, threadSqr;
	private int turnFlag=0;
	private ArrayList<Square> finalMovesList = new ArrayList<Square>();
	private JPanel hintPanel;
	private Button hint;
	private JSplitPane hintPane;
	private String threadName;
	private Thread t;
	Hashtable<String,ArrayList<Square>> hintMoves= new Hashtable <String,ArrayList<Square>>();
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	HashMap<String, Square> refineMap = new HashMap <String, Square>();
	
	ChessGUI(){
		
		Square square;
		Piece p;
		
		chessBoard = new JPanel(new GridLayout(8,8));
		chessBoard.setMinimumSize(new Dimension(600,500));
		chessBoard.setBorder(BorderFactory.createLoweredBevelBorder());
		
		container = getContentPane();
	
		
		//Let's populate the squares and display them in Container
		currentState = new Square[8][8];
		for (int i=0; i<8;i++){
			for (int j=0;j<8;j++){
				p=null;
				if (i==0&&j==0){
					p = bRookLeft;
				}
				else if(i==0&&j==1){
					p=bKngtLeft;
				}
				else if(i==0&&j==2){
					p=bBshpLeft;
				}
				else if(i==0&&j==3){
					p=bQueen;
				}
				else if(i==0&&j==4){
					p=bKing;
				}
				else if (i==0&&j==5){
					p=bBshpRight;
				}
				else if (i==0&&j==6){
					p=bKngtRight;
				}
				else if(i==0&&j==7){
					p=bRookRight;
				}
				else if(i==1){
					p=bPawn[j];
				}
				
				else if(i==7&&j==0){
					p=wRookLeft;
				}
				else if(i==7&&j==1){
					p=wKngtLeft;
				}
				else if(i==7&&j==2){
					p=wBshpLeft;
				}
				else if(i==7&&j==3){
					p=wQueen;
				}
				else if(i==7&&j==4){
					p=wKing;
				}
				else if(i==7&&j==5){
					p=wBshpRight;
				}
				else if (i==7&&j==6){
					p=wKngtRight;
				}
				else if(i==7&&j==7){
					p=wRookRight;
				}
				else if(i==6){
					p=wPawn[j];
				}
				
				square = new Square(i,j,p);
				square.addMouseListener(this);
				chessBoard.add(square);
				currentState[i][j]=square;
			}
		}
		
		//container.add(chessBoard);
		
		hintPanel = new JPanel();
		hint = new Button("hint");
		hint.setBackground(Color.GRAY);
		hint.setForeground(Color.white);
		hint.setPreferredSize(new Dimension(120,40));
		hint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				//Check who's turn it is when Hint button is clicked
				// Get the current state of the Board. 
				// Start a Thread for each sqaure where the current turn's pieces are there. 
				
				//Clear the HashMap which holds the best possible moves for every piece in a turn 
				refineMap.clear();
								
				for (int i=0;i<8;i++){
					for (int j=0;j<8;j++){
						if (currentState[i][j].getPiece() != null){
							if(currentState[i][j].getPiece().getColor() == turnFlag){
								//write code to start thread for each piece 
								//ChessGUI hintThread = new ChessGUI(currentState[i][j].getPiece().getName(),currentState[i][j]);
								Runnable r = new ChessGUI(currentState[i][j].getPiece().getName(),currentState[i][j],currentState, hintMoves);
								//new Thread(r).start();
								Thread t = new Thread(r);
								t.start();
								threads.add(t);
								
								//hintThread.start();
							}
						}
					}
				}
				
				//Joining all the threads so that we know all of them are completed 
				while(Thread.activeCount()!=2){
				for(Thread thread:threads){
					try {
						thread.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				}
				
				Set<String> Keys = hintMoves.keySet();
				
				for(String key: Keys){
					ArrayList<Square> suggestionList = hintMoves.get(key);
					ArrayList<Square> captureList = new ArrayList<Square>();
					ArrayList<Square> noCaptureList = new ArrayList<Square>();
					
					int wy=0;
					int by=7;
					int captureSig = 0;
					Square tempSq = null;
					
				if(!suggestionList.isEmpty()){
					
					for (Square s:suggestionList){
						if(s.getPiece()!=null){
							if(s.getPiece().getColor()!=turnFlag){
								captureList.add(s);
								captureSig = 1;
								//refineMap.put(key, s);
							}
						}
					}
					
					if(captureList.size()==1){
						refineMap.put(key, captureList.get(0));
					}
					
					//If there are two or more captures for a given piece in a turn. 
					else if(captureList.size()>1){
						for(Square q : captureList){
							//White's turn since capturePiece will be black
							if(q.getPiece().getColor()!=0){
								//highest Y selected
								if(wy<=q.y){
									wy = q.y;
									tempSq = q;
								}
							}
							//Black's turn since captiredPiece will be white
							else if(q.getPiece().getColor()==0){
								//lowest y selected 
								if(by>=q.y){
									by = q.y;
									tempSq = q;
								}
							}
						}
						
						if(turnFlag==0){
						findHighestX(wy, key, tempSq);
						}
						else if(turnFlag!=0){
							findHighestX(by, key, tempSq);
						}
						
						//refineMap.put(key, tempSq);
					}
					
					
					//If there are no capture by that piece
					
					if(captureSig==0){
						
						for (Square s:suggestionList){
							
							if(turnFlag==0){
								//highest Y selected
								if(wy<=s.y){
									wy = s.y;
									tempSq = s;
								}
							}
							else if(turnFlag!=0){
								//lowest y selected 
								if(by>=s.y){
									by = s.y;
									tempSq = s;
								}
							}
						
						}
						
						if(turnFlag==0){
							findHighestX(wy, key, tempSq);
							}
							else if(turnFlag!=0){
								findHighestX(by, key, tempSq);
							}
						
						//refineMap.put(key, tempSq);
						
					}
					
				}
				}
				
				//Clear the HashTable which has the possible moves for a piece in a turn
				hintMoves.clear();
				
				//Refine the Map which has stored one move for each piece in a given turn. 
				refineMovesInMap(refineMap);
		
			}
			
			
			//Method to find the highest X for the right'st Y
			private void findHighestX(int y,String s, Square sq){
				int count = 0;
				int wx = 7;
				int bx = 0;
				//Square highXSq = null;
				
				ArrayList<Square> sugList = hintMoves.get(s);
				for(Square square : sugList){
					if (square.y == y){
						count++;
					}
				}
				if(count==1){
					refineMap.put(s, sq);
				}
				else if(count>1){
					//find the highest x for the right'st y
					for(Square square : sugList){
						if(square.y == y){
							if(turnFlag==0){
								if(wx>square.x){
									wx=square.x;
								}
							}
							
							else if (turnFlag!=0){
								if(bx<square.x){
									bx=square.x;
								}
							}
						}
					}
					
					if(turnFlag==0){
					for(Square square:sugList){
						if(square.x==wx && square.y == y){
							refineMap.put(s, square);
						}
					}
					}
					else if (turnFlag !=0){
						for(Square square:sugList){
							if(square.x==bx && square.y == y){
								refineMap.put(s, square);
							}
						}
					}
					
				}	
				
			}
			
			private void refineMovesInMap(HashMap<String,Square> map){
				
				HashMap<String,Square> finalCapture = new HashMap<String,Square>();
				
				for(String k : map.keySet()){
					Square sq = map.get(k);
					if(map.get(k).getPiece()!=null){
						if(map.get(k).getPiece().getColor()!=turnFlag){
							finalCapture.put(k,map.get(k));
						}
					}
				}
				
				if (finalCapture.size()==1){
					//code to highlight the possible destination 
					//code to highlight the selected piece 
					highlightThePieces(finalCapture);
				}
				
				else if (finalCapture.size()>1){
					//send the 'square and key' pairs to select one pair  
					HashMap<String,Square> hm = selectTheFinalHint(finalCapture);
					highlightThePieces(hm);
				}
				
				else {
					//no capture, so send all the 'square and key' pairs to be selected for that one move.  
					
					HashMap<String,Square> hm = selectTheFinalHint(map);
					highlightThePieces(hm);
				}
				
			}
			
			private void highlightThePieces(HashMap<String,Square> hm){
				
				for (String s: hm.keySet()){
					hm.get(s).setpossibledestination();
					for(int i=0;i<8;i++){
						for (int j=0;j<8;j++){
							if(currentState[i][j].getPiece()!=null){
							if(currentState[i][j].getPiece().getName().equals(s)){
								currentState[i][j].select();
							}
							}
						}
					}
					}
			}
			
			private HashMap<String,Square> selectTheFinalHint(HashMap<String,Square> hm){
				HashMap<String,Square> returnMap = new HashMap<String,Square>();
				HashMap<String,Square> findHighestXMap = new HashMap<String,Square>();
				HashMap <String,Square> finalPossXMap = new HashMap<String,Square>();
				HashMap <String,Square> findSelectedXMap = new HashMap<String,Square>();
				HashMap <String,Square> finalSelectedMap = new HashMap<String,Square>();
				int wy = 0;
				int wx = 7;
				int wly = 7;
				int whx = 0; 
				int by = 7;
				int bx = 0;
				int bhy = 0;
				int blx = 7;
				
				Square tmp = null;
				String tmpName = null;
				//white turn
				if(turnFlag==0){
					
					//Find the highest Y in the map. 
					for (String s:hm.keySet()){
						if(wy<hm.get(s).y){
							wy = hm.get(s).y;
							tmp = hm.get(s);
							tmpName = s;
						}
					}
					
					for (String sq : hm.keySet()){
						if (wy == hm.get(sq).y){
							findHighestXMap.put(sq, hm.get(sq));
						}		
					}
					
					//Returning the map, if we selected just by getting the Highest Y in the list of white pieces. 
					if(findHighestXMap.size() == 1){
						returnMap.put(tmpName, tmp);
						return returnMap;
					}
					
					//Find the lowest X for the possible Square for a white piece
					else if(findHighestXMap.size()>1){
						for (String x : findHighestXMap.keySet()){
							if (wx>findHighestXMap.get(x).x){
								wx = findHighestXMap.get(x).x;
							}
						}
						for (String t : findHighestXMap.keySet()){
							if ( wx == findHighestXMap.get(t).x){
								finalPossXMap.put(t, findHighestXMap.get(t));
							}
						}
						
						//returning the map if we are able to find the lowest X in the list of white pieces having the same highest Y
						if(finalPossXMap.size()==1){
							return finalPossXMap;
						}
						
						else if (finalPossXMap.size()>1){
							//find the lowest Y for the selected square in the finalPossXMap
							for (String a : finalPossXMap.keySet()){
								for (int i=0;i<8;i++){
									for (int j=0;j<8;j++){
										if(currentState[i][j].getPiece()!=null){
										if(currentState[i][j].getPiece().getName().equals(a)){
											if(wly > currentState[i][j].y){
												wly = currentState[i][j].y;
											}
										}
										}
									}
								}
							}
							
							for(String b: finalPossXMap.keySet()){
								for (int i=0;i<8;i++){
									for (int j=0;j<8;j++){
										if(currentState[i][j].getPiece()!=null){
										if(currentState[i][j].getPiece().getName().equals(b)){
											if(currentState[i][j].y == wly){
												findSelectedXMap.put(b, finalPossXMap.get(b));
											}
										}
										}
									}
									}
							}
							
							if (findSelectedXMap.size() == 1){
								return findSelectedXMap;
							}
							
							else if(findSelectedXMap.size()>1){
								//Find the highest X for a white as the last option. 
								for (String f : findSelectedXMap.keySet()){
									for (int i=0;i<8;i++){
										for (int j=0;j<8;j++){
											if(currentState[i][j].getPiece()!=null){
											if(currentState[i][j].getPiece().getName().equals(f)){
												if(whx < currentState[i][j].x){
													whx = currentState[i][j].x;
												}
											}
										}
										}
									}
								}
								
								for(String c: findSelectedXMap.keySet()){
									for (int i=0;i<8;i++){
										for (int j=0;j<8;j++){
											if(currentState[i][j].getPiece()!=null){
											if(currentState[i][j].getPiece().getName().equals(c)){
												if(currentState[i][j].x == whx){
													finalSelectedMap.put(c, findSelectedXMap.get(c));
												}
											}
										}
										}
										}
								}
								return finalSelectedMap;
								
							}
							
							
						}
						
					}
					
					
				}
				
				//Black's turn
				if (turnFlag!=0){
					
					//Find the Lowest Y in the map. 
					for (String s:hm.keySet()){
						if(by>hm.get(s).y){
							by = hm.get(s).y;
							tmp = hm.get(s);
							tmpName = s;
						}
					}
					
					for (String sq : hm.keySet()){
						if (by == hm.get(sq).y){
							findHighestXMap.put(sq, hm.get(sq));
						}		
					}
					
					//Returning the map, if we selected just by getting the lowest Y in the list of black pieces. 
					if(findHighestXMap.size() == 1){
						returnMap.put(tmpName, tmp);
						return returnMap;
					}
					
					//Find the highest X for the possible Square for a black piece
					else if(findHighestXMap.size()>1){
						for (String x : findHighestXMap.keySet()){
							if (bx<findHighestXMap.get(x).x){
								bx = findHighestXMap.get(x).x;
							}
						}
						for (String t : findHighestXMap.keySet()){
							if ( bx == findHighestXMap.get(t).x){
								finalPossXMap.put(t, findHighestXMap.get(t));
							}
						}
						
						//returning the map if we are able to find the highest X in the list of black pieces having the same lowest Y
						if(finalPossXMap.size()==1){
							return finalPossXMap;
						}
						
						else if (finalPossXMap.size()>1){
							//find the highest Y for the selected square in the finalPossXMap
							for (String a : finalPossXMap.keySet()){
								for (int i=0;i<8;i++){
									for (int j=0;j<8;j++){
										if(currentState[i][j].getPiece()!=null){
										if(currentState[i][j].getPiece().getName().equals(a)){
											if(bhy < currentState[i][j].y){
												bhy = currentState[i][j].y;
											}
										}
									}
									}
								}
							}
							
							for(String b: finalPossXMap.keySet()){
								for (int i=0;i<8;i++){
									for (int j=0;j<8;j++){
										if(currentState[i][j].getPiece()!=null){
										if(currentState[i][j].getPiece().getName().equals(b)){
											if(currentState[i][j].y == bhy){
												findSelectedXMap.put(b, finalPossXMap.get(b));
											}
										}
									}
									}
									}
							}
							
							if (findSelectedXMap.size() == 1){
								return findSelectedXMap;
							}
							
							else if(findSelectedXMap.size()>1){
								//Find the lowest X for a black as the last option. 
								for (String f : findSelectedXMap.keySet()){
									for (int i=0;i<8;i++){
										for (int j=0;j<8;j++){
											if(currentState[i][j].getPiece()!=null){
											if(currentState[i][j].getPiece().getName().equals(f)){
												if(blx > currentState[i][j].x){
													blx = currentState[i][j].x;
												}
											}
										}
										}
									}
								}
								
								for(String c: findSelectedXMap.keySet()){
									for (int i=0;i<8;i++){
										for (int j=0;j<8;j++){
											if(currentState[i][j].getPiece()!=null){
											if(currentState[i][j].getPiece().getName().equals(c)){
												if(currentState[i][j].x == blx){
													finalSelectedMap.put(c, findSelectedXMap.get(c));
												}
											}
										}
										}
										}
								}
								return finalSelectedMap;
								
							}
							
							
						}
						
					}
					
					
					
				}
				
				
				return returnMap;
				
			}

		});
		hintPanel.add(hint);
		
		hintPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chessBoard, hintPanel);
		hintPane.setDividerLocation(1200);
		
		container.add(hintPane);
	}
	
	ChessGUI(String name, Square threadSqr, Square threadState[][], Hashtable<String,ArrayList<Square>> threadContainer){
		this.threadName = name;
		this.threadSqr = threadSqr;
		this.threadSqrState= threadState; 
		this.hintMoves = threadContainer;
	}
	
	
	
	public static void main(String[] args){
		
		bRookLeft = new Rook("BROOK_L",1,"resources/black_rook.png");
		bRookRight = new Rook("BROOK_R",1,"resources/black_rook.png");
		wRookRight = new Rook("WROOK_R",0,"resources/white_rook.png");
		wRookLeft = new Rook("WROOK_L",0,"resources/white_rook.png");
		
		bBshpLeft = new Bishop("BBISH_L",1,"resources/black_bishop.png");
		bBshpRight = new Bishop("BBISH_R",1,"resources/black_bishop.png");
		wBshpRight = new Bishop("WBISH_R",0,"resources/white_bishop.png");
		wBshpLeft = new Bishop("WBISH_L",0,"resources/white_bishop.png");
		
		bKngtLeft = new Knight("BKNGT_L",1,"resources/black_knight.png");
		bKngtRight = new Knight("BKNGT_R",1,"resources/black_knight.png");
		wKngtLeft = new Knight("WKNGT_R",0,"resources/white_knight.png");
		wKngtRight = new Knight("WKNGT_L",0,"resources/white_knight.png");
		
		bQueen = new Queen("BQUEEN",1,"resources/black_queen.png");
		wQueen = new Queen("WQUEEN",0,"resources/white_queen.png");
		
		bKing = new King("BKING",1,"resources/black_king.png");
		wKing = new King("WKING",0,"resources/white_king.png");
		
		bPawn = new Pawn[8];
		wPawn = new Pawn[8];
		
		for(int i=0;i<8;i++){
		bPawn[i] = new Pawn("BPAWN"+(i+1),1,"resources/black_pawn.png");
		wPawn[i] = new Pawn("WPAWN"+(i+1),0,"resources/white_pawn.png");
		}
		
		
		chessUI = new ChessGUI();
		chessUI.setVisible(true);
		chessUI.setMinimumSize(new Dimension(1500, 1700));
		chessUI.setResizable(false);
	
	}
	
    private void highlightPossibleMoves(ArrayList<Square> moveList)
    {
    	ListIterator<Square> tmpSqr = moveList.listIterator();
    	while(tmpSqr.hasNext())
    	{
    		tmpSqr.next().setpossibledestination();
    		}
    }
    
    private void cleandestinations(ArrayList<Square> moveList)      //Function to clear the last move's destinations
    {
    	ListIterator<Square> it = moveList.listIterator();
    	while(it.hasNext())
    	{
    		it.next().removepossibledestination();
    	}
    }
    
	public void changeTurn()
	{

		if(finalMovesList.isEmpty()==false)
		{
			cleandestinations(finalMovesList);
		}
		if(sqrFlag!=null)
		{
			sqrFlag.deselect();
		}
		sqrFlag=null;
		turnFlag^=1;
	}
	
	public void refineHintMoves(){
		
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		sqr = (Square)arg0.getSource();
		
		// here we are selecting the coin to move and setting it in an obj called 'previous'
				if (sqrFlag==null)
				{
					if(sqr.getPiece()!=null)
					{
						if(sqr.getPiece().getColor()!=turnFlag) // to see who's chance it is
							return;
						sqr.select();
						sqrFlag=sqr;
						finalMovesList.clear();
						finalMovesList=sqr.getPiece().getPossibleMoves(sqr.x, sqr.y,currentState);
						highlightPossibleMoves(finalMovesList);
					}
				}
				
				else
				{
					//If you selected the same cell again, you have to deselect 
					if(sqr.x==sqrFlag.x && sqr.y==sqrFlag.y)
					{
						sqr.deselect();
						cleandestinations(finalMovesList);
						finalMovesList.clear();
						sqrFlag=null;
					}
					
					//if the cell you are going to move is empty or if it has oppopnent piece 
					else if(sqr.getPiece()==null||sqrFlag.getPiece().getColor()!=sqr.getPiece().getColor())
					{
						if(sqr.ispossibledestination())
						{
							if(sqr.getPiece()!=null)
							{
								sqr.removePiece();
							}
							sqr.setPiece(sqrFlag.getPiece());
							sqrFlag.removePiece();
							changeTurn();
				
						}
						
						if(sqrFlag!=null)
						{
							sqrFlag.deselect();
							sqrFlag=null;
						}
						cleandestinations(finalMovesList);
						finalMovesList.clear();
					}
					
					//if the user selected the destination cell which is already having the same teams piece 
					else if(sqrFlag.getPiece().getColor()==sqr.getPiece().getColor())
					{
						sqrFlag.deselect();
						cleandestinations(finalMovesList);
						finalMovesList.clear();
						sqr.select();
						sqrFlag=sqr;
						finalMovesList=sqr.getPiece().getPossibleMoves(sqr.x, sqr.y,currentState);
						highlightPossibleMoves(finalMovesList);
					}
				}				
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		ArrayList<Square> hintList = new ArrayList<Square>();
		Square hintSquare = threadSqr;
	      try {
	           hintList = hintSquare.getPiece().getPossibleMoves(hintSquare.x, hintSquare.y, threadSqrState);
	           synchronized(this){
	           this.addItem(threadName, hintList);
	           }
	          Thread.sleep(1);
	       
	      }catch (InterruptedException e) {
	         System.out.println("Thread " +  threadName + " interrupted.");
	      }
		
	}
	
	 public synchronized void addItem(String key, ArrayList hintList) {
		 hintMoves.put(key, hintList);
		 
	 }
	

}


