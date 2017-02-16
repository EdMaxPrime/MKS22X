public class KnightBoard {
    private int[][] board;
    private int rows, cols;

    public KnightBoard(int startingRows, int startingCols) {
	board = new int[startingRows][startingCols];
	rows = startingRows;
	cols = startingCols;
    }
    
    /* Plan:
       given row, col, move
       if move == last --> true
       get valid moves
       sort them
       foreach possible move
         helper(newRow, newCol, move+1)
	 if it works --> true
       return false
     */
    public void solve() {
	for(int r = 0; r < rows/2 + 1; r++) {
	    for(int c = 0; c < cols/2 + 1; c++) {
		boolean didItWork = solveHelper(r, c, 1);
		if(didItWork) return;
	    }
	}
    }

    private boolean solveHelper(int row, int col, int move) {
	stamp(row, col, move);
	if(move == rows*cols) return true;
	return false;
    }

    private void stamp(int row, int col, int move) {
	board[row][col] = move;
    }

    public int[][] getMoves(int row, int col) {
        PList moves = new PList(8);
	int[][] inTheory = {
	    {row-1, col-2}, {row-2, col-1}, {row-2, col+1}, {row-1, col+2},
	    {row+1, col+2}, {row+2, col+1}, {row+2, col-1}, {row-1, col-2}
	};
	for(int[] possibility : inTheory) {
	    if(validSpot(possibility[0], possibility[1]) &&
	       board[possibility[0]][possibility[1]] == 0) {
		moves.add(possibility);
	    }
	}
	return moves.toArray();
    }

    private boolean validSpot(int row, int col) {
	return (row >= 0 && row < rows) && (col >= 0 && col <= cols);
    }

    private String pad(String original, char what, int length) {
	while(original.length() < length) {
	    original = what + original;
	}
	return original;
    }

    public String toString() {
	String str = "";
	for(int r = 0; r < rows; r++) {
	    for(int c = 0; c < cols; c++) {
		str += pad(""+board[r][c], ' ', 3);
	    }
	    str += "\n";
	}
	return str;
    }

    private class PList {
	private int[][] stuff;
	private int length;
	public PList() {this(8);}
	public PList(int init) {
	    stuff = new int[init][];
	    length = 0;
	}
	public void add(int[] arr) {
	    if(length >= stuff.length) {grow();}
	    stuff[length] = arr;
	    length++;
	}
	public int length() {return length;}
	public int[] get(int index) {
	    return stuff[index];
	}
	public int[][] toArray() {
	    int[][] view = new int[length][];
	    for(int i = 0; i < length; i++) {view[i] = stuff[i];}
	    return view;
	}
	private void grow() {
	    int[][] newStuff = new int[2 * stuff.length][];
	    for(int i = 0; i < length; i++) {newStuff[i] = copy(stuff[i]);}
	}
	private int[] copy(int[] orig) {
	    int[] cp = new int[orig.length];
	    for(int i = 0; i < orig.length; i++) {cp[i] = orig[i];}
	    return cp;
	}
    }

    public static void main(String[] args) {
	KnightBoard a = new KnightBoard(5, 5);
	System.out.println(a);
	
    }
}
