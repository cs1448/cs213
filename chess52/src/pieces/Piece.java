package pieces;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents class Piece, the abstract superclass for all of the chess pieces 
 * 
 * @author Benjamin Lee
 * @author Christopher Shen
 * 
 */
public abstract class Piece {

	/**
	 * A boolean representing whether the piece has moved or not
	 */
	private boolean hasMoved;
	
	/**
	 * A char representing the color of the piece
	 */
	private char color;
	
	/**
	 * A String representing the name of the piece
	 */
	private String pieceName;
	
	/**
	 * An int representing the file number of the piece 
	 */
	private int fileNumber;
	
	/**
	 * An int representing the rank number of the piece
	 */
	private int rankNumber;
	
	/**
	 * 
	 * @param board A 2D array of Pieces representing the entire chess board
	 * @param movingFile An int representing the file number of the starting position of the piece that is moving
	 * @param movingRank An int representing the rank number of the starting position of the piece that is moving
	 * @param landingFile An int representing the file number of the destination of the piece that is moving
	 * @param landingRank  An int representing the rank number of the destination of the piece that is moving
	 * @param color A char representing which color is moving a piece
	 * @return Returns true if the attempted move is valid
	 */
	public abstract boolean validMovement(Piece[][] board, int movingFile, int movingRank, int landingFile, int landingRank, char color);
	/**
	 * 
	 * @param board A 2D array of Pieces representing the entire chess board
	 * @param threats A 2D array of booleans representing threats to the opponenets pieces
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 */
	public abstract void getThreats(Piece[][] board, boolean[][] threats, int file, int rank);
	
	/**
	 * 
	 * @param board A 2D array of Pieces representing the entire chess board
	 * @param threats A 2D array of booleans representing threats to the opponenets pieces
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 * @return Returns an Arraylist of all the possible moves that the specified piece can move to
	 */
	public abstract ArrayList<List<Integer>> possibleMoves(Piece[][] board, boolean[][] threats, int file, int rank);
	
	
	/**
	 * 
	 * @param color A char representing which color is moving a piece
	 * @param file An int representing the file number of the piece
	 * @param rank An int representing the rank number of the piece
	 * @param pieceName A String representing the name of the piece 
	 * 
	 */
	public Piece(char color, int file, int rank, String pieceName) {
    			
			this.setPieceName(pieceName);
			this.setColor(color);
			setFileNumber(file);
			setRankNumber(rank);
	}
	
	/**
	 * 
	 * @return Returns the current rank number of the piece
	 */
	public int getRankNumber() {
		return rankNumber;
	}
	
	/**
	 *Sets the rank number of the piece
	 * @param rankNumber Rank number that is being set
	 */
	public void setRankNumber(int rankNumber) {
		this.rankNumber = rankNumber;
	}
	
	/**
	 * @return Returns the current file number of the piece
	 */
	public int getFileNumber() {
		return fileNumber;
	}
	
	/**
	 * Sets the file number of the piece
	 * @param fileNumber File number that is being set
	 */
	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}
	
	/**
	 * @return Returns the current name of the piece
	 */
	public String getPieceName() {
		return pieceName;
	}
	
	/**
	 * Sets the name of the piece
	 * @param pieceName Name of the piece that is being set
	 */
	public void setPieceName(String pieceName) {
		this.pieceName = pieceName;
	}
	
	/**
	 * @return Returns the current color of the piece
	 */
	public char getColor() {
		return color;
	}
	
	/**
	 * Sets the color of the piece
	 * @param color Color of the piece that is being set
	 */
	public void setColor(char color) {
		this.color = color;
	}
	
	/**
	 * @return Returns whether the piece has moved or not
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/**
	 * Sets the hasMoved boolean
	 * @param hasMoved the value that we are changing hasMoved to
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	
	
}
