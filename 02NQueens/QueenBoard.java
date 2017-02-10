public class QueenBoard {
    private int[][] board;
    private int solutionCount;

    public QueenBoard(int size) {
	board = new int[size][size];
	clear();
	solutionCount = 0;
    }

    public void solve() {}
    public void countSolutions() {}
    public String getCount() {return "";}
    public String toString() {return "";}

    private void placeQueen(int row, int col) {
	board[row][col] = 1;
	for(int c = 0; c < board.length; c++) {
	    if(c != col) {board[row][c] = -1;}
	}
	for(int r = 0; r < board.length; r++) {
	    if(r != row) {board[r][col] = -1;}
	}
    }

    private void clear() {
	for(int i = 0; i < board.length; i++) {
	    for(int j = 0; j < board[i].length; j++) {
		board[i][j] = 0;
	    }
	}
    }

    public static void main(String[] args) {
	QueenBoard a = new QueenBoard(4);
    }
}
