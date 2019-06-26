//package testTree;

import java.awt.Point;
import java.util.ArrayList;

import connectK.BoardModel;

public class IDNode {
	static BoardModel currentBoard;
	int noOfMoves; // pass this in as a variable, children need noOfMoves + 1
	Byte currentPlayer; //0 for AI's move 1 for opponent
	float heuristicScore;
	ArrayList<IDNode> children;
	int noOfChildren;
	boolean containsWinningMove;
	public IDNode(BoardModel board){
		currentBoard = board;
		if(currentBoard.getLastMove() != null){
			currentPlayer = currentBoard.getSpace(currentBoard.getLastMove().getLocation());
		}
		else{
			currentPlayer = (byte) 1;
		}
		heuristicScore = 0;
		children = new ArrayList<IDNode>();
		calculateHeuristicScore();
	}
	
	void calculateHeuristicScore(){
		Point lastMove = null;
		if(currentBoard.getLastMove() != null){
			lastMove = currentBoard.getLastMove().getLocation();
			int horScore = checkHorizontal(lastMove);
			int vertScore = checkVertical(lastMove);
			int diagScore = checkDiagonal(lastMove);
			heuristicScore = horScore;
			if(vertScore > heuristicScore)
				heuristicScore = vertScore;
			if(diagScore > heuristicScore){
				heuristicScore = diagScore;	
			}
		}
		else{
			heuristicScore = 0;
		}

	}
	
	int checkHorizontal(Point lastMove){
		int piecesInARow = 1;
		//leftDead, rightDead are for a better Heuristic. Will implement later.
		//boolean rightDead = true;
		//boolean leftDead = true;
		//right
		for(int pieceNum = 1; pieceNum < currentBoard.kLength; pieceNum++){
			Point currentPoint = new Point(lastMove.x + pieceNum, lastMove.y);
			if(currentPoint.x > currentBoard.getWidth() - 1){
				//rightDead = true;
				break;
			}
			Byte playerAtCurrent = currentBoard.getSpace(currentPoint);
			if(playerAtCurrent != currentPlayer){
				if(playerAtCurrent == null){
					//rightDead = false;
					break;
				}
				break;
			}
			piecesInARow += 1;
		}
		if(piecesInARow >= currentBoard.kLength){
			return 110;//winning Code
		}
		
		//left
		for(int pieceNum = 1; pieceNum < currentBoard.kLength; pieceNum++){
			Point currentPoint = new Point(lastMove.x - pieceNum, lastMove.y);
			if(currentPoint.x < 0){
				//leftDead = true;
				break;
			}
			Byte playerAtCurrent = currentBoard.getSpace(currentPoint);
			if(playerAtCurrent != currentPlayer){
				if(playerAtCurrent == null){//empty space
					//leftDead = false;
					break;
				}
				//dead end
				break;
			}
			piecesInARow += 1;
		}
		if(piecesInARow >= currentBoard.kLength){
			return 110;//winning Code
		}
		else if(piecesInARow >= currentBoard.kLength - 1){
			return 86;
		}
		else if(piecesInARow >= currentBoard.kLength - 2){
			return 75;
		}
		else if(piecesInARow >= currentBoard.kLength - 3){
			return 65;
		}
		else if(piecesInARow >= currentBoard.kLength - 4){
			return 55;
		}
		else{
			return 0;
		}
	}
	
	int checkVertical(Point lastMove){
		int piecesInARow = 1;
		for(int pieceNum = 1; pieceNum < currentBoard.kLength; pieceNum++){
			Point currentPoint = new Point(lastMove.x, lastMove.y + pieceNum);
			if(currentPoint.y > currentBoard.getHeight()-1){
				break;
			}
			Byte playerAtCurrent = currentBoard.getSpace(currentPoint);
			if(playerAtCurrent != currentPlayer){
				if(playerAtCurrent == null){
					break;
				}
				break;
			}
			piecesInARow += 1;
		}
		if(piecesInARow >= currentBoard.kLength){
			return 100;//winning Code
		}
		//only need to check below pieces if gravity off
		if(!currentBoard.gravity){
			for(int pieceNum = 1; pieceNum < currentBoard.kLength; pieceNum++){
				Point currentPoint = new Point(lastMove.x, lastMove.y - pieceNum);
				if(currentPoint.y < 0){
					break;
				}
				Byte playerAtCurrent = currentBoard.getSpace(currentPoint);
				if(playerAtCurrent != currentPlayer){
					if(playerAtCurrent == null){
						break;
					}
					break;
				}
				piecesInARow +=1;
			}
		}
		

		//break out score
		if(piecesInARow >= currentBoard.kLength){
			return 100;//winning Code
		}
		else if(piecesInARow >= currentBoard.kLength - 1){
			return 80;
		}
		else if(piecesInARow >= currentBoard.kLength - 2){
			return 70;
		}
		else if(piecesInARow >= currentBoard.kLength - 3){
			return 60;
		}
		else if(piecesInARow >= currentBoard.kLength - 4){
			return 40;
		}
		else{
			return 0;
		}
	}
	
	int checkDiagonal(Point lastMove){
		ArrayList<Boolean> continueDirection = new ArrayList<Boolean>();
		//TR, TL, BL, BR
		continueDirection.add(true);
		continueDirection.add(true);
		continueDirection.add(true);
		continueDirection.add(true);
		ArrayList<Integer> countsOfDirection = new ArrayList<Integer>();
		countsOfDirection.add(1);
		countsOfDirection.add(1);
		countsOfDirection.add(1);
		countsOfDirection.add(1);
		//ArrayList
		for(int changeX = 1; changeX < currentBoard.kLength; changeX++){
				ArrayList<Point> pointArray = new ArrayList<Point>();
				Point TRPoint = new Point(lastMove.x + changeX, lastMove.y + changeX);
				Point TLPoint = new Point(lastMove.x - changeX, lastMove.y + changeX);
				Point BLPoint = new Point(lastMove.x - changeX, lastMove.y - changeX);
				Point BRPoint = new Point(lastMove.x + changeX, lastMove.y - changeX);
				pointArray.add(TRPoint);
				pointArray.add(TLPoint);
				pointArray.add(BLPoint);
				pointArray.add(BRPoint);
				//System.out.println("Iteration number " + (changeX* currentBoard.kLength + changeX));
				for(int index = 0; index < 4; index++){
					//System.out.print(pointArray.get(index) + "   ");
					if(pointArray.get(index).x < 0 || pointArray.get(index).x > currentBoard.getWidth() - 1
							|| pointArray.get(index).y < 0 || pointArray.get(index).y > currentBoard.getHeight() - 1){
						continueDirection.set(index, false);
					}
					if(continueDirection.get(index)){
						Byte playerAtPoint = currentBoard.getSpace(pointArray.get(index));
						if(playerAtPoint != currentPlayer){
							continueDirection.set(index, false);
						}
						else{
						countsOfDirection.set(index, countsOfDirection.get(index) + 1);
						}
					}
				}
				//System.out.println();

			
		}
		int piecesInARow = 1;
		int TR_BL_Count = countsOfDirection.get(0) + countsOfDirection.get(2) - 1;
		int TL_BR_Count = countsOfDirection.get(1) + countsOfDirection.get(1) - 1;
		piecesInARow = TR_BL_Count;
		if(TL_BR_Count > piecesInARow)
			piecesInARow = TL_BR_Count;
		if(piecesInARow >= currentBoard.kLength){
			return 130;//winning Code
		}
		else if(piecesInARow >= currentBoard.kLength - 1){
			return 90;
		}
		else if(piecesInARow >= currentBoard.kLength - 2){
			return 85;
		}
		else if(piecesInARow >= currentBoard.kLength - 3){
			return 74;
		}
		else if(piecesInARow >= currentBoard.kLength - 4){
			return 64;
		}
		else{
			return 0;
		}
	}
	
	
	boolean winMove(){
		
		return false;
	}
	float getHeuristicScore(){
		return heuristicScore;
	}
	
	BoardModel getCurrentBoard(){
		return currentBoard;
	}
	
	int getNoOfMoves(){
		return noOfMoves;
	}
	
	Byte getCurrentPlayer(){
		return currentPlayer;
	}
	
	ArrayList<IDNode> getChildren(){
		return children;
	}
	
	IDNode getChild(int index){
		return children.get(index);
	}
	
	int addChild(IDNode child){
		children.add(child);
		return children.size();
	}
	
	
}
