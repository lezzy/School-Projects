import connectK.CKPlayer;
import connectK.BoardModel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class BboyAI extends CKPlayer{

	//long TURN_TIME = 4500;
	long TURN_TIME = 4500000000L;
	int current_depth;
	public BboyAI(byte player, BoardModel state) {
		super(player, state);
		teamName = "BboyAI";
	}
	
	@SuppressWarnings("null")
	private float generateMIN(BoardModel parent, long start_time, int depth_limit)
	{
		java.awt.Point potential_move = new Point();
		boolean pass = false;
		float min = 99999;
		float potential_score;
		Point best_min;
			
		current_depth++;
		
		ArrayList<Point> moves_list = getPotentialMoves(parent);
		
		byte player = (byte) (parent.getSpace(parent.lastMove) == 1? 2 : 1);
		
		// need if statement to check time limit
		if (System.nanoTime() - TURN_TIME > start_time || parent.winner() != -1 || current_depth >= depth_limit)
		{
			IDNode current_node = new IDNode(parent);
			return current_node.getHeuristicScore();
		}
		
				
		if (moves_list != null && parent.spacesLeft > 0) // There are possible moves to make
		{
			// need to go through each move.
			for(Point move: moves_list)
			{
				// compute the MAX of each move.
				potential_move = move;
				potential_score = generateMAX(parent.clone().placePiece(move, player), start_time, depth_limit);
				if (potential_score < min)
				{
					min = potential_score;
					best_min = potential_move;
				}
			}	
		}
		// need to check if moves is empty and there are stil spaces left
		else if (moves_list == null && parent.spacesLeft > 0)
		{
			// search random for free spot?
			Point new_move = new Point();
			boolean found = false;
			for (int x = 0; x < parent.width; x++)
			{
				for (int y = 0; y < parent.height; y++)
				{
					if (parent.getSpace(x, y) == 0)
					{
						new_move.x = x;
						new_move.y = y;
						found = true;
						break;
					}
				}
				if (found)
				{
					break;
				}
			}
			min = generateMIN(parent.clone().placePiece(new_move, parent.getSpace(parent.lastMove)), start_time, depth_limit);
			
		}
		else // there are no more spaces left
		{
			IDNode current_node = new IDNode(parent);
			return current_node.getHeuristicScore();
		}
		return min;

	}
	
	@SuppressWarnings("null")
	private float generateMAX(BoardModel parent, long start_time, int depth_limit)
	{
		float max = -99999;
		boolean pass = false;
		Point potential_move, best_max;
		float potential_score;
		
		current_depth++;
		
		ArrayList<Point> moves_list = getPotentialMoves(parent);
	
		byte player = (byte) (parent.getSpace(parent.lastMove) == 1? 2 : 1);
		
		// checking time
		if (System.nanoTime() - TURN_TIME > start_time || parent.winner() == -1 || current_depth >= depth_limit)
		{
			IDNode current_node = new IDNode(parent);
			return current_node.getHeuristicScore();
		}

		if (moves_list != null && parent.spacesLeft > 0) // There are possible moves to make
		{			
			// need to go through each move.
			for(Point move: moves_list)
			{
				// compute the MIN of each move.
				potential_move = move;
				potential_score = generateMIN(parent.clone().placePiece(move, player), start_time, depth_limit);
				if (potential_score > max)
				{
					max = potential_score;
					best_max = potential_move;
				}
			}			
		} 
		// need to check if moves is empty and there are stil spaces left
		else if (moves_list == null && parent.spacesLeft > 0)
		{
			// search random for free spot?
			// search random for free spot?
			Point new_move = new Point();
			boolean found = false;
			for (int x = 0; x < parent.width; x++)
			{
				for (int y = 0; y < parent.height; y++)
				{
					if (parent.getSpace(x, y) == 0)
					{
						new_move.x = x;
						new_move.y = y;
						found = true;
						break;
					}
				}
				if (found)
				{
					break;
				}
			}
			max = generateMAX(parent.clone().placePiece(new_move, parent.getSpace(parent.lastMove)), start_time, depth_limit);
						
		}
		else // there are no more spaces left
		{
			IDNode current_node = new IDNode(parent);
			return current_node.getHeuristicScore();
		}
		return max; // best max score for the best move

	}
	
	ArrayList<Point> getPotentialMoves(BoardModel state){	
		Point lastMove = state.getLastMove();
		int a = lastMove.x;
		int b = lastMove.y;
	
		
		ArrayList<Point> potentialMoves = new ArrayList<Point>();
	
		for (int x = a - 1; x < a + 2; x++)
		{
			for (int y = b - 1; y < b + 2; y++)
			{
				if (x < 0 || x > state.getWidth() - 1 || y < 0 || y > state.getHeight() - 1)
				{
					continue; // these are illegal values/exceeding boundaries, so we will ignore them.
				}
				else if (x == a && y == b)
				{
					continue; //ignore the spot that was/has been last placed.
				}
				else if (state.getSpace(x, y) == 0) //If it's 0 it is a potential move
				{
					if(state.gravity && state.getSpace(x,y-1) != 0 ){//gravity on and there is a move below the move we want
						potentialMoves.add(new Point(x,y));
					}
					else if(!state.gravity){//gravity off so we don't need to check for moves below the move we want
						potentialMoves.add(new Point(x,y));
					}
				}
			}	
		}
		if(potentialMoves.size() == 0){
			return null;
		}
		return potentialMoves;
	}

	@Override
	public Point getMove(BoardModel state) 
	{

		int depth = 5;
		float max = -99999;
		float potential;
		Point best_move = null;
		long start_time = System.nanoTime();
		
		
		if (state.spacesLeft == state.width * state.height)
		{
			Point first_move = new Point();
			first_move.x = state.width/2;
			first_move.y = state.height/2;
			return first_move;
		}
		
		// generate moves
		ArrayList<Point> moves_list = getPotentialMoves(state);
		
		byte player = state.getSpace(state.lastMove);
		byte enemy = (byte) (player == 1? 2 : 1);
		
		int depth_limit = 0;
		
		if (moves_list == null)
		{
			moves_list = new ArrayList<Point>();
			boolean found = false;
			for (int x = 0; x < state.width; x++)
			{
				for (int y = 0; y < state.height; y++)
				{
					if (state.getSpace(x, y) == 0)
					{
						Point new_move = new Point();
						new_move.x = x;
						new_move.y = y;
						moves_list.add(new_move);
						if (x + 1 >= state.width)
						{
							found = true;
							break;
						}
						x = x + 1;
						y = 0;	
					}
				}
				if (found)
				{
					break;
				}
			}
			//max = generateMAX(state.clone().placePiece(new_move, state.getSpace(state.lastMove)), start_time, depth_limit);
		}

		//System.out.println("current: " + System.currentTimeMillis() + "\n turn: " + TURN_TIME + "\n start: " + start_time);
		while (System.nanoTime() - TURN_TIME < start_time)
		{
			for (Point move: moves_list)
			{
				if (state.clone().placePiece(move, player).winner() == player) //check to see if you win.
				{
					return move;
				}
				else if (state.clone().placePiece(move, enemy).winner() == enemy) // check if you can block enemy winning move
				{
					return move;
				}
				current_depth = 0;
				potential = generateMAX(state.clone().placePiece(move, player), start_time, depth_limit);
				if (potential > max)
				{
					max = potential;
					best_move = move;
					
				}
			}
			depth_limit++;
		}
		return best_move;
	}

	@Override
	public Point getMove(BoardModel state, final int deadline)
	{

		return getMove(state);
	}
	
}
