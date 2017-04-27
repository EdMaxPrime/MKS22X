public class ExpressionTree {
    private char op;
    private double value;
    private ExpressionTree left,right;
  
    /*TreeNodes are immutable, so no issues with linking them across multiple
    *  expressions. The can be constructed with a value, or operator and 2
    * sub-ExpressionTrees*/
    public ExpressionTree(double value){
	this.value = value;
	op = '~';
    }
    public ExpressionTree(char op, ExpressionTree l, ExpressionTree r){
	this.op = op;
	left = l;
	right = r;
    }
  
  
  
    public char getOp(){
	return op;
    }
  
    /* accessor method for Value, precondition is that isValue() is true.*/
    private double getValue(){
	return value;
    }
    /* accessor method for left, precondition is that isOp() is true.*/
    private ExpressionTree getLeft(){
	return left;
    }
    /* accessor method for right, precondition is that isOp() is true.*/
    private ExpressionTree getRight(){
	return right;
    }
  
    private boolean isOp(){
	return hasChildren();
    }
    private boolean isValue(){
	return !hasChildren();
    }
  
    private boolean hasChildren(){
	return left != null && right != null;
    }

    public String toString() {
	if(!hasChildren())
	    return ""+getValue();
	else
	    return String.format("(%s %s %s)", getLeft().toString(), getOp(), getRight().toString());
    }

    public static void main(String[] args) {
	ExpressionTree a = new ExpressionTree(4.0);
	ExpressionTree b = new ExpressionTree(2.0);
	ExpressionTree c = new ExpressionTree('+',a,b);
	System.out.println(c);
    }
}
