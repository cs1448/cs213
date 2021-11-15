package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.Chess;


/**
 * Represents the King Piece
 * 
 * @author Benjamin Lee
 * @author Christopher Shen
 * 
 */
public class King extends Piece{
	
	
	/**
	 * Creates a King Piece and sets its fields
	 * @param color A char representing the color of the piece
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 * @param pieceName A String representing the name of the piece 
	 * 
	 */
	public King(char color, int file, int rank, String pieceName) {
		super(color, file, rank, pieceName);
		setHasMoved(false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validMovement(Piece[][] board, int movingFile, int movingRank, int landingFile, int landingRank, char color) {
    
		// TODO Auto-generated method stub
		
		
		int rank = Math.abs(landingRank - movingRank);
		int file = Math.abs(landingFile - movingFile);
		
		
		if (!(rank <= 1 && file <= 1)){ //
			
			//check castling
			
			if(!getHasMoved() && movingRank == landingRank) {
				if(landingFile == 6) { // castling to the right
					
					if(Chess.getPiece(board, 7, movingRank) != null && Chess.getPiece(board, 7, movingRank) instanceof Rook 
							&& Chess.getPiece(board, 7, movingRank).getHasMoved() == false && Chess.getPiece(board, 5, movingRank) == null
							&& Chess.getPiece(board, 6, movingRank) == null ) {
						
						if((color == 'w' && !(Chess.blackThreats[0][4] || Chess.blackThreats[0][5] || Chess.blackThreats[0][6]))
								|| ( color == 'b' && (Chess.blackThreats[7][4] || Chess.blackThreats[7][5] || Chess.blackThreats[7][6]))){
							board[movingRank][5] = Chess.getPiece(board, 7, movingRank);
							board[movingRank][7] = null;
							setHasMoved(true);
							return true;
						}
						
						
					}
				}
				else if(landingFile == 2) { // castling to the left
					if(Chess.getPiece(board, 0, movingRank) != null && Chess.getPiece(board, 0, movingRank) instanceof Rook 
							&& Chess.getPiece(board, 0, movingRank).getHasMoved() == false && Chess.getPiece(board, 1, movingRank) == null
							&& Chess.getPiece(board, 2, movingRank) == null && Chess.getPiece(board, 3, movingRank) == null) {
						
						if((color == 'w' && !(Chess.blackThreats[0][2] || Chess.blackThreats[0][3] || Chess.blackThreats[0][4]))
								|| ( color == 'b' && (Chess.blackThreats[7][2] || Chess.blackThreats[7][3] || Chess.blackThreats[7][4]))){
							board[movingRank][5] = Chess.getPiece(board, 7, movingRank);
							board[movingRank][7] = null;
							setHasMoved(true);
							return true;
						}
						
						board[movingRank][3] = Chess.getPiece(board, 0, movingRank);
						board[movingRank][0] = null;
						setHasMoved(true);
						return true;
					}
				}
			}
			
			
			return false;
		}
		
		setHasMoved(true);
		
		return true;
		
	}


	@Override
	public void getThreats(Piece[][] board, boolean[][] threats, int file, int rank) {
		// TODO Auto-generated method stub
		
		for(int i = file - 1; i <= file + 1; i++) {
			if(i < 0 || i > 7) {
				continue;
			}
			for(int j = rank - 1; j <= rank + 1; j++) {
				if(j < 0 || j > 7) {
					continue;
				}
				if(i == file && j == rank) {
					continue;
				}
				threats[j][i] = true;
			}
		}
	}

	@Override
	public ArrayList<List<Integer>> possibleMoves(Piece[][] board, boolean[][] threats, int file, int rank) {
		// TODO Auto-generated method stub
		ArrayList<List<Integer>> moves = new ArrayList<List<Integer>>();
		
		for(int i = file - 1; i <= file + 1; i++) {
			if(i < 0 || i > 7) {
				continue;
			}
			for(int j = rank - 1; j <= rank + 1; j++) {
				if(j < 0 || j > 7) {
					continue;
				}
				if(i == file && j == rank) {
					continue;
				}
				if(board[j][i] != null && board[j][i].getColor() == board[rank][file].getColor()) {
					continue;
				}
				moves.add(Arrays.asList(j, i));
			}
		}
		
		return moves;
	}
	
	

}
