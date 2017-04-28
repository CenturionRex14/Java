import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	private char[][] board;
	private Point startingPoint;
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";
	private static IUDoubleLinkedList<Character> allowedCharList = new IUDoubleLinkedList<Character>();

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		//TODO: parse the given file to populate the char[][]
		// throw FileNotFoundException if Scanner cannot read the file
		// throw InvalidFileFormatException if any formatting or parsing issues are encountered
		Scanner fileScan = new Scanner(new File(filename));
		ROWS = Integer.parseInt(fileScan.next());
		COLS = Integer.parseInt(fileScan.next());
		/*
		 * Builds list containing valid characters
		 */
		allowedCharList.add('O');
		allowedCharList.add('X');
		allowedCharList.add('T');
		allowedCharList.add('1');
		allowedCharList.add('2');
		/*
		 * Builds list containing invalid characters i.e. repeated start or end points
		 */
		IUDoubleLinkedList<Character> usedCharList = new IUDoubleLinkedList<Character>();
		board = new char[ROWS][COLS];
		int j = 0;
		int i = 0;
		int realRowCount = 0;
		while(fileScan.hasNext()){
			String nextLine = fileScan.nextLine();
			Scanner lineScan = new Scanner(nextLine);
			realRowCount++; //For checking dimensions
			while(lineScan.hasNext()) {
				String nextToken = lineScan.next();
				char nextChar = nextToken.charAt(0);
				/*
				 * Checks if char is valid
				 */
				if (!allowedCharList.contains(nextChar)) {
					lineScan.close();
					throw new InvalidFileFormatException(null);
				}
				/*
				 * Checks if start or end points are repeated
				 */
				if (nextChar == '1') {
					int y = j;
					int x = i;
					if (usedCharList.contains(nextChar)) {
						throw new InvalidFileFormatException(null);
					} else {
						usedCharList.add(nextChar);
						/*
						 * designates startPoint
						 */
						if (y < COLS) {
							startingPoint = new Point(x, y);
						} else {
							y = 0;
							x++;
							startingPoint = new Point(x, y);
						}
					}
				} else if (nextChar == '2') {
					int y = j;
					int x = i;
					if (usedCharList.contains(nextChar)) {
						throw new InvalidFileFormatException(null);
					} else {
						usedCharList.add(nextChar);
						/*
						 * designates endingPoint
						 */
						if (y < COLS) {
							endingPoint = new Point(x, y);
						} else {
							y = 0;
							x++;
							endingPoint = new Point(x, y);
						}
					}
				}
				if (j < COLS) {
					board[i][j] = nextChar;
					j++;
				} else {
					j = 0;
					i++;
					board[i][j] = nextChar;
					j++;
				} 
			}
			lineScan.close();
		}
		/*
		 * Checks if given dimensions match actual dimensions
		 * TODO ensure rows and cols are as specified (don't know how)
		 */
		if(j != COLS || i != ROWS - 1 || realRowCount != ROWS + 1){
			fileScan.close();
			throw new InvalidFileFormatException(null);
		}
		/*
		 * Checks if file has both start and end points
		 */
		if(!usedCharList.contains('1') || !usedCharList.contains('2')){
			fileScan.close();
			throw new InvalidFileFormatException(null);
		}
		
		fileScan.close();
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
