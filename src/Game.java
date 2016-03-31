public class Game {
	public StringBuffer board;
	
	public static final int NOMOVE = -1;

	public Game(String s) {board = new StringBuffer(s);}

	public Game(StringBuffer s, int position, char player) {
		board = new StringBuffer();
		board.append(s);
		board.setCharAt(position, player);
	}

	public int move(char player) {
		int defaultMove=NOMOVE;
		//Liefert die erste Stelle mit der der Spieler gewinnen könnte.
		for (int i = 0; i < 9; i++) {
			if (istLeer(board.charAt(i)))  {
				Game game = play(i, player);
				if (game.winner() == player) {
					defaultMove=i;
//					return defaultMove;
				}
					
			}
		}
		// Liefert die erste freie Stelle.
//		for (int i = 0; i < 9; i++) {
//			if (istLeer(board.charAt(i))){
//				//defaultMove=i;
//				return defaultMove;
//			}
//		}	
		return defaultMove;
	}

	public Game play(int move, char player) {
		return new Game(this.board, move, player);
	}

	public char winner() {
//		if (board.charAt(0) != '-' 
//            && board.charAt(0) == board.charAt(1) 
//            && board.charAt(1) == board.charAt(2))
//			return board.charAt(0);
//		if (board.charAt(3) != '-' 
//            && board.charAt(3) == board.charAt(4) 
//            && board.charAt(4) == board.charAt(5))
//			return board.charAt(3);
//		if (board.charAt(6) != '-' 
//            && board.charAt(6) == board.charAt(7) 
//            && board.charAt(7) == board.charAt(8))
//			return board.charAt(6);
		
		for(int i =0;i<=6;i=i+3){
			if (!(istLeer(board.charAt(i)))
		            && board.charAt(i) == board.charAt(i+1) 
		            && board.charAt(i+1) == board.charAt(i+2))
					return board.charAt(i);
		}
		return '-';
	}
	
	private boolean istLeer(char symbol){
		return symbol == '-';
	}
}
