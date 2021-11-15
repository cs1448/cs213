

package pieces;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import chess.Chess;
/**
 * Represents the Pawn Piece
 * 
 * @author Benjamin Lee
 * @author Christopher Shen
 * 
 */
public class Pawn extends Piece{
	boolean movedTwo;
	
	 /**
	 * Creates a King Piece and sets its fields
	 * @param color A char representing the color of the piece
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 * @param pieceName A String representing the name of the piece 
	 * 
	 */
	public Pawn(char color, int file, int rank, String pieceName) {
		super(color, file, rank, pieceName);
		// TODO Auto-generated constructor stub
		movedTwo = false;
	}

	@Override
	public boolean validMovement(Piece[][] board, int movingFile, int movingRank, int landingFile, int landingRank, char color) {
		// TODO Auto-generated method stub
		//file is horizontal
		//rank is vertical
		if(movingFile != landingFile) { // ATTACKING/Moving diagonally
			if (color == 'w' && Chess.blackEnPassant == true && movingRank == 4) {

				if ((landingRank == Chess.blackEnPassantRank + 1) && (landingFile == Chess.blackEnPassantFile)){
					
					board[Chess.blackEnPassantRank][Chess.blackEnPassantFile] = null;
					return true;
				}
				
			}

			if (color == 'b' && Chess.whiteEnPassant == true && movingRank == 3) {
				
				if ((landingRank == Chess.whiteEnPassantRank - 1) && (landingFile == Chess.whiteEnPassantFile )){
					board[Chess.whiteEnPassantRank][Chess.whiteEnPassantFile] = null;
					return true;
				}
				
			}
			


			if (color == 'w' && landingRank != movingRank + 1){
				return false;
			}
			if (color == 'b' && landingRank != movingRank - 1){
				return false;
			}
			if(Math.abs(landingRank - movingRank) != 1 ) {
				return false;
			}
			if(Chess.getPiece(board, landingFile, landingRank) == null || Chess.getPiece(board, landingFile, landingRank).getColor() == color) {
				return false;
			}
		}
		else { // Moving forward
			if(Chess.getPiece(board, landingFile, landingRank) != null) {
				return false;
			}
			
			if (color == 'w'){ //first move for pawn either 2 or 1 spaces
				if(movingRank == 1) {
					if (landingRank != 2 && landingRank != 3){
						

						return false;
					
					}
				}
				else {
					if(landingRank != movingRank + 1) {
						return false;
					}
				}
				

		
			}
			
			if (color == 'b'){ //first move for pawn either 2 or 1 spaces
				if (movingRank == 6){
					if (landingRank != 5 && landingRank != 4){
						
						return false;
					
					}
	
				}
				else {
					if(landingRank != movingRank - 1) {
						return false;
					}
				}
		
			}
			
		}
		
	
		return true;
	}
	
	/**
	 * Returns true if the pawn is on a square where it should promote
	 * @param board A 2D array of Pieces representing the entire chess board
	 * @param movingFile An int representing the file number of the starting position of the piece that is moving
	 * @param movingRank An int representing the rank number of the starting position of the piece that is moving
	 * @param landingFile An int representing the file number of the destination of the piece that is moving
	 * @param landingRank  An int representing the rank number of the destination of the piece that is moving
	 * @param color A char representing which color is moving a piece
	 * @return Returns true if the pawn is allowed to promote, otherwise returns false
	 */
	public boolean promote(Piece[][] board, int movingFile, int movingRank, int landingFile, int landingRank, char color) {
		if (Chess.getPiece(board, landingFile, landingRank).getColor() == 'w'){
			if (Chess.getPiece(board, landingFile, landingRank).getFileNumber() == 7){ //white pawn promotes
					return true;
				
			}
			
		}
		
		if (Chess.getPiece(board, landingFile, landingRank).getColor() == 'b'){
			if (Chess.getPiece(board, landingFile, landingRank).getFileNumber() == 0){ //black pawn promotes
					return true;
				
			}
			
		}
		return false;
	}
	


	@Override
	public void getThreats(Piece[][] board, boolean[][] threats, int file, int rank) {
		
		
		if(Chess.getPiece(board, file, rank).getColor() == 'w') {
			if(rank == 7) {
				return;
			}
			if(file+1 < 8) {
				threats[rank + 1][file + 1] = true;
			}
			if(file-1 >= 0) {
				threats[rank + 1][file - 1] = true;
			}
			
			
		}
		else {
			if(rank == 0) {
				return;
			}
			if(file+1 < 8) {
				threats[rank - 1][file + 1] = true;
			}
			if(file-1 >= 0) {
				threats[rank - 1][file - 1] = true;
			}
		}
		
	}


	@Override
	public ArrayList<List<Integer>> possibleMoves(Piece[][] board, boolean[][] threats, int file, int rank) {
		// TODO Auto-generated method stub
		ArrayList<List<Integer>> moves = new ArrayList<List<Integer>>();
		if(board[rank][file].getColor() == 'w') { // white 
			if(board[rank+1][file] == null) { // in front
				moves.add(Arrays.asList(rank+1, file));
			}
			if(!getHasMoved()) { // 2 spaces in front
				if(board[rank+2][file] == null) {
					moves.add(Arrays.asList(rank+2, file));
				}
			}
			
			if(file > 0 && rank < 7) {
				if(board[rank+1][file-1] != null && board[rank+1][file-1].getColor() == 'b') {
					moves.add(Arrays.asList(rank+1, file-1));
				}
			}
			if(file < 7 && rank < 7) {
				if(board[rank+1][file+1] != null && board[rank+1][file+1].getColor() == 'b') {
					moves.add(Arrays.asList(rank+1, file+1));
				}
			}
			
			if (Chess.blackEnPassant == true && rank == 4) {
				if (Math.abs(file - Chess.blackEnPassantFile) == 1){
					moves.add(Arrays.asList(rank+1, Chess.blackEnPassantFile));
				}
			}
			
		}
		else {
			if(board[rank-1][file] == null) { // in front
				moves.add(Arrays.asList(rank-1, file));
			}
			if(!getHasMoved()) { // 2 spaces in front
				if(board[rank-2][file] == null) {
					moves.add(Arrays.asList(rank-2, file));
				}
			}
			
			if(file > 0 && rank > 0) {
				if(board[rank-1][file-1] != null && board[rank-1][file-1].getColor() == 'w') {
					moves.add(Arrays.asList(rank+1, file-1));
				}
			}
			if(file < 7 && rank > 7) {
				if(board[rank-1][file+1] != null && board[rank-1][file+1].getColor() == 'w') {
					moves.add(Arrays.asList(rank+1, file+1));
				}
			}
			
			if (Chess.whiteEnPassant == true && rank == 3) {
				if (Math.abs(file - Chess.whiteEnPassantFile) == 1){
					moves.add(Arrays.asList(rank-1, Chess.whiteEnPassantFile));
				}
			}
			
		}
		
		
		return moves;
	}
	
	
}
