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
	this.clear();
	for(int r = 0; r < rows/2 + 1; r++) {
	    for(int c = 0; c < cols/2 + 1; c++) {
		boolean didItWork = solveHelper(r, c, 1);
		if(didItWork) return;
		this.clear(); //reset if it didnt work
	    }
	}
    }

    public void solve2() {
	this.clear();
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
		Text.print("<X>\033[2J" + this.toString() + "<P"+(rows-r)+" B"+(4*(cols-c))+">");
		boolean didItWork = animateHelper(r, c, 1);
		if(didItWork) {
		    Text.print("<N30 B30>\n===\n");
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
	if(move == rows*cols) return true;
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
	Text.print("---<B3>" + pad(move+"", ' ', 3) + "<B3>");
	Text.wait(1000);
	if(move == rows*cols) return true;
	int[][] moves = getMoves(row, col);
	sortByOutgoing(moves); //but here we sort them
	for(int m = 0; m < moves.length; m++) {
	    int x = 3 * (moves[m][1] - col);
	    if(x < 0)   Text.print("<B"+(-x)+">");
	    else   Text.print("<F"+x+">");
	    int y = moves[m][0] - row;
	    if(y < 0)    Text.print("<P"+(-y)+">");
	    else    Text.print("<N"+y+">");
	    boolean success = animateHelper(moves[m][0], moves[m][1], move+1);
	    if(success) return true;
	    x = -x; y = -y;
	    if(x < 0)    Text.print("<B"+(-x)+">");
	    else    Text.print("<F"+x+">");
	    if(y < 0)    Text.print("<P"+(-y)+">");
	    else    Text.print("<N"+y+">");
	}
	stamp(row, col, 0);
	Text.print("   <B3>");
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

    /**
       Parses the arguments given to the program. Works even if none are
       specified, in that case it just runs a speed test of
       <code>KnightBoard 4-5</code><br>
       Here is the full spec of the arguments:<br>
       KnightBoard [flags] [dimensions]<br>
       Althought those can be in any order. The flags you can pass are:<br>
       -anim     animates each board, otherwise a speed test is run
       -square   makes it so that only square boards are run
       -print    if you are doing speed tests, prints the board for each
       -both     does a speed test of both the regular and opt approach
       The dimensions can be specified together or separately. Columns
       go after rows. Each dimension can be a single number, indicating
       only one board of that dimension needs to be run, or a range.
       Ranges are specified by putting a minus(-) between two numbers,
       one of which is the lower and the other the upper bound. Order
       does not matter for ranges(ie min-max and max-min both work).
       Here are some examples:
       KnightBoard 4
         does a speed test of the knightboard, optimized method only.
       KnightBoard 4-8 -square
         speed test of 4x4, 5x5, 6x6, 7x7, 8x8 optimized boards
       KnightBoard 4-6 -both
         speed test of 4x4, 4x5, 4x6, 5x4, 5x5, 5x6, 6x4, 6x5, 6x6 both
       KnightBoard 5 6 -anim
         animation of 5x6 board
       KnightBoard 8-10 4 -print
         speed text of 8x4, 9x4, 10x4 and prints the board
       KnightBoard 4-8 7-8
         you get the idea
     */
    private static void doArgs(String[] args) {
	boolean anim = false, print = false, regular = false, square = false;
	int rowMin = 4, rowMax = 5, colMin = 4, colMax = 5;
	boolean doneRows = false, doneCols = false;
	for(String s : args) {
	    if(s.equals("-anim")) anim = true;
	    else if(s.equals("-print")) print = true;
	    else if(s.equals("-both")) regular = true;
	    else if(s.equals("-square")) square = true;
	    else if(Character.isDigit(s.charAt(0))) {
	        String[] nums = s.split("-");
		int[] dim = new int[nums.length];
		try {
		    dim[0] = Integer.parseInt(nums[0]);
		    dim[1] = Integer.parseInt(nums[1]);
		} catch(Exception e) {}
		if(doneRows) {
		    colMin = dim[0];
		    if(dim.length > 1) colMax = dim[1];
		    else colMax = colMin;
		    doneCols = true;
		} else {
		    rowMin = dim[0];
		    if(dim.length > 1) rowMax = dim[1];
		    else rowMax = rowMin;
		    doneRows = true;
		}
	    }
	}
	KnightBoard a;
	long timeReg, timeOpt;
	if(rowMin > rowMax) {
	    int temp = rowMin;
	    rowMin = rowMax;
	    rowMax = temp;
	}
	if(colMin > colMax) {
	    int temp = colMin;
	    colMin = colMax;
	    colMax = temp;
	}
	if(!doneCols) {
	    colMin = rowMin;
	    colMax = rowMax;
	}
	for(int rows = rowMin; rows <= rowMax; rows++) {
	    for(int cols = colMin; cols <= colMax; cols++) {
		if(square) cols = rows;
		a = new KnightBoard(rows, cols);
		if(anim) a.animate();
		else {
		    a.clear();
		    timeReg = System.currentTimeMillis();
		    if(rows < 8 && cols < 8 && regular) a.solve();
		    timeReg = System.currentTimeMillis() - timeReg;
		    a.clear();
		    timeOpt = System.currentTimeMillis();
		    a.solve2();
		    timeOpt = System.currentTimeMillis() - timeOpt;
		    System.out.printf("%dx%d board: reg %-4d,%8d\t\topt %dms, %d%n", rows, cols, timeReg, a.regBacktrack, timeOpt, a.optBacktrack);
		    if(print) System.out.println(a);
		}
		if(square) break;
	    }
	}
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
	//KnightBoard b = new KnightBoard(6, 6);
	//b.animate();
	doArgs(args);
    }
}
