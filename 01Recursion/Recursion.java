public class Recursion {
    public static String name() {
	return "Zlotskiy,Max";
    }

    public static double sqrt(double n) throws IllegalArgumentException {
	if(n < 0) throw new IllegalArgumentException("Can't sqrt a negative number");
	return sqrt_helper(n, n/2);
    }

    private static double sqrt_helper(double n, double guess) {
	if(isCloseEnough(n, guess * guess)) return guess;
	return sqrt_helper(n, 0.5 * (n / guess + guess));
    }

    private static boolean isCloseEnough(double a, double b) {
	if(a == b) return true;
	return (Math.abs(a - b) / b) < 0.000001;
    }

    public static void main(String[] args) {
	System.out.println(name());
	System.out.println("===Square roots");
	double[] a = {-1, -2.25, 0, 1, 4, 9, 16, 25, 2.25, 0.16, 0.009, 0.09};
	for(double i : a) {
	    try {
		System.out.println("Square root of " + i + " is " + Math.sqrt(i) + " and I got " + sqrt(i));
	    } catch(IllegalArgumentException err) {
		System.out.println("Square root of " + i + " threw an error.");
	    }
	}
    }
}