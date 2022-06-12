import java.util.*;
public class Board {
	Piece[][] board;
	public Board(int size) {
		board = new Piece[size][size];
	}
	public int getLength() {
		return board.length;
	}
	public Piece[][] getboard(){
		return board;
	}
	public void drop(int column, Piece p) {
		if (column > board.length||column<0) {
			System.out.println("Invalid Request, placing piece in first column. ");
			column = 1;
		}
		
		int x = 0;
		int index = board.length;
		while (x < index) { //going through the whole column to see if there are already pieces
				if (!(board[x][column-1] == null)) {
					index = x;
				}
				x++;
		}
		if (index == board.length) {
			board[index-1][column-1] = p;
		}
		else if (index < board.length) {
			board[index-1][column-1] = p;
		}
		
	}
	
}
