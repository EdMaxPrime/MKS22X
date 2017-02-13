public class QueenBoard {
    private int[][] board;
    private int solutionCount;

    /**
     * Makes a new NxN board
     * @param size length of one side of the board, must be &gt; 0
     */
    public QueenBoard(int size) {
	if(size < 0) {
	    throw new IllegalArgumentException("Chess Board Dimensions Must be Positive");
	}
	board = new int[size][size];
	clear();
	solutionCount = -1;
    }

    /**
     * Wrapper function for the recursibe solver.
     * Solves the puzzle, updates board variable.
     */
    public void solve() {
	solve(0);
    }
    /**
     * Counts the total number of possible solutions,
     * including reflections and rotations. Use getCount
     * to get the number after this is run.
     */
    public void countSolutions() {
	solutionCount = 0;
	clear();
	countHelper(0, 0);
    }
    /**
     * You must call countSolutions first, otherwise
     * this will return -1.
     * @return the number of solutions for this puzzle
     */
    public int getCount() {return solutionCount;}

    /**
     * A recursive backtracking solution to the N-Queens
     * problem, but every time it gets an answer it updates
     * the solution count. Modifies the board each time.
     * @param row  [0,size) the vertical position to start in
     * @param col  [0,size) the horizontal position to start in
     */
    private void countHelper(int row, int col) {
	if(row >= board.length || col >= board[row].length) return;
	for(int r = row; r < board.length; r++) {
	    if(board[r][col] == 0) {
		addQueen(r, col);
		if(col == board.length - 1) {//last queen
		    solutionCount++;
		} else {
		    countHelper(0, col+1);
		}
		removeQueen(col);
	    }
	};
    }

    /**
     * Starts the solver at a different horizontal
     * position. Used by the old counting algorithm.
     * @deprecated
     * @param row  [0,size) the row to start the solver at
     */
    private boolean solve(int row) {
	clear();
	return solveHelper(row, 0);
    }

    /**
     * Recursive backtracking solution to the N-Queens
     * problem. Stops as soon as it finds a solution,
     * guaranteed to leave the board in the same state
     * each time it finishes. Slightly optimized to cut
     * out unecessary intermediate recursions, but can
     * still take a minute to solve a 14x14 board.
     * @param row  [0,size) the horizontal position to start at
     * @param col  [0,size) the vertical position to start at
     * @return     whather this arrangement is a valid solution
     */
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

    /**
     * Converts the board array into a string.
     * @return  a string with each row on a new line,
                each column separated by a space,
		each queen is a Q,
		each empty spot is a dot,
		and each dangerous position is an x
     */
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

    /**
     * Adds a queen to the board, draws laser beams too.
     * The laser beams are a positive integer equal to
     * the column this queen is in plus one.
     * @param row  [0,size) the row to put this queen in.
     * @param col  [0,size) the column to put this queen in.
                   Not recommended to skip columns, otherwise
		   the solver might not work.
     */
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

    /**
     * Utility method to see if an index is valid for a square board.
     * @return true if [0,size)
     */
    private boolean validIndex(int index) {
	return (index >= 0 && index < board.length);
    }

    /**
     * Removes the queen located in this column. Also
     * removes laser beams associated with this queen.
     * Safe to call on nonexistant queens.
     * @param col  [0,size) the column the queen is in
     */
    private void removeQueen(int col) {
	//System.out.println("  Removing Queen#"+col);
	for(int r = 0; r < board.length; r++) {
	    for(int c = 0; c < board[r].length; c++) {
		if(board[r][c] == -1 && c == col) board[r][c] = 0;
		if(board[r][c] == col+1) board[r][c] = 0;
	    }
	}
    }

    /**
     * Clears the board, essentially removing all queens.
     */
    private void clear() {
	for(int i = 0; i < board.length; i++) {
	    for(int j = 0; j < board[i].length; j++) {
		board[i][j] = 0;
	    }
	}
    }

    /**
     * Shows the inner workings of the solver. Will print out
     * the board at each step.
     */
    public void animate() {
	clear();
	animateHelper(0, 0);
    }

    /**
     * Same as solveHelper, except this method also prints out
     * the board and the action taking place at each step.
     * @param row  [0,size) the row to start at
     * @param col  [0,size) the column to start at
     */
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

    /**
     * The main.
     */
    public static void main(String[] args) {
	QueenBoard a = new QueenBoard(4);
	//System.out.println(a);
	a.solve();
	System.out.println(a);
	a.countSolutions();
	System.out.println(a.getCount());
	System.out.println("\n\t\t##ANIMATION##\n");
	a.animate();
    }
}
