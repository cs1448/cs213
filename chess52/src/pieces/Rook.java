package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.Chess;


/**
 * Represents the Rook Piece
 * 
 * @author Benjamin Lee
 * @author Christopher Shen
 * 
 */
public class Rook extends Piece{
	
	
	 /**
	 * Creates a King Piece and sets its fields
	 * @param color A char representing the color of the piece
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 * @param pieceName A String representing the name of the piece 
	 * 
	 */
	public Rook(char color, int file, int rank, String pieceName) {
		super(color, file, rank, pieceName);
		this.setHasMoved(false);
	}
	
	
	@Override
	public boolean validMovement(Piece[][] board, int movingFile, int movingRank, int landingFile, int landingRank, char color) {
		// TODO Auto-generated method stub
		
		if(!((movingFile == landingFile) || (movingRank == landingRank)) ) {
			return false;
		}
		
		if(movingRank == landingRank) {
			int low = 0, hi = 0;
			if(movingFile < landingFile) {
				low = movingFile;
				hi = landingFile;
			}
			else {
				low = landingFile;
				hi = movingFile;
			}
			
			for(int i = low+1; i < hi-1; i++) {
				if(Chess.getPiece(board, i, landingRank) != null) {
					return false;
				}
			}
		}
		else { //movingFile == movingRank
			int low = Math.min(landingRank, movingRank), hi = Math.max(landingRank, movingRank);
			
			for(int i = low+1; i < hi; i++) {
				if(Chess.getPiece(board, landingFile, i) != null) {
					return false;
				}
			}

		}
		
		this.setHasMoved(true);

			
		
		return true;
	}


	@Override
	public void getThreats(Piece[][] board, boolean[][] threats, int file, int rank) {
		// TODO Auto-generated method stub
		
		for(int i = file + 1; i < 8; i++){
			if (board[rank][i] == null){
				threats[rank][i] = true;
			}
			else {
				threats[rank][i] = true;
				break;
			}
			
		}
		for(int i = file - 1; i >= 0; i--){
			if (board[rank][i] == null){
				threats[rank][i] = true;
			}
			else {
				threats[rank][i] = true;
				break;
			}
		}
		for(int i = rank + 1; i < 8; i++){
			if (board[i][file] == null){
				threats[i][file] = true;
			}
			else {
				threats[i][file] = true;
				break;
			}
		}
		for(int i = rank - 1; i >= 0; i--){
			if (board[i][file] == null){
				threats[i][file] = true;
			}
			else {
				threats[i][file] = true;
				break;
			}
		}
		
	}


	@Override
	public ArrayList<List<Integer>> possibleMoves(Piece[][] board, boolean[][] threats, int file, int rank) {
		// TODO Auto-generated method stub
		ArrayList<List<Integer>> moves = new ArrayList<List<Integer>>();
		
		for(int i = file + 1; i < 8; i++){
			if (board[rank][i] == null){
				moves.add(Arrays.asList(rank, i));

			}
			else {
				if(board[rank][i].getColor() != board[rank][file].getColor())
					moves.add(Arrays.asList(rank, i));
				break;
			}
			
		}
		for(int i = file - 1; i >= 0; i--){
			if (board[rank][i] == null){
				moves.add(Arrays.asList(rank, i));
			}
			else {
				if(board[rank][i].getColor() != board[rank][file].getColor())
					moves.add(Arrays.asList(rank, i));
				break;
			}
		}
		for(int i = rank + 1; i < 8; i++){
			if (board[i][file] == null){
				moves.add(Arrays.asList(i, file));
			}
			else {
				if(board[i][file].getColor() != board[rank][file].getColor())
					moves.add(Arrays.asList(i, file));
				break;
			}
		}
		for(int i = rank - 1; i >= 0; i--){
			if (board[i][file] == null){
				moves.add(Arrays.asList(i, file));
			}
			else {
				if(board[i][file].getColor() != board[rank][file].getColor())
					moves.add(Arrays.asList(i, file));
				break;
			}
		}
		
		
		return moves;
	}
	
	
	

}
