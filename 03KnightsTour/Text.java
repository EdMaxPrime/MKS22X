import java.util.Scanner;
import java.util.regex.*;

public class Text{
    private static final int DARK = 1;
    private static final int BRIGHT = 2;
    private static final int ITALICS = 3;
    private static final int UNDERLINE = 4;
    private static final int BLACK = 30;
    private static final int RED = 31;
    private static final int GREEN = 32;
    private static final int YELLOW = 33;
    private static final int BLUE = 34;
    private static final int MAGENTA = 35;
    private static final int CYAN = 36;
    private static final int WHITE = 37;
    private static final String CLEAR_SCREEN =  "\033[2J";
    private static final String CLEAR_LINE   =  "\033[2K";
    private static final String HIDE_CURSOR  =  "\033[?25l";
    private static final String SHOW_CURSOR  =  "\033[?25h";

    //use this to go back to normal terminal colors
    private static final String RESET = "\033[0m"+SHOW_CURSOR;

    //use this to convert from color to background (30 to 37 becomes 40 to 47)
    public static int background(int color){
	return color + 10;
    }

    //terminal specific character to move the cursor to a location
    //top left is 1,1
    private static String go(int x,int y){
        return ("\033[" + x + ";" + y + "H");
    }
    private static String up(int amount) {
	return ("\033[" + amount + "A");
    }
    private static String down(int amount) {
	return ("\033[" + amount + "B");
    }
    private static String right(int amount) {
	return ("\033[" + amount + "C");
    }
    private static String left(int amount) {
	return ("\033[" + amount + "D");
    }

    
    private static String color(int a) {
	return ("\033[" + a + "m");
    }
    private static String color(int a, int b){
        return ("\033[0;" + a + ";" + b + "m");
    }
    private static String color(int a, int b, int c){
        return ("\033[0;" + a + ";" + b + ";" + c + "m");
    }
    private static String color(int a, int b, int c, int d){
        return ("\033[0;" + a + ";" + b + ";" + c + ";" + d + "m");
    }
    
    private static String repeat(String unit, int times) {
	String str = "";
	for(int i = 0; i < times; i++) {
	    str += unit;
	}
	return str;
    }


    //And don't forget you can easily delay the printing if needed:
    private static void wait(int millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
    }

    public static String ask(String question) {
	System.out.print(question + " ");
	Scanner in = new Scanner(System.in);
	return in.nextLine();
    }

    public static String print(String markup) {
	String result = "";
	Matcher m = Pattern.compile("<[A-Za-z0-9= ]+>").matcher(markup);
	int start, end, prev = 0;
	String[] codes;
	while(m.find()) {
	    start = m.start();
	    end = m.end();
	    result += markup.substring(prev, start);
	    //could've used m.group(), but we need to get rid of the < >
	    //and then split by space
	    codes = markup.substring(start+1, end-1).split(" ");
	    for(String s : codes) {
		result += parseMarkup(s);
	    }
	    prev = end; //to remember where the previous(current) match ended
	}
	result += markup.substring(prev); //get the last stuff out
	return result.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&"); //unescape escaped characters
    }

    private static String parseMarkup(String code) {
	if(code.equals("R")) return RESET;
	if(code.equals("X")) return CLEAR_SCREEN;
	if(code.equals("u")) return color(UNDERLINE);
	if(code.equals("i")) return color(ITALICS);
	if(code.equals("b")) return color(DARK);
	if(code.equals("l")) return color(BRIGHT);
	if(code.startsWith("P"))
	    return up(Integer.parseInt(code.substring(1)));
	if(code.startsWith("N"))
	    return down(Integer.parseInt(code.substring(1)));
	if(code.startsWith("B"))
	    return left(Integer.parseInt(code.substring(1)));
	if(code.startsWith("F"))
	    return right(Integer.parseInt(code.substring(1)));
	if(code.startsWith("c") || code.startsWith("h")) {
	    int color = 0;
	    switch(code.charAt(1)) {
	    case 'R':
		color = RED;
		break;
	    case 'G':
		color = GREEN;
		break;
	    case 'Y':
		color = YELLOW;
		break;
	    case 'B':
		color = BLUE;
		break;
	    case 'M':
		color = MAGENTA;
		break;
	    case 'C':
		color = CYAN;
		break;
	    case 'W':
		color = WHITE;
		break;
	    default:
		color = BLACK;
		break;
	    }
	    if(code.startsWith("h")) color = background(color);
	    return color(color);
	}
	return "";
    }


    public static void main(String[]args){
	/*System.out.println(CLEAR_SCREEN);
	System.out.println(HIDE_CURSOR);

	//SHOW A LOT OF COLORS!
	for(int i = 0; i < 8; i++){
	    for(int j = 0; j < 8; j++){
		System.out.println(go(i+1,j+1)+color(30+i,40+j) + "#");
		System.out.println(go(i+1,j+10)+color(30+i,40+j,BRIGHT) + "#");
		System.out.println(go(i+1,j+19)+color(30+i,40+j,DARK,ITALICS) + "#");
	    }
	}

	//HOW TO USE FOR SOME PARTS:
	System.out.println(go(15,20)+color(ITALICS,RED,background(BLUE))+"ITALICS FISH!~~~~");
	System.out.println(go(20,20)+color(GREEN,background(YELLOW))+"+=+ ^o^ ");
	System.out.println(RESET);*/
	System.out.print(CLEAR_SCREEN);
	System.out.printf("%n%n%n%n%n" + color(GREEN, 4) + "HIIII");
	System.out.print(left(2) + color(GREEN, DARK, 4) + " who are you?");
	System.out.println();
	System.out.println(RESET);
    }
}
