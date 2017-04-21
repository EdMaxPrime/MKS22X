import java.util.Stack;

public class Calculator {
    public static double eval(String postfix) {
	String[] pieces = postfix.split(" ");
	Stack<Double> numbers = new Stack<Double>();
	for(String token : pieces) {
	    if(isOperator(token)) {
		double second = numbers.pop();
		numbers.push(performOp(token, numbers.pop(), second));
	    }
	    else {
		numbers.push(Double.parseDouble(token));
	    }
	}
	return numbers.pop();
    }

    private static boolean isOperator(String s) {
	return s.equals("+") || s.equals("-") || s.equals("*") ||
	       s.equals("/") || s.equals("%");
    }

    private static double performOp(String op, double a, double b) {
	switch(op.charAt(0)) {
	case '+':
	    return a + b;
	case '-':
	    return a - b;
	case '*':
	    return a * b;
	case '/':
	    return a / b;
	case '%':
	    return a % b;
	default:
	    return a;
	}
    }

    public static void main(String[] args) {
	String code = "5 5 /";
	System.out.println(code);
	System.out.println(Calculator.eval(code));
    }
    
}
