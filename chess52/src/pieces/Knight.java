package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Represents the Knight Piece
 * 
 * @author Benjamin Lee
 * @author Christopher Shen
 * 
 */
public class Knight extends Piece{
	
	/**
	 * Creates a King Piece and sets its fields
	 * @param color A char representing the color of the piece
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 * @param pieceName A String representing the name of the piece 
	 * 
	 */
	public Knight(char color, int file, int rank, String pieceName) {
		super(color, file, rank, pieceName);
	}
	
	
	@Override
	public boolean validMovement(Piece[][] board, int movingFile, int movingRank, int landingFile, int landingRank, char color) {
		// TODO Auto-generated method stu
		
		
		
		int rank = Math.abs(landingRank - movingRank);
		int file = Math.abs(landingFile - movingFile);
		
		if (rank == 2 && file == 1) {
			return true;
		}
		else if (rank == 1 && file == 2) {
			return true;
		}

		
		
		return false;
	}


	@Override
	public void getThreats(Piece[][] board, boolean[][] threats, int file, int rank) {
		
		if(rank + 1 <= 7 && file + 2 <= 7)
			threats[rank + 1][file+2] = true;
		
		if(rank + 2 <= 7 && file + 1 <= 7)
			threats[rank + 2][file+1] = true;
		
		if(rank - 1 >= 0 && file + 2 <= 7)
			threats[rank - 1][file+2] = true;
		
		if(rank -2 >= 0 && file + 1 <= 7)
			threats[rank - 2][file+1] = true;
		
		if(rank + 1 <= 7 && file -2 >= 0)
			threats[rank + 1][file-2] = true;
		
		if(rank + 2 <= 7 && file - 1 >= 0)
			threats[rank + 2][file-1] = true;
		
		if(rank - 1 >= 0 && file - 2 >= 0)
			threats[rank - 1][file-2] = true;
			
		if(rank - 2 >= 0 && file - 1 >= 0)
			threats[rank - 2][file-1] = true;

		
	}




	@Override
	public ArrayList<List<Integer>> possibleMoves(Piece[][] board, boolean[][] threats, int file, int rank) {
		// TODO Auto-generated method stub
		
		ArrayList<List<Integer>> moves = new ArrayList<List<Integer>>();
		
		if(rank + 1 <= 7 && file + 2 <= 7 && (board[rank+1][file+2] == null || board[rank+1][file+2].getColor() != board[rank][file].getColor()))
			moves.add(Arrays.asList(rank+1, file+2));
		
		if(rank + 2 <= 7 && file + 1 <= 7 && (board[rank+2][file+1] == null || board[rank+2][file+1].getColor() != board[rank][file].getColor()))
			moves.add(Arrays.asList(rank+2, file+1));
		
		if(rank - 1 >= 0 && file + 2 <= 7 && (board[rank-1][file+2] == null || board[rank-1][file+2].getColor() != board[rank][file].getColor()))
			moves.add(Arrays.asList(rank-1, file+2));
		
		if(rank -2 >= 0 && file + 1 <= 7 && (board[rank-2][file+1] == null || board[rank-2][file+1].getColor() != board[rank][file].getColor()))
			moves.add(Arrays.asList(rank-2, file+1));
		
		if(rank + 1 <= 7 && file -2 >= 0 && (board[rank+1][file-2] == null || board[rank+1][file-2].getColor() != board[rank][file].getColor()))
			moves.add(Arrays.asList(rank+1, file-2));
		
		if(rank + 2 <= 7 && file - 1 >= 0 && (board[rank+2][file-1] == null || board[rank+2][file-1].getColor() != board[rank][file].getColor()))
			moves.add(Arrays.asList(rank+2, file-1));
		
		if(rank - 1 >= 0 && file - 2 >= 0 && (board[rank-1][file-2] == null || board[rank-1][file-2].getColor() != board[rank][file].getColor()))
			moves.add(Arrays.asList(rank-1, file-2));
			
		if(rank - 2 >= 0 && file - 1 >= 0 && (board[rank-2][file-1] == null || board[rank-2][file-1].getColor() != board[rank][file].getColor()))
			moves.add(Arrays.asList(rank-2, file-1));
		
		return moves;
	}
	
	
	
}
