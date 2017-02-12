import java.util.ArrayList;

public class QueenBoard {
    private int[][] board;
    private int solutionCount;

    public QueenBoard(int size) {
	board = new int[size][size];
	clear();
	solutionCount = -1;
	addQueen(0, 3);
	removeQueen(3);
    }

    public void solve() {
	solve(0);
    }
    public void countSolutions() {
	solutionCount = 0;
	clear();
	countHelper(0, 0);
    }
    public int getCount() {return solutionCount;}

    public void countHelper(int row, int col) {
	if(row >= board.length || col >= board[row].length) return;
	for(int r = row; r < board.length; r++) {
	    if(board[r][col] == 0) {
		addQueen(r, col);
		if(col == board.length - 1) {//last queen
		    solutionCount++;
		} else {
		    countHelper(0, col+1);
		    //countHelper(r+1, col);
		}
		removeQueen(col);
	    }
	};
    }

    private boolean solve(int row) {
	clear();
	return solveHelper(row, 0);
    }

    public boolean solveHelper(int row, int col) {
	if(row >= board.length || col >= board[row].length) return false;
	int r = row;
	while(r < board.length) {
	    if(board[r][col] != 0) r++;
	    else break;
	}
	//System.out.println("Col:"+col+"\tinit:"+row+"  now:"+r);
	if(r == board.length) { //can't place queen in column
	    return false;
	} else {
	    addQueen(r, col);
	    if(col == board.length - 1) { //last queen, we're done
		return true;
	    } else {
		boolean advance = solveHelper(0, col+1);
		if(!advance) {
		    //System.out.println("Backtrack to "+col);
		    removeQueen(col);
		    return solveHelper(r+1, col);
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
	//System.out.println("  placin queen @"+row+","+col+"  safe:"+(board[row][col]));
	board[row][col] = -1;
	for(int i = 0; i < board.length; i++) {
	    if(i != col) {
		if(board[row][i] == 0) board[row][i] = col + 1;
		if(validIndex(i - col + row) && board[i-col+row][i] == 0) board[i-col+row][i] = col + 1;
		if(validIndex(col - i + row) && board[col-i+row][i] == 0) board[col-i+row][i] = col + 1;
	    }
	    if(i != row && board[i][col] == 0) {board[i][col] = col + 1;}
	}
    }

    private boolean validIndex(int index) {
	return (index >= 0 && index < board.length);
    }

    private void removeQueen(int col) {
	//System.out.println("  Removing Queen#"+col);
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

    public void animate() {
	clear();
	animateHelper(0, 0);
    }

    private boolean animateHelper(int row, int col) {
	if(row >= board.length || col >= board[row].length) return false;
	int r = row;
	while(r < board.length && board[r][col] != 0) {r++;}
	if(r == board.length) {
	    System.out.println("No available spaces:\n" + this.toString());
	    return false;
	} else {
	    System.out.println("Trying:\n" + this.toString());
	    addQueen(r, col);
	    if(col == board.length - 1) {
		System.out.println("Done:\n" + this.toString());
		return true;
	    } else {
		boolean advance = animateHelper(0, col+1);
		if(!advance) {
		    removeQueen(col);
		    System.out.println("Backtracking:\n" + this.toString());
		    return animateHelper(r+1, col);
		}
	    }
	}
	return true;
    }

    public static void main(String[] args) {
	QueenBoard a = new QueenBoard(6);
	//System.out.println(a);
	a.solve();
	System.out.println(a);
	a.countSolutions();
	System.out.println(a.getCount());
	System.out.println("\n\t\t##ANIMATION##\n");
	a.animate();
    }
}
