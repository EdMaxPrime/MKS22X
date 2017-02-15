public class KnightBoard {
    private int[][] board;
    private int rows, cols;

    public KnightBoard(int startingRows, int startingCols) {
	board = new int[startRows][startingCols];
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
    public void solve() {}

    public String toString() {
	return "";
    }

    public static void main(String[] args) {
	//
    }
}
