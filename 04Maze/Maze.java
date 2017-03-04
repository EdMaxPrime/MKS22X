import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Maze {
    private char[][] maze;
    private boolean animate;

    public Maze(String filename) {
	try {
	    Scanner in = new Scanner(new File(filename));
	    ArrayList<String> file = new ArrayList<String>();
	    while(in.hasNextLine()) {
		file.add(in.nextLine());
	    }
	    maze = new char[file.size()][];
	    for(int l = 0; l < maze.length; l++) {
		maze[l] = file.get(l).toCharArray();
	    }
	    animate = false;
	} catch(FileNotFoundException e) {
	    System.out.println('"'+filename+"\" not found");
	    System.exit(0);
	}
    }

    public void setAnimate(boolean b) {}

    public void clearTerminal() {}

    public boolean solve() {
	int startR = 0, startC = 0;
	for(int r = 0; r < maze.length; r++) {
	    for(int c = 0; c < maze[r].length; c++) {
		if(maze[r][c] == 'S') {
		    startR = r;
		    startC = c;
		    maze[r][c] = ' ';
		    return solve(r, c);
		}
	    }
	}
	return false; //there was no Start
    }

    /* Plan:
       if its not on the board --> false
       if its a wall --> false
       if its an E --> true
       if its already visited --> false
       if its part of the path --> false
       else
         mark HERE as #path
         split off in four directions
	 if they all return false, set HERE to #visited and --> false
	 if one returns true, 
	   set HERE to #path
	   --> true
     */
    private boolean solve(int row, int col) {
	if(!inMaze(row, col) || (maze[row][col] != ' ' && maze[row][col] != 'E')) return false; //if HERE is valid
	if(maze[row][col] == 'E') return true; //reached the end
        maze[row][col] = '@'; //HERE=#path
	boolean attempts = solve(row-1, col) || solve(row, col+1) || solve(row+1, col) || solve(row, col-1); //split off
	if(!attempts) maze[row][col] = '.'; //HERE=#visited
	return attempts;
    }

    private boolean inMaze(int row, int col) {
	return (row >= 0 && row < maze.length) && (col >= 0 && col < maze[0].length);
    }

    public String toString() {
	String str = "";
	for(char[] line : maze) {
	    str += new String(line) + "\n";
	}
	return str;
    }

    public static void main(String[] args) {
	Maze a = new Maze("data/01.dat");
	System.out.println(a);
	System.out.println(a.solve());
	System.out.println(a);
    }
}
