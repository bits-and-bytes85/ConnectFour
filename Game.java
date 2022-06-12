import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
public class Game {
	public static void main(String[] args) {
		int score1 = 0;
		int score2 = 0;
		
		
		while (score1 < 100 && score2 < 100) {
			int scored = game(score1, score2);
			if (scored == 1) {
				score1 ++;
			} else if (scored == 2) {
				score2 ++;
			}
		}
		
	}
	public static int game(int score1, int score2) {
		String haswon = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("How large do you want your board to be? Pick a number between 4 amd 7");
		int size = scan.nextInt();
		if (size > 7|| size < 4) {
			System.out.println("Invalid Number. Pick a number between 4 and 7");
			size = scan.nextInt();
		}
		DrawingPanel panel = new DrawingPanel(500,500);
		Graphics g = panel.getGraphics();
		g.setFont(new Font("font", Font.BOLD , 20));
		g.setColor(Color.BLACK);
		g.drawString("Player 1: " + score1, 40, 370);
		g.drawString("Player 2: " + score2, 300, 370);
		Board board = new Board(size);
		g.setColor(Color.BLACK);
		for (int j = 0; j < board.getLength();j++) { //building the board structure
			for (int i = 0; i < board.getLength(); i++) {
				if (size <= 4) {
					g.drawRect(80 + j*(size*8), 50 + (i*size*7), 30, 30);
					g.setColor(new Color (150, 200, 240));
					g.fillRect(80 + j*(size*8), 50 + (i*size*7), 30, 30);
					g.setColor(Color.BLACK);
				} else {
					g.drawRect(80 + j*(size*7), 50 + (i*size*6), 30, 30);
					g.setColor(new Color (150, 200, 240));
					g.fillRect(80 + j*(size*7), 50 + (i*size*6), 30, 30);
					g.setColor(Color.BLACK);
				}
				
			}
		}
		while (haswon == null) { //perpetual while loop to continue the game until someone wins
			haswon = checkwin(board, panel, g);
			turn("player 1", board, scan, panel,g, size);
			haswon = checkwin(board, panel, g);
			turn("player 2", board, scan, panel, g, size);
			haswon = checkwin(board, panel, g);
		}
		//returning the player that has won
		if (haswon.equals("player 2")) {
			return 2;
		} else if (haswon.equals("player 1")) {
			return 1;
		} else if (haswon.equals("tie")) {
			return 0;
		}
		return 0;
		
	}
	public static void turn(String player, Board board, Scanner scan, DrawingPanel panel, Graphics g, int size) { //goes through all the methods for a single turn
		if (checkwin(board, panel, g) != null) {
			return;
		}
		System.out.print("Which column does " + player + " want to drop a piece in?");
		int column = scan.nextInt();
		Piece p = new Piece(player);
		//checks if the column is already full and redoing the turn
		if (column > board.getLength() || column < 0) {
			column = board.getLength();
			System.out.println("Invalid Request, choose a different column.");
			turn(player, board, scan, panel, g, size);
		} else {
		int sum = 0;
		for (int i = 0; i < board.getLength(); i++) {
			if ((board.getboard()[i][column-1] != null)) {
				sum++;
			}
		}
		if (sum >= board.getLength()) {
			System.out.println("Invalid Request, choose a different column.");
			turn(player, board, scan, panel, g, size);
		} else if (sum < board.getLength()){
			board.drop(column, p);
			update(board, panel, size);
		}
		}
		
	}
	public static void update(Board board, DrawingPanel panel, int size) { //updating the pieces on the board after each turn
		Graphics g = panel.getGraphics();
		Piece[][] pieces = board.getboard();
		Color c = new Color(10, 40, 220);
		Color d = new Color(40, 80, 150);
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				if (pieces[i][j] != null) {
					if (pieces[i][j].getPlayer().equals("player 2")) {
						g.setColor(c);
						if (size <= 4) {
							g.fillOval(80 + j*(size*8), 50 + (i*size*7), 30, 30);
						} else {
							g.fillOval(80 + j*(size*7), 50 + (i*size*6), 30, 30);

						}
					} else if (pieces[i][j].getPlayer().equals("player 1")) {
						g.setColor(d);
						if (size <= 4) {
							g.fillOval(80 + j*(size*8), 50 + (i*size*7), 30, 30);
						} else {
							g.fillOval(80 + j*(size*7), 50 + (i*size*6), 30, 30);

						}
					}
				}
			}
		}
	} 
	//goes through the matrix and checks for any wins
	public static String checkwin(Board board, DrawingPanel panel, Graphics g) { 
		Piece[][] pieces = board.getboard();
		//checks horizontal and vertical wins
		for (int i =0; i < pieces.length-3; i++) {
			for (int j = 0; j < pieces[i].length-3; j++) {
				
				//horizontal possibilities
				if (pieces[i][j+1]!= null && pieces[i][j+2] != null && pieces[i][j+3] != null && pieces[i][j]!= null) {
					if (pieces[i][j].getPlayer().equals(pieces[i][j+1].getPlayer()) && pieces[i][j+2].getPlayer().equals(pieces[i][j+3].getPlayer())&& pieces[i][j].getPlayer().equals(pieces[i][j+2].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i][j+1].getPlayer() + " wins!", 80, 40);
						return pieces[i][j+1].getPlayer(); 
					}
				}
				if (pieces[i+1][j+1]!= null && pieces[i+1][j+2] != null && pieces[i+1][j+3] != null && pieces[i+1][j]!= null) {
					if (pieces[i+1][j+1].getPlayer().equals(pieces[i+1][j+2].getPlayer()) && pieces[i+1][j+3].getPlayer().equals(pieces[i+1][j].getPlayer())&& pieces[i+1][j].getPlayer().equals(pieces[i+1][j+2].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i+1][j+1].getPlayer() + " wins!", 80, 40);
						return pieces[i+1][j+1].getPlayer(); 
					}
				}
				if (pieces[i+2][j+1]!= null && pieces[i+2][j+2] != null && pieces[i+2][j+3] != null && pieces[i+2][j]!= null) {
					if (pieces[i+2][j+1].getPlayer().equals(pieces[i+2][j+2].getPlayer()) && pieces[i+2][j+3].getPlayer().equals(pieces[i+2][j].getPlayer())&& pieces[i+2][j+2].getPlayer().equals(pieces[i+2][j].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i+2][j+1].getPlayer() + " wins!", 80, 40);
						return pieces[i+2][j+1].getPlayer(); 
					}
				}
				if (pieces[i+3][j+1]!= null && pieces[i+3][j+2] != null && pieces[i+3][j+3] != null && pieces[i+3][j]!= null) {
					if (pieces[i+3][j+1].getPlayer().equals(pieces[i+3][j+2].getPlayer()) && pieces[i+3][j+3].getPlayer().equals(pieces[i+3][j].getPlayer())&& pieces[i+3][j].getPlayer().equals(pieces[i+3][j+1].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i+3][j+1].getPlayer() + " wins!", 80, 40);
						return pieces[i+3][j+1].getPlayer(); 
					}
				}
				//vertical possibilities
				if (pieces[i][j]!= null && pieces[i+1][j] != null && pieces[i+2][j] != null && pieces[i+3][j]!= null) {
					if (pieces[i][j].getPlayer().equals(pieces[i+1][j].getPlayer()) && pieces[i+2][j].getPlayer().equals(pieces[i+3][j].getPlayer())&& pieces[i][j].getPlayer().equals(pieces[i+2][j].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i+3][j].getPlayer() + " wins!", 80, 40);
						return pieces[i+3][j+1].getPlayer(); 
					}
				}
				if (pieces[i][j+1]!= null && pieces[i+1][j+1] != null && pieces[i+2][j+1] != null && pieces[i+3][j+1]!= null) {
					if (pieces[i][j+1].getPlayer().equals(pieces[i+1][j+1].getPlayer()) && pieces[i+2][j+1].getPlayer().equals(pieces[i+3][j+1].getPlayer())&& pieces[i][j+1].getPlayer().equals(pieces[i+2][j+1].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i][j+1].getPlayer() + " wins!", 80, 40);
						return pieces[i][j+1].getPlayer(); 
					}
				}
				if (pieces[i][j+2]!= null && pieces[i+1][j+2] != null && pieces[i+2][j+2] != null && pieces[i+3][j+2]!= null) {
					if (pieces[i][j+2].getPlayer().equals(pieces[i+1][j+2].getPlayer()) && pieces[i+2][j+2].getPlayer().equals(pieces[i+3][j+2].getPlayer())&& pieces[i][j+2].getPlayer().equals(pieces[i+2][j+2].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i][j+2].getPlayer() + " wins!", 80, 40);
						return pieces[i][j+2].getPlayer(); 
					}
				}
				if (pieces[i][j+3]!= null && pieces[i+1][j+3] != null && pieces[i+2][j+3] != null && pieces[i+3][j+3]!= null) {
					if (pieces[i][j+3].getPlayer().equals(pieces[i+1][j+3].getPlayer()) && pieces[i+2][j+3].getPlayer().equals(pieces[i+3][j+3].getPlayer())&& pieces[i][j+3].getPlayer().equals(pieces[i+2][j+3].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i][j+3].getPlayer() + " wins!", 80, 40);
						return pieces[i][j+3].getPlayer(); 
					}
				}
				
			}
		}
		//diagonal possibilities
		for (int i = 0; i < board.getLength()-3; i++) {
			for (int j = 0; j < board.getLength()-3;j++) {
				if (pieces[i][j]!= null && pieces[i+1][j+1]!= null && pieces[i+2][j+2]!= null && pieces[i+3][i+3] != null) {
					if (pieces[i][j].getPlayer().equals(pieces[i+1][j+1].getPlayer()) && pieces[i+2][j+2].getPlayer().equals(pieces[i+3][j+3].getPlayer()) && pieces[i][j].getPlayer().equals(pieces[i+3][j+3].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i][j].getPlayer() + " wins!", 80, 40);
						return pieces[i][j].getPlayer(); 
					}
				}
				}
			if (board.getLength() > 6) {
				for (int j = board.getLength()-3; j >= 0;j--) {
					if (pieces[i][j]!= null && pieces[i+1][j-1]!= null && pieces[i+2][j-2]!= null && pieces[i+3][j-3] != null) {
						if (pieces[i][j].getPlayer().equals(pieces[i+1][j-1].getPlayer()) && pieces[i+2][j-2].getPlayer().equals(pieces[i+3][j-3].getPlayer()) && pieces[i][j].getPlayer().equals(pieces[i+3][j-3].getPlayer())) {
							g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
							g.setColor(Color.GREEN);
							g.drawString(pieces[i][j].getPlayer() + " wins!", 80, 40);
							return pieces[i][j].getPlayer(); 
						}
					}
				}
			}
			if (board.getLength()>4) {
				for (int j = board.getLength()-1; j > board.getLength()-3;j--) {
				if (pieces[i][j]!= null && pieces[i+1][j-1]!= null && pieces[i+2][j-2]!= null && pieces[i+3][j-3] != null) {
					if (pieces[i][j].getPlayer().equals(pieces[i+1][j-1].getPlayer()) && pieces[i+2][j-2].getPlayer().equals(pieces[i+3][j-3].getPlayer()) && pieces[i][j].getPlayer().equals(pieces[i+3][j-3].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i][j].getPlayer() + " wins!", 80, 40);
						return pieces[i][j].getPlayer(); 
					}
				}
				}
			}
		}
		for (int i = board.getLength()-4; i > 0; i--) {
			for (int j = 0; j < board.getLength()-4;j++) {
				if (pieces[i][j]!= null && pieces[i+1][j+1]!= null && pieces[i+2][j+2]!= null && pieces[i+3][i+3] != null) {
					if (pieces[i][j].getPlayer().equals(pieces[i+1][j+1].getPlayer()) && pieces[i+2][j+2].getPlayer().equals(pieces[i+3][j+3].getPlayer()) && pieces[i][j].getPlayer().equals(pieces[i+3][j+3].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i][j].getPlayer() + " wins!", 80, 40);
						return pieces[i][j].getPlayer(); 
					}
				}
				}
			if (i >= 3) {
				for (int j = board.getLength()-4; j > 0 ;j--) {
				if (pieces[i][j]!= null && pieces[i-1][j+1]!= null && pieces[i-2][j+2]!= null && pieces[i-3][j+3] != null) {
					if (pieces[i][j].getPlayer().equals(pieces[i-1][j+1].getPlayer()) && pieces[i-2][j+2].getPlayer().equals(pieces[i-3][j+3].getPlayer()) && pieces[i][j].getPlayer().equals(pieces[i-3][j+3].getPlayer())) {
						g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
						g.setColor(Color.GREEN);
						g.drawString(pieces[i][j].getPlayer() + " wins!", 80, 40);
						return pieces[i][j].getPlayer(); 
					}
				}
				}
			}
		}
			if (pieces[board.getLength()-1][0]!= null && pieces[board.getLength()-2][1]!= null && pieces[board.getLength()-3][2]!= null && pieces[board.getLength()-4][3] != null) {
				if (pieces[board.getLength()-1][0].getPlayer().equals(pieces[board.getLength()-2][1].getPlayer()) && pieces[board.getLength()-3][2].getPlayer().equals(pieces[board.getLength()-4][3].getPlayer()) && pieces[board.getLength()-1][0].getPlayer().equals(pieces[board.getLength()-3][3].getPlayer())) {
					g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
					g.setColor(Color.GREEN);
					g.drawString(pieces[board.getLength()-1][0].getPlayer() + " wins!", 80, 40);
					return pieces[board.getLength()-1][0].getPlayer(); 
				}
			}
		
		
		//checking for a tie
		int a = 0;
		for (int i = 0; i < board.getLength(); i++){
			for (int j = 0; j < board.getLength();j++){
				if (board.getboard()[i][j] != null) {
					a++;
				}
			}
		}
		if (a >= (board.getLength()*board.getLength())) {
			g.setFont(new Font("font", Font.BOLD + Font.ITALIC, 32));
			g.setColor(Color.RED);
			g.drawString("It's a tie!", 80, 40);
			return "tie";
		}
		return null;
	}
	

}
