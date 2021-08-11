package calculator;

/******************************************************************
 * Calculator
 *
 * Class to represent a simple calculator
 *
 * KNOWN ERROR #1
 * This calculator has a major flaw in that it doesn't take 
 * operator precedence into much consideration and will 
 * not work for something like "3 + 2 * 6 ="
 *
 * A refactoring would be necessary to work correctly.  Perhaps
 * having Calculator delegate to an Expression, which could be
 * an (val, op, expr) expression or a (val) which would allow
 * for recursively addressing operator precedence.
 *
 * Another possibility is to allow one of the Value types be 
 * another Calculator, but I don't think this is as clean.  It
 * would mix Calculator and Expression functionality, not segregating
 * duties very well.
 *
 * KNOWN ERROR #2
 * Pressing equals when there are values put no operation produces error
 * The fix should be to ignore "=" when there is no operator
 *
 * KNOWN PROBLEM
 * Need to add rounding
 ******************************************************************/
public class Calculator {
    
    public static final int OP_NONE = 0; 
    public static final int OP_PLUS = 1;
    public static final int OP_MINUS = 2;
    public static final int OP_MULTIPLY = 3;
    public static final int OP_DIVIDE = 4;

    public static final String STR_EQUALS = "=";
    public static final String STR_CLEAR = "Clear";
    public static final String STR_PLUS = "+";
    public static final String STR_MINUS = "-";
    public static final String STR_DIVIDE = "/";
    public static final String STR_MULTIPLY = "*";
    public static final String STR_DOT = ".";

    private static final String[] OPERATION = {"", STR_PLUS, STR_MINUS, STR_MULTIPLY, STR_DIVIDE};

    private String inputString = "";  // a non-zero length would indicate an edit state
    private Value operand1 = null;
    private Value operand2 = null;
    private int operation = OP_NONE;
 

    /**
     *  constructor
     */
    public Calculator() {
        clear();
    }


    /**
     * clear the calculator
     */
    public void clear() {
        this.operand1 = null;
        this.operand2 = null;
        this.operation = OP_NONE;
        setOperand("0");
        this.inputString = "";
    }

    // apply the input string as operand
    private void applyInput() {
        if (this.inputString.length() > 0) {
            setOperand(this.inputString);
        }
        this.inputString = "";
    }

    /**
     * enter op or value into the calculator, respond accordingly
     */
    public void enter(String token) {
        //System.out.println("BEFORE: " + this + ", " + this.inputString);
        if (token.equals(STR_EQUALS)) {
            applyInput();
            calculate();
        }
        else if (token.equals(STR_CLEAR)) {
            clear();
        }
        else if (isValidOp(token)) {
            applyInput();
            setOperation(token);
        }
        else {
            this.inputString += token;
        }
        //System.out.println("AFTER: " + this + ", " + this.inputString + "\n");
    }

    /**
     *  check for valid op
     */
    public static boolean isValidOp(String op) {
        if (op == null) {
            return false;
        }
        for (int i = 1; i < OPERATION.length; i++) { // skip the OP_NONE
            if (OPERATION[i].equals(op)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  set the operation 
     */
    public void setOperation(int op) {
        if (op > OPERATION.length || op < 0) {
            op = OP_NONE;  // invalid option
            return;
        }
        if (hasBothOperands()) {
            if (!isHigherPriorityOp(op)) {
                calculate();
                this.operation = op;
            }
            else {
                this.operand1 = Value.error();
            }
        }
        else {
            this.operation = op;
        }
    }

    /**
     * set the operation based on String
     */
    public void setOperation(String op) {
        if (op == null) {
            setOperation(OP_NONE);
        }
        else {
            for (int i = 0; i < OPERATION.length; i++) {
                if (OPERATION[i].equals(op)) {
                    setOperation(i);
                    return;
                }
            }
        }
        setOperation(OP_NONE);
    }

    // checks whether the provided op is of multiplication precedence
    private boolean _isMulOp(int op) {
        return (op == OP_MULTIPLY || op == OP_DIVIDE);
    }

    // checks whether the provided op is of multiplication precedence
    private boolean _isAddOp(int op) {
        return (op == OP_PLUS || op == OP_MINUS);
    }

    // determine whether current operation is higher priority than last
    private boolean isHigherPriorityOp(int op) {
        if (_isMulOp(op) && _isAddOp(this.operation)) {
            return true;
        }
        return false;
    }

    // determine if both operand2s are set or not
    private boolean hasBothOperands() {
        if (this.operand1 == null || this.operand2 == null) {
            return false;
        }
        return true;
    }

 
    /**
     *  set the 1st operand if not already set, 2nd operand otherwise
     */
    public void setOperand(String str) {
        if (this.operand1 == null) {
            this.operand1 = new Value(str);
        }
        else {
            this.operand2 = new Value(str);
        }
    }


    /**
     *  @return the current value
     */
    public Value getCurrentValue() {
        if (this.operand2 != null) {
            return this.operand2;
        }
        return this.operand1;
    }

    /**
     * @return string version of current value, or non-finished input string if there
     */
    public String getCurrentValueAsString() {
        if (this.inputString.length() > 0) {
            return this.inputString;
        }
        Value value = getCurrentValue();
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     *  calculate based on operation and operand2s
     *      current value will become the result, operation and operand2 will be initialized
     *  @return resulting value 
     */
    public Value calculate() {
        Value result = _calculate(this.operand1, this.operation, this.operand2);
        this.operand1 = result;
        this.operation = OP_NONE;
        this.operand2 = null;
        return this.operand1;
    }
    
    // _calculate implementation details
    private Value _calculate(Value val1, int op, Value val2) {
        if (val1 == null || val2 == null) {
            return Value.error();
        }
        if (val1.isError() || val2.isError()) {
            return Value.error();
        }
        switch(op) {
            case OP_NONE: return val2;
            case OP_PLUS: return add(val1, val2);
            case OP_MINUS: return subtract(val1, val2);
            case OP_MULTIPLY: return multiply(val1, val2);
            case OP_DIVIDE: return divide(val1, val2);
            default: // do nothing
        }
        return Value.error();
    }
    
    // addition
    private Value add(Value val1, Value val2) {
        if (val1.isLong() && val2.isLong()) {
            return new Value(val1.longValue() + val2.longValue());
        }
        return new Value(val1.doubleValue() + val2.doubleValue());
    }
 
    // subtraction
    private Value subtract(Value val1, Value val2) {
        if (val1.isLong() && val2.isLong()) {
            return new Value(val1.longValue() - val2.longValue());
        }
        return new Value(val1.doubleValue() - val2.doubleValue());
    }
 
    // multiplication
    private Value multiply(Value val1, Value val2) {
        if (val1.isLong() && val2.isLong()) {
            return new Value(val1.longValue() * val2.longValue());
        }
        return new Value(val1.doubleValue() * val2.doubleValue());
    }
 
    // division
    private Value divide(Value val1, Value val2) {
        if (val2.isZero()) {
            return Value.error();
        }
        double result = val1.doubleValue() / val2.doubleValue();
        return new Value(result);
    }

    // converts and operator to string
    private String opToString(int op) {
        if (op < 0 || op > OPERATION.length) {
            return "err";
        }
        return OPERATION[op];
    }

    /**
     *  @return this object as a String
     */
    public String toString() {
        return "" + this.operand1 + " " + opToString(this.operation) + " " + this.operand2 ;
    }
    
}


