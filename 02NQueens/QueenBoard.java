public class QueenBoard {
    private int[][] board;
    private int solutionCount;

    public QueenBoard(int size) {
	board = new int[size][size];
	clear();
	solutionCount = 0;
	addQueen(0, 3);
	removeQueen(3);
    }

    public void solve() {
	clear();
	solveHelper(0, 0);
    }
    public void countSolutions() {}
    public int getCount() {return solutionCount;}

    public boolean solveHelper(int row, int col) {
	if(row >= board.length || col >= board[row].length) return false;
	int r = row;
	while(r < board.length) {
	    if(board[r][col] != 0) r++;
	    else break;
	}
	if(r == board.length) { //can't place queen in column
	    return false;
	} else {
	    if(col == board.length - 1) { //last queen, we're done
		addQueen(r, col);
		return true;
	    } else {
		addQueen(r, col);
		boolean advance = solveHelper(0, col+1);
		if(!advance) {
		    removeQueen(col);
		    return solveHelper(row+1, col);
		}
	    }
	}
	return true;
    }

    public String toString() {
        String result = "";
	for(int r = 0; r < board.length; r++) {
	    for(int c = 0; c < board[r].length; c++) {
		if(board[r][c] == 0) result += ".";
		else if(board[r][c] > 0) result += "x";
		else result += "Q";
		if(c < board[r].length - 1) result += " ";
		else result += "\n";
	    }
	}
	return result;
    }

    private void addQueen(int row, int col) {
	board[row][col] = -1;
	for(int i = 0; i < board.length; i++) {
	    if(i != col) {
		board[row][i] = col + 1;
		if(validIndex(i - col + row)) board[i - col + row][i] = col + 1;;
		if(validIndex(col - i + row)) board[col - i + row][i] = col + 1;
	    }
	    if(i != row) {board[i][col] = col + 1;}
	}
    }

    private boolean validIndex(int index) {
	return (index >= 0 && index < board.length);
    }

    private void removeQueen(int col) {
	for(int r = 0; r < board.length; r++) {
	    for(int c = 0; c < board[r].length; c++) {
		if(board[r][c] == -1 && c == col) board[r][c] = 0;
		if(board[r][c] == col+1) board[r][c] = 0;
	    }
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
	System.out.println(a);
	a.solve();
	System.out.println(a);
    }
}
