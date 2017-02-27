public class KnightBoard {
    private int[][] board;
    private int rows, cols;
    public int regBacktrack, optBacktrack;

    public KnightBoard(int startingRows, int startingCols) {
	board = new int[startingRows][startingCols];
	rows = startingRows;
	cols = startingCols;
	regBacktrack = 0;
	optBacktrack = 0;
    }
    
    public void solve() {
	for(int r = 0; r < rows/2 + 1; r++) {
	    for(int c = 0; c < cols/2 + 1; c++) {
		boolean didItWork = solveHelper(r, c, 1);
		if(didItWork) return;
		this.clear(); //reset if it didnt work
	    }
	}
    }

    public void solve2() {
	for(int r = 0; r <= rows/2; r++) {
	    for(int c = 0; c <= rows/2; c++) {
	        boolean didItWork = solveBetter(r, c, 1);
		if(didItWork) return;
		this.clear(); //reset if it didnt work
	    }
	}
    }

    public void animate() {
	for(int r = 0; r <= rows/2; r++) {
	    for(int c = 0; c <= cols/2; c++) {
		System.out.print(Text.print("<X>\033[2J" + this.toString() + "<P"+(rows-r)+" B"+(4*(cols-c))+">"));
		boolean didItWork = animateHelper(r, c, 1);
		if(didItWork) {
		    System.out.print(Text.print("<N30 B30>\n===\n"));
		    System.out.println(this);
		    return;
		}
		this.clear();
	    }
	}
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
    private boolean solveHelper(int row, int col, int move) {
	stamp(row, col, move);
	if(move == rows*cols) return true;
	int[][] moves = getMoves(row, col);
	//dont sort them
	for(int m = 0; m < moves.length; m++) {
	    boolean success = solveHelper(moves[m][0], moves[m][1], move+1);
	    if(success) return true;
	}
	stamp(row, col, 0);
	regBacktrack++;
	return false;
    }

    private boolean solveBetter(int row, int col, int move) {
	stamp(row, col, move);
	if(move == rows*col) return true;
	int[][] moves = getMoves(row, col);
	sortByOutgoing(moves); //but here we sort them
	for(int m = 0; m < moves.length; m++) {
	    boolean success = solveBetter(moves[m][0], moves[m][1], move+1);
	    if(success) return true;
	}
	stamp(row, col, 0);
	optBacktrack++;
	return false;
    }

    private boolean animateHelper(int row, int col, int move) {
	stamp(row, col, move);
	System.out.print(Text.print("---<B3>" + pad(move+"", ' ', 3) + "<B3>"));
	Text.wait(1000);
	if(move == rows*cols) return true;
	int[][] moves = getMoves(row, col);
	sortByOutgoing(moves); //but here we sort them
	for(int m = 0; m < moves.length; m++) {
	    int x = 3 * (moves[m][1] - col);
	    if(x < 0) System.out.print(Text.print("<B"+(-x)+">"));
	    else System.out.print(Text.print("<F"+x+">"));
	    int y = moves[m][0] - row;
	    if(y < 0) System.out.print(Text.print("<P"+(-y)+">"));
	    else System.out.print(Text.print("<N"+y+">"));
	    boolean success = animateHelper(moves[m][0], moves[m][1], move+1);
	    if(success) return true;
	    x = -x; y = -y;
	    if(x < 0) System.out.print(Text.print("<B"+(-x)+">"));
	    else System.out.print(Text.print("<F"+x+">"));
	    if(y < 0) System.out.print(Text.print("<P"+(-y)+">"));
	    else System.out.print(Text.print("<N"+y+">"));
	}
	stamp(row, col, 0);
	System.out.print(Text.print("   <B3>"));
	optBacktrack++;
	return false;
    }

    private void stamp(int row, int col, int move) {
	board[row][col] = move;
    }

    public int[][] getMoves(int row, int col) {
        PList moves = new PList(8);
	int[][] inTheory = {
	    {row-1, col-2}, {row-2, col-1}, {row-2, col+1}, {row-1, col+2},
	    {row+1, col+2}, {row+2, col+1}, {row+2, col-1}, {row+1, col-2}
	};
	for(int[] possibility : inTheory) {
	    //System.out.println(arr2str(possibility) + " for "+row+","+col+"  " + validSpot(possibility[0], possibility[1]));
	    if(canGoThere(possibility[0], possibility[1])) {
		moves.add(possibility);
	    }
	}
	return moves.toArray();
    }

    public int[][] getMoves(int[] spot) {
	return getMoves(spot[0], spot[1]);
    }

    private boolean validSpot(int row, int col) {
	return (row >= 0 && row < rows) && (col >= 0 && col < cols);
    }

    private boolean canGoThere(int row, int col) {
	return validSpot(row, col) && (board[row][col] == 0);
    }

    /**
       Sorts an array of spots on the board by how many
       outgoing moves each of them has. Uses selection
       sort to go through the array,
       @param moves  an array of spots on the board to
                     sort, where each spot is an array
		     of length two [row, col].
     */
    public void sortByOutgoing(int[][] moves) {
	int[] numMoves = new int[moves.length];
	for(int i = 0; i < moves.length; i++) {
	    numMoves[i] = getMoves(moves[i]).length;
	}
	for(int m = 0; m < moves.length; m++) {
	    //find smallest number of outgoing moves
	    int minIndex = m;
	    for(int i = m+1; i < moves.length; i++) {
		if(numMoves[i] < numMoves[minIndex]) {
		    //if(compareMoves(moves[i], moves[minIndex]) < 0) {
		    minIndex = i;
		}
	    }
	    if(minIndex != m) { //if the two are diff, then swap
		int[] temp = moves[m];
		moves[m] = moves[minIndex];
		moves[minIndex] = temp;
		int temp2 = numMoves[m];
		numMoves[m] = numMoves[minIndex];
		numMoves[minIndex] = temp2;
	    }
	}
    }
    
    /**
       Compares two spots on the chessboard in terms of which is
       closer to a corner or the edge of the board.
       @param a  an array [row, col]
       @param b  an array [row, col]
       @return   true if a is closer than b, or if they are the same
                 distance. False if b is closer than a.
     */
    private boolean closerToEdge(int[] a, int[] b) {return false;}

    /**
       Compares two spots on the board by the number of possible
       moves that can be made from each.
       @param a  a spot [row, col]
       @param b  a spot [row, col[
       @return   a negative number if a has less possible moves
                 than b, zero if they have the same number of
		 possible moves, and a positive number if a has
		 more possible moves than b.
     */
    private int compareMoves(int[] a, int[] b) {
        return getMoves(a).length - getMoves(b).length;
    }

    public void clear() {
	board = new int[rows][cols];
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

    public static String arr2str(int[][] arr) {
	String str = "[\n";
	for(int[] i : arr) {
	    str += "  " + arr2str(i) + "\n";
	}
	return str + "]";
    }

    public static String arr2str(int[] arr) {
	String str = "[";
	if(arr.length == 0) return "[]";
	for(int i : arr) {
	    str += i + ", ";
	}
	return str.substring(0, str.length()-2) + "]";
    }

    public static void main(String[] args) {
	/*KnightBoard a = new KnightBoard(8, 9);
	System.out.println(a);
	long timeA = System.currentTimeMillis();
	a.solve();
	timeA = System.currentTimeMillis() - timeA;
	System.out.println(a);
	System.out.println("Took: " + timeA + " ms");*/
	KnightBoard a;
	long timeReg, timeOpt;
	/*for(int rows = 10; rows < 11; rows++) {
	    for(int cols = 4; cols < 5; cols++) {
		a = new KnightBoard(rows, cols);
		timeReg = System.currentTimeMillis();
		//if(rows < 8 && cols < 8) a.solve();
		timeReg = System.currentTimeMillis() - timeReg;
		a.clear();
		timeOpt = System.currentTimeMillis();
		a.solve2();
		timeOpt = System.currentTimeMillis() - timeOpt;
		System.out.printf("%dx%d board: reg %-4d,%8d\t\topt %dms, %d%n", rows, cols, timeReg, a.regBacktrack, timeOpt, a.optBacktrack);
		System.out.println(a);
	    }
	    }*/
	KnightBoard b = new KnightBoard(10, 4);
	b.animate();
	/*int[][] b = a.getMoves(0, 2);
	System.out.println(arr2str(b));
	a.sortByOutgoing(b);
	System.out.println(arr2str(b));*/
    }
}
