package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.Chess;


/**
 * Represents the Queen Piece
 * 
 * @author Benjamin Lee
 * @author Christopher Shen
 * 
 */
public class Queen extends Piece{
  
  
	 /**
	 * Creates a King Piece and sets its fields
	 * @param color A char representing the color of the piece
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 * @param pieceName A String representing the name of the piece 
	 * 
	 */
	public Queen(char color, int file, int rank, String pieceName) {
		super(color, file, rank, pieceName);
	}
	
	
	@Override
	public boolean validMovement(Piece[][] board, int movingFile, int movingRank, int landingFile, int landingRank, char color) {
		// TODO Auto-generated method stub
		if((movingFile == landingFile) || (movingRank == landingRank)){
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
			
			return true;
		}
		
		
		
		
		
		else if (Math.abs(landingRank - movingRank) == Math.abs(landingFile - movingFile)){
			int moveRank= -1;
			if(movingRank < landingRank){
				moveRank = 1;
			}
			
			int moveFile = -1;
			if(movingFile < landingFile){
				moveFile = 1;
			}
			
			for (int i = movingRank+moveRank, j = movingFile+moveFile; !(i == landingRank && j == landingFile); i+= moveRank, j+= moveFile) {//for pieces moving up
				if (board[i][j] != null) {
					return false;
				}
			}
			
			return true;
		}
		
		//Not bishop or rook move
		return false;
		
		
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
		
		for(int i = file + 1, j = rank + 1 ; !(i > 7 || j > 7); i++, j++){
			if (board[j][i] == null){
				threats[j][i] = true;
			}
			else {
				threats[j][i] = true;
				break;
			}
			
		}
		for(int i = file - 1, j = rank + 1; !(i <0 || j > 7); i--,j++){

			if (board[j][i] == null){
				threats[j][i] = true;
			}
			else {
				threats[j][i] = true;
				break;
			}
		}
		for(int i = file + 1, j = rank - 1; !(i > 7 || j <0); i++,j--){
			if (board[j][i] == null){
				threats[j][i] = true;
			}
			else {
				threats[j][i] = true;
				break;
			}
		}
		for(int i = file - 1, j = rank - 1; !(i < 0 || j < 0); i--,j--){
			if (board[j][i] == null){
				threats[j][i] = true;
			}
			else {
				threats[j][i] = true;
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
		
		//bishop
		for(int i = file + 1, j = rank + 1 ; !(i > 7 || j > 7); i++, j++){
			if (board[j][i] == null){
				moves.add(Arrays.asList(j, i));
			}
			else {
				if(board[j][i].getColor() != board[rank][file].getColor())
					moves.add(Arrays.asList(j, i));
				break;
			}
			
		}
		for(int i = file - 1, j = rank + 1; !(i <0 || j > 7); i--,j++){

			if (board[j][i] == null){
				moves.add(Arrays.asList(j, i));
			}
			else {
				if(board[j][i].getColor() != board[rank][file].getColor())
					moves.add(Arrays.asList(j, i));
				break;
			}
		}
		for(int i = file + 1, j = rank - 1; !(i > 7 || j <0); i++,j--){
			if (board[j][i] == null){
				moves.add(Arrays.asList(j, i));
			}
			else {
				if(board[j][i].getColor() != board[rank][file].getColor())
					moves.add(Arrays.asList(j, i));
				break;
			}
		}
		for(int i = file - 1, j = rank - 1; !(i < 0 || j < 0); i--,j--){
			if (board[j][i] == null){
				moves.add(Arrays.asList(j, i));
			}
			else {
				if(board[j][i].getColor() != board[rank][file].getColor())
					moves.add(Arrays.asList(j, i));
				break;
			}
		}
		
		
		
		// TODO Auto-generated method stub
		return moves;
	}
	
	

}
