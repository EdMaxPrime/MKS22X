import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    private char[][] maze;
    private boolean animate;

    public Maze(String filename) {
	try {
	    Scanner in = new Scanner(new File(filename));
	    while(in.hasNextLine()) {
		char[] line = in.nextLine().toCharArray();
	    }
	} catch(FileNotFoundException e) {
	    System.out.println('"'+filename+"\" not found");
	    System.exit(0);
	}
    }

    public void setAnimate(boolean b) {}

    public void clearTerminal() {}

    public boolean solve() {return false;}

    private boolean solve(int row, int col) {return false;}

    public static void main(String[] args) {
	Maze a = new Maze("data1.txt");
    }
}
