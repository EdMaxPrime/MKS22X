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
    public int getCount() {return solutionCount;}
    public String toString() {return "";}

    private void addQueen(int row, int col) {
	board[row][col] = 1;
	for(int i = 0; i < board.length; i++) {
	    if(i != col) {board[row][i] = -1;}
	    if(i != row) {board[i][col] = -1;}
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
