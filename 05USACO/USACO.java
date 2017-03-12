/*
there are 9 cows
there are R rows of spots and C of spots
1st number is R, 2nd numberr is C, 3rd number is D, inches by stomping
hit the highest number in the 3x3, and then press by D

N instructions
E is final sea level
find V(volume)

first line is R C E N
next lines are array of elevations
next lines are instructions

calculate V with inches (one square six by six foot)

*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class USACO {
    private int[][] farm;
    private int sealevel;

    public static void main (String[] args) throws FileNotFoundException {
	//testing stuff
	//testBronze();
	USACO d = new USACO();
	d.silver("ctravel/ctravel.1.in");
    }
    public static void testBronze() throws FileNotFoundException {
	USACO c = new USACO();
	Scanner answer;
	for(int i = 1; i < 10; i++) {
	    int myAnswer = c.bronze("makelake/makelake."+i+".in");
	    answer = new Scanner(new File("makelake/makelake."+i+".out"));
	    int realAnswer = answer.nextInt();
	    System.out.printf("[%d] Got %d for %d which is "+(myAnswer==realAnswer)+"%n", i, myAnswer, realAnswer);
	}
    }
    public int bronze(String filename)throws FileNotFoundException{
	Scanner in = new Scanner(new File(filename));
	int rows = in.nextInt(), cols = in.nextInt();
	farm = new int[rows][cols];
	sealevel = in.nextInt();
	int numCommands = in.nextInt();
	int r = 0, c = 0;
	int[][] instructions = new int[numCommands][3];
	while(in.hasNextInt()) {
	    if(r < rows) {
	        farm[r][c] = in.nextInt();
	        c++;
	        if(c >= cols) {r++; c = 0;}
	    }
	    else {
		instructions[r-rows] = new int[] {
		    in.nextInt(), in.nextInt(), in.nextInt()
		};
		stomp(instructions[r-rows][0]-1, instructions[r-rows][1]-1, instructions[r-rows][2]);
		r++;
	    }
	}
	return computeVolume();
    }
    public void parray(int[][] a){
	String retstr = "";
	for (int i = 0; i < a.length; i++){
	    for (int j = 0; j < a[0].length; j++){
		retstr+= (a[i][j] + " ");
	    }
	    retstr+="\n";
	}
	System.out.println(retstr);
    }
    public void stomp(int row, int col, int amount) {
	int highest = farm[row][col];
	for (int i = row; i < row+3; i++){
	    for (int j = col; j < col+3; j++){
		if (farm[i][j] > highest){highest = farm[i][j];}
	    }
	}
	if (highest - amount < 0){highest = amount;}
	//System.out.println("Highest:   " + highest);
       	for (int i = row; i < row+3; i++){
	    for (int j = col; j < col+3; j++){
	        if (farm[i][j]>highest-amount){farm[i][j] = highest-amount;}
	    }
	}
    }
    public int computeVolume(){
	int howmuchwater = 0;
	for (int i = 0; i < farm.length; i++){
	    for (int j = 0; j < farm[0].length; j++){
		if(sealevel > farm[i][j]){
		    howmuchwater += (sealevel - farm[i][j])*5184;
		}
	    }
	}
	return howmuchwater;
    }

    /*~~~SILVER PROBLEM~~~*/
    public int silver(String filename) throws FileNotFoundException {
	Scanner in = new Scanner(new File(filename));
	int rows = in.nextInt(), cols = in.nextInt(), time = in.nextInt();
	int[][] pasture = new int[rows][cols];
	in.nextLine(); //got to the next line
	int row = 0;
	while(row < rows) {
	    String line = in.nextLine();
	    System.out.println(line);
	    for(int c = 0; c < cols; c++) {
		if(line.charAt(c) == '.') pasture[row][c] = 0; //free
		else if(line.charAt(c) == '*') pasture[row][c] = -1; //tree
	    }
	    row++;
	}
	int startRow = in.nextInt(),
	    startCol = in.nextInt(),
	    endRow = in.nextInt(),
	    endCol = in.nextInt();
	parray(pasture);
	return 0;
    }
    private void moveCow(int[][] pasture) {
	String newValues = "";
	for(int r = 0; r < pasture.length; r++) {
	    for(int c = 0; c < pasture[r].length; c++) {
		if(pasture[r][c] == 0) {
		    //make it sum of surrounding squares (ignore trees)
		    //add it to string
		    newValues += " " + sum4Neighbors(r, c, pasture);
		}
		else if(pasture[r][c] > 0) {
		    //add 0 to string
		    newValues += " 0";
		}
	    }
	}
	Scanner scanner = new Scanner(newValues);
	for(int r = 0; r < pasture.length; r++) {
	    for(int c = 0; c < pasture[r].length; c++) {
	        if(scanner.hasNextInt()) pasture[r][c] = scanner.nextInt();
	    }
	}
    }
    /**
       Returns the sum of the adjacent four naighbors of a cell
       in a 2d array, provided that they are all positive integers.
       @param row    the row of the middle cell
       @param col    the column of the middle cell
       @param data   the array of integers
       @return       the nonnegative sum of the four adjacent positive
                     integers.
     */
    private int sum4Neighbors(int row, int col, int[][] data) {
	int sum = 0;
	if(row - 1 > 0 && data[row-1][col] > -1) //above
	    sum += data[row-1][col];
	if(col+1 < data[row].length && data[row][col+1] > -1) //left
	    sum += data[row][col+1];
	if(row+1 < data.length && data[row+1][col] > -1) //below
	    sum += data[row+1][col];
	if(col-1 > 0 && data[row][col-1] > -1) //right
	    sum += data[row][col-1];
	return sum;
    }
 }
