package chess;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;


/**
 * Represents the Chess game
 * 
 * @author Benjamin Lee
 * @author Christopher Shen
 * 
 */
public class Chess {
	
	/**
	 * A boolean that represents if white's last move was an En passant
	 */
	public static boolean whiteEnPassant = false;
	
	/**
	 * A boolean that represents if black's last move was an En passant 
	 */
	public static boolean blackEnPassant = false;
	
	/**
	 * A Piece that represents white's most recent move
	 */
	public Piece whiteMostRecent;
	
	/**
	 * A piece that represents black's most recent move
	 */
	public Piece blackMostRecent;
	
	/**
	 * An int that represents the rank of the white pawn if it has just En passanted
	 */
	public static int whiteEnPassantRank = 0;
	
	/**
	 * An int that represents the rank of the black pawn if it has just En passanted
	 */
	public static int blackEnPassantRank = 0;
	
	/**
	 * An int that represents the file of the white pawn if it has just En passanted
	 */
	public static int whiteEnPassantFile = 0;
	
	/**
	 * An int that represents the file of the black pawn if it has just En passanted
	 */
	public static int blackEnPassantFile = 0;
	
	/**
	 * A Boolean that represents if the white King is in check
	 */
	public static boolean whiteChecked = false;
	
	/***
	 * A Boolean that represents if the black King is in check
	 */
	public static boolean blackChecked = false;
	
	/**
	 * The white King Piece
	 */
	public static King whiteKing;
	
	/**
	 * The black King Piece
	 */
	public static King blackKing;
	
	/**
	 * 2D array of booleans that represents the current threats of white's Pieces
	 */
	public static boolean[][] blackThreats;
	
	/**
	 * 2d array of booleans that represents the current threats of black's Pieces
	 */
	public static boolean[][] whiteThreats;

	/**
	 * Our main method that runs the Chess game
	 * @param args A String array representing the command line arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Creates the board and initialize variables
		Piece[][] board = new Piece[8][8];
		blackThreats = new boolean[8][8];
		whiteThreats = new boolean[8][8];
		
		
		
		
		boolean gameOver = false;
		char turn = 'w';
		setup(board);
		Scanner scanner = new Scanner(System.in);
		String input;
		String[] inputArr;
		String landingTile;
		String movingTile;
		String thirdInput;	
		int movingFile;
		int movingRank;
		int landingFile;
		int landingRank;
		int fileNumber;
		
		
		blackKing = (King) board[7][4];
		whiteKing = (King) board[0][4];

		printBoard(board);
		//CHESS GAME
		while(!gameOver) {
			
			
			if(turn == 'w') {
				System.out.print("White's move: ");
			}
			else {
				System.out.print("Black's move: ");
			}
			
		    input = scanner.nextLine();  // Read user input
		    inputArr = input.split(" ");
		   

		    if (inputArr[0].toLowerCase().equals("resign")) {
		    	if (turn == 'w') {
		    		System.out.println("\nBlack Wins\n");
		    	}
		    	else {
		    		System.out.println("\nWhite Wins\n");
		    	}
		    	break;
		    }
		    
		    
		    movingTile = inputArr[0];
		    landingTile = inputArr[1];
		    
		    movingFile = movingTile.charAt(0) - 'a';
		    movingRank = Character.getNumericValue(movingTile.charAt(1)) - 1;
		    landingFile = landingTile.charAt(0) - 'a';
		    landingRank = Character.getNumericValue(landingTile.charAt(1)) - 1;
		    
		    
			if(!validMove(board, movingFile, movingRank, landingFile, landingRank, turn, whiteThreats, blackThreats)) {
		    	System.out.println("\nIllegal move, try again\n");
		    	continue;
		    }
			
			if(inputArr.length == 3 && inputArr[2].equals("draw?")) {
				System.out.println("\ndraw\n");
				break;
			}

		   
		    
		    board[landingRank][landingFile] = board[movingRank][movingFile];
		    board[movingRank][movingFile] = null;
		    
		    Piece landingPiece = getPiece(board, landingFile, landingRank);
		  
		    if(landingPiece instanceof Pawn && Math.abs(landingRank - movingRank) == 2){
		    	
				if(landingPiece.getColor() == 'b'){
					blackEnPassant = true;
					blackEnPassantFile = landingFile;
					blackEnPassantRank = landingRank;
				}
				else{
					whiteEnPassant = true;
					whiteEnPassantFile = landingFile;
					whiteEnPassantRank = landingRank;
				}
			}
			else{
				if(landingPiece.getColor() == 'b'){
					blackEnPassant = false;
				}
				else{
					whiteEnPassant = false;
				}
			}


		    landingPiece.setFileNumber(landingFile);
		    landingPiece.setRankNumber(landingRank);
		   
		     
		    findThreats(board, whiteThreats, blackThreats);
		    	
		    if(landingPiece != null && ((landingPiece.getColor() == 'w' && landingRank == 7 && landingPiece instanceof Pawn) ||
		    		(landingPiece.getColor() == 'b' && landingRank == 0 && landingPiece.getPieceName().charAt(1) == 'P'))){
					promotePawn(board, landingFile, landingRank, landingPiece, inputArr);
					findThreats(board, whiteThreats, blackThreats);
		    }
		    
		    // find threats
		    
		    
		    
		    if(blackThreats[whiteKing.getRankNumber()][whiteKing.getFileNumber()]) {
		    	whiteChecked = true;
		    }
		    else {
		    	whiteChecked = false;
		    }
		    if(whiteThreats[blackKing.getRankNumber()][blackKing.getFileNumber()]) {
		    	blackChecked = true;
		    }
		    else {
		    	blackChecked = false;
		    }
		    
		    
		    if(checkmate(board, turn)){
		    	printBoard(board);
		    	System.out.println("Checkmate\n");
				gameOver = true;
				if (turn == 'w'){
					System.out.println("White wins");
				}
				else {
					System.out.println("Black wins");
				}
				
				
				
				break;
			}
		    
		    if(whiteChecked || blackChecked) {
		    	System.out.println("\nCheck");
		    }
		    
		    
		    
		    
		    //Switch turns
		    if(turn == 'w') 
		    	turn = 'b';
		    else
		    	turn = 'w';
			//do stuff
		    
		    printBoard(board);
		}
		scanner.close();
		
	}
	
	/**
	 * Checks whether or not a King is being checkmated
	 * @param board A 2D array of Pieces representing the chess board
	 * @param turn A char representing which color's turn it is
	 * @return returns false if the opponent's piece is not in check, otherwise returns true
	 */

	static boolean checkmate(Piece[][] board, char turn) {
		if(turn == 'b') { // black is checkmating white
			if(!whiteChecked) {
				return false;
			}

			for(int tempRank = 0; tempRank < 8; tempRank++) {
				for(int tempFile = 0; tempFile < 8 ; tempFile++) {
					if(board[tempRank][tempFile] == null) {
						continue;
					}
					if(board[tempRank][tempFile].getColor() == 'w') {
						//check every possible move of this piece
						
						
						ArrayList<List<Integer>> list = board[tempRank][tempFile].possibleMoves(board, whiteThreats, tempFile, tempRank);

						for(int k = 0; k < list.size(); k++) {
							
							//System.out.println(tempRank + " " + tempFile + " | " + list.get(k).get(0) + " " + list.get(k).get(1));
							if(checkCheck(board, list.get(k).get(0), list.get(k).get(1), tempRank, tempFile, board[list.get(k).get(0)][list.get(k).get(1)], board[tempRank][tempFile], 'w')) {
								
								return false;
							}
							
							
							
						}
						
						
					}
					
				}
			}
			
			
		}
		else {
			if(!blackChecked) {
				return false;
			}
			for(int tempRank = 0; tempRank < 8; tempRank++) {
				for(int tempFile = 0; tempFile < 8 ; tempFile++) {
					if(board[tempRank][tempFile] == null) {
						continue;
					}
					if(board[tempRank][tempFile].getColor() == 'b') {
						//check every possible move of this piece
						
						
						ArrayList<List<Integer>> list = board[tempRank][tempFile].possibleMoves(board, blackThreats, tempFile, tempRank);
						
						
						for(int k = 0; k < list.size(); k++) {
							
							//System.out.println(tempRank + " " + tempFile + " | " + list.get(k).get(0) + " " + list.get(k).get(1));
							if(checkCheck(board, list.get(k).get(0), list.get(k).get(1), tempRank, tempFile, board[list.get(k).get(0)][list.get(k).get(1)], board[tempRank][tempFile], 'b')) {
								
								return false;
							}
							
							
							
						}
						
						
					}
					
				}
			}
			
			
		}
		
		return true;
	}

	/**
	 * Fills the whiteThreats and blackThreats arrays with the current squares that are being threatened
	 * @param board A 2D array of Pieces representing the chess board
	 * @param whiteThreats A 2D array of booleans that contains the squares that are being threatened by white Pieces
	 * @param blackThreats A 2D array of booleans that contains the squares that are being threatened by black Pieces
	 */
	static void findThreats(Piece[][] board, boolean[][] whiteThreats, boolean[][] blackThreats) {
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				blackThreats[i][j] = false;
				whiteThreats[i][j] = false;
			}
		}
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(getPiece(board,i,j) == null) {
					continue;
				}
				else if(getPiece(board,i,j).getColor() == 'b') { //if black
					getPiece(board,i,j).getThreats(board, blackThreats, i, j);
				}
				else { //if white
					getPiece(board,i,j).getThreats(board, whiteThreats, i, j);
				}

			}
		}
	}
	
	/**
	 * Promotes the pawn to another piece
	 * @param board A 2D array of Pieces representing the chess board
	 * @param landingFile An int representing the file number of the destination of the piece that is moving
	 * @param landingRank An int representing the rank number of the destination of the piece that is moving
	 * @param landingPiece A Piece representing the original piece at the destination
	 * @param inputArr A String array that represents the input from the user
	 */
	static void promotePawn(Piece[][] board, int landingFile, int landingRank, Piece landingPiece, String[] inputArr) {
		
    	if(inputArr.length == 2) { // not specified, make queen
    		board[landingRank][landingFile] = new Queen(landingPiece.getColor(),landingRank, landingFile, landingPiece.getColor() + "Q");
    	}
    	else { // specified piece, inputArr[2] would be piece specified
    		String promotePiece = inputArr[2];
    		if (promotePiece.equals("N")){
    			board[landingRank][landingFile] = new Knight(landingPiece.getColor(),landingRank, landingFile, landingPiece.getColor() + "N");
				
			}
			else if (promotePiece.equals("Q")){
				board[landingRank][landingFile] = new Queen(landingPiece.getColor(),landingRank, landingFile, landingPiece.getColor() + "Q");
				
			}
			else if (promotePiece.equals("B")){
				board[landingRank][landingFile] = new Bishop(landingPiece.getColor(),landingRank, landingFile, landingPiece.getColor() + "B");
				
			}
			else if (promotePiece.equals("R")){
				board[landingRank][landingFile] = new Rook(landingPiece.getColor(),landingRank, landingFile, landingPiece.getColor() + "R");
				
			}
			else{
				board[landingRank][landingFile] = new Queen(landingPiece.getColor(),landingRank, landingFile, landingPiece.getColor() + "Q");
    		}
    		
    	}
	}
	
	/**
	 * Sets up the inital board with the default pieces
	 * @param board A 2D array of Pieces representing the chess board 
	 */
	static void setup(Piece[][] board) {
		//Pawns
		for(int i = 0; i <= 7; i++) {

			board[1][i] = new Pawn('w', i, 1, "wP");
			board[6][i] = new Pawn('b', i, 6, "bP");
			


		}

		//White Pieces
		board[0][0] = new Rook('w', 0, 0, "wR");
		board[0][1] = new Knight('w', 1, 0, "wN");
		board[0][2] = new Bishop('w', 2, 0, "wB");
		board[0][3] = new Queen('w', 3, 0, "wQ");
		board[0][4] = new King('w', 4, 0, "wK");
		board[0][5] = new Bishop('w', 5, 0, "wB");
		board[0][6] = new Knight('w', 6, 0, "wN");
		board[0][7] = new Rook('w', 7, 0, "wR");
		
		//Black Pieces
		board[7][0] = new Rook('b', 0, 7, "bR");
		board[7][1] = new Knight('b', 1, 7, "bN");
		board[7][2] = new Bishop('b', 2, 7, "bB");
		board[7][3] = new Queen('b', 3, 7, "bQ");
		board[7][4] = new King('b', 4, 7, "bK");
		board[7][5] = new Bishop('b', 5, 7, "bB");
		board[7][6] = new Knight('b', 6, 7, "bN");
		board[7][7] = new Rook('b', 7, 7, "bR");

	}
	
	/**
	 * Prints out the current Chess board
	 * @param board A 2D array of Pieces representing the chess board
	 */
	static void printBoard(Piece[][] board) {
		System.out.println();
		for(int rank = 7; rank >= 0; rank--) {
			for(int file = 0; file < 8; file++) {
				if(board[rank][file] == null) { // No Piece
					if((rank+file)%2 == 1) // Print Black Tiles
						System.out.print("## ");
					
					else
						System.out.print("   ");
				}
				else { 
					System.out.print(board[rank][file].getPieceName() + " ");
				}
				
			}
			System.out.print(rank+1 + "\n");
		}
		for(int i = 0; i < 8; i++) {
			System.out.print(" " + (char) ('a'+i) + " ");
		}
		System.out.println("\n");
	}
	
	
	/**
	 * Checks whether or not the attempted move is valid
	 * @param board A 2D array of Pieces representing the chess board
	 * @param movingFile An int representing the file number of the starting position of the piece that is moving
	 * @param movingRank An int representing the rank number of the starting position of the piece that is moving
	 * @param landingFile An int representing the file number of the destination of the piece that is moving
	 * @param landingRank  An int representing the rank number of the destination of the piece that is moving
	 * @param turn A char representing which color's turn it is
	 * @param whiteThreats A 2D array of booleans that contains the squares that are being threatened by white Pieces
	 * @param blackThreats A 2D array of booleans that contains the squares that are being threatened by black Pieces
	 * @return Returns true if the move is valid
	 */
	static boolean validMove(Piece[][] board, int movingFile, int movingRank, int landingFile, int landingRank, char turn, boolean[][] whiteThreats, boolean[][] blackThreats) {
		// CHECK CONDITIONS 
		
		if (movingRank >= 8 || landingRank >= 8 || (movingRank < 0 || landingRank < 0)) {
			return false;
		}
		
		if (movingFile >= 8 || landingFile >= 8 || (movingFile < 0 || landingFile < 0)) {
			return false;
		}
		
		
		Piece movingPiece = getPiece(board, movingFile, movingRank);
		Piece landingPiece = getPiece(board, landingFile, landingRank);
		
		
		if(movingPiece == null) { // No piece to move
			return false;
		}
		if(movingPiece.getColor() != turn) { // Not your turn
			return false;
		}
		if(landingPiece != null) { // NO PIECE AT LANDING TEST CASES
			if(movingPiece.getColor() == landingPiece.getColor()){
				return false;
			}
		}
		if(!movingPiece.validMovement(board, movingFile, movingRank, landingFile, landingRank, turn)) {
			return false;
		}
		
		if (!checkCheck(board, landingRank, landingFile, movingRank, movingFile, landingPiece, movingPiece, turn)){
			return false;
		}

		


		return true;
	}
	
	
	/**
	 * Checks whether or not the player is in check after performing the move
	 * @param board A 2D array of Pieces representing the chess board
	 * @param landingRank An int representing the rank number of the destination of the piece that is moving
	 * @param landingFile An int representing the file number of the destination of the piece that is moving
	 * @param movingRank An int representing the rank number of the starting position of the piece that is moving
	 * @param movingFile An int represnting the file number of the starting position of the piece that is moving
	 * @param landingPiece A Piece representing the original piece at the destination 
	 * @param movingPiece A Piece representing the original piece at the starting position 
	 * @param turn A char representing which color's turn it is
	 * @return Returns true if the current player is not in check after performing the move
	 */
	public static boolean checkCheck(Piece[][] board, int landingRank, int landingFile, int movingRank, int movingFile, Piece landingPiece, Piece movingPiece, char turn) {

		Piece temp = null;
		if(landingPiece != null) {
			temp = landingPiece;
		}
		
		board[landingRank][landingFile] = movingPiece;
		
		board[movingRank][movingFile] = null;
		
		movingPiece.setFileNumber(landingFile);
		movingPiece.setRankNumber(landingRank);
		
		findThreats(board, whiteThreats, blackThreats);
		
		if(turn == 'w' && blackThreats[whiteKing.getRankNumber()][whiteKing.getFileNumber()]) {		
			board[movingRank][movingFile] = board[landingRank][landingFile];
			board[landingRank][landingFile] = temp;
			
			movingPiece.setFileNumber(movingFile);
			movingPiece.setRankNumber(movingRank);
			
			return false;
			
		}
		
		else if (turn == 'b' && whiteThreats[blackKing.getRankNumber()][blackKing.getFileNumber()]){
			
			board[movingRank][movingFile] = board[landingRank][landingFile];
			board[landingRank][landingFile] = temp;
			
			movingPiece.setFileNumber(movingFile);
			movingPiece.setRankNumber(movingRank);
			
			return false;

		}
		
		movingPiece.setFileNumber(movingFile);
		movingPiece.setRankNumber(movingRank);
		
		board[movingRank][movingFile] = board[landingRank][landingFile];
		board[landingRank][landingFile] = landingPiece;
		
		return true;
	}
	
	
	/**
	 * Returns the piece at the specified file and rank
	 * @param board A 2D array of Pieces representing the chess board
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 * @return Piece at the specified file and rank
	 */
	public static Piece getPiece(Piece[][] board, int file, int rank) {
		
		return board[rank][file];
	}
	
}
