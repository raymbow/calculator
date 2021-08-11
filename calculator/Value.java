package calculator;


/************************************************************
 *  Value
 *
 *  Immutable class to represent a calculator value
 *  
 ************************************************************/
public class Value {
  
    private static final String ERROR_MESSAGE = "Error";

    // display types
    private static final int ERROR = -1;
    private static final int UNKNOWN = 0;
    private static final int LONG = 1;
    private static final int DOUBLE = 2;

    private int type = 0;
    private Object value = null;


    /**
     *  constructors
     */
    public Value(int type) {
        init(type);
    }
    public Value(String value) {
        init(value);
    }
    public Value(long value) {
        this.type = LONG;
        this.value = Long.valueOf(value);
    }
    public Value(double value) {
        long longValue = (long)value;
        if ((double)longValue == value) {
            this.type = LONG;
            this.value = Long.valueOf(longValue);
        }
        else {
            this.type = DOUBLE;
            this.value = Double.valueOf(value);
        }
    }


    // initialize based on type only
    private void init(int type) {
        this.type = type;
        switch(type) {
            case UNKNOWN: this.value = Long.valueOf(0); // assume integer
                          this.type = LONG;  
                          break;
            case LONG: this.value = Long.valueOf(0);
                          break;
            case DOUBLE:   this.value = Double.valueOf(0.0);
                          break;
            case ERROR:   this.value = ERROR_MESSAGE;
                          break;
            default:      this.value = ERROR_MESSAGE;
                          this.type = ERROR;
        }
    }

    // initialize based on String value
    private void init(String str) {
        if (str == null) {
            init(UNKNOWN);
        }
	else if (str.equals(ERROR_MESSAGE)) {
            init(ERROR);
            return;
        }
        else if (str.indexOf(".") >= 0) {
            init(DOUBLE);
            try {
                this.value = Double.valueOf(str);
            }
            catch(NumberFormatException e) {
                init(ERROR);
            }
        }
        else {
            init(LONG);
            try {
                this.value = Long.valueOf(str);
            }
            catch(NumberFormatException e) {
                init(ERROR);
            }
        }
    }

    /**
     * @return the value as a String
     */
    public String toString() {
        return this.value.toString();
    }

    /**
     * @return whether this value is an error
     */
    public boolean isError() {
        if (this.type == ERROR) {
            return true;
        }
        return false;
    }

    /**
     * @return whether this value is zero
     */
    public boolean isZero() {
        if (doubleValue() == 0.0) {
            return true;
        }
        return false;
    }

    /**
     * @return whether this value is a long
     */
    public boolean isLong() {
        if (this.type == LONG) {
            return true;
        }
        return false;
    }

    /**
     * @return whether this value is a double
     */
    public boolean isDouble() {
        if (this.type == DOUBLE) {
            return true;
        }
        return false;
    }


    /**
     * @return error value object
     */
    public static Value error() {
        return new Value(ERROR);
    }

    /**
     * @return default initial value
     */
    public static Value init() {
        return new Value(LONG);
    }


    /**
     * @return Value object
     */
    public static Value valueOf(String str) {
        return new Value(str);
    }

    /**
     * @return long version of value
     */
    public long longValue() {
        if (this.type == LONG) {
            Long l = (Long)this.value;
            return l.longValue();
        }
        if (this.type == DOUBLE) {
            Double d = (Double)this.value;
            return d.longValue();
        }
        return 0;
    }

    /**
     * @return double version of value
     */
    public double doubleValue() {
        if (this.type == LONG) {
            Long l = (Long)this.value;
            return l.doubleValue();
        }
        if (this.type == DOUBLE) {
            Double d = (Double)this.value;
            return d.doubleValue();
        }
        return 0.0;
    }
}


