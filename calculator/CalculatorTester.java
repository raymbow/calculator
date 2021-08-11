package calculator;



/**
 *  Test class for the calculator
 *
 *  With no args it will run through test calculations
 *  With 3 args it will attempt to assign operand, operator, operand and then calculate
 *
 */
public class CalculatorTester {
    private static String[][] TESTS = {
        {"3 + 2 = * 6 =", "30"},
        {"3 + 2 * 6 =", "15"}, // This will not work with the simple calculator
	{"70 * 7 =", "490"},
        {"72345 =", "72345"},
	{"7 0 * 7 =", "490"},
        {"7 2 3 4 5 =", "72345"},
        {"4 = =", "4"},
        {"7.0 * 7 =", "49"},
        {"7.0 / 2.0 =", "3.5"},
        {"7 . 0 * 7 =", "49"},
        {"7 . 1 * 7 =", "49.7"},
        {"7 . 0 / 2 . 0 =", "3.5"},
        {"1 + 4 + 10 + 2 - 6 + 1 =", "12"},
        {"1 + 4 + 1 0 + 2 - 6.0 + 1 =", "12"},
        {"1 + 4 + 1 0 + 2 - 6.1 + 1 =", "11.9"}
    };


    // run individual test
    private static String runTest(Calculator calculator, String test, String result) {
        String[] token = test.split("[ ]");
        calculator.clear();
        for (int i = 0; i < token.length; i++) {
            calculator.enter(token[i]);
        }
        String testResult = "bad: ";
        if (calculator.getCurrentValue().toString().equals(result)) {
            testResult = "good: ";
        }
        return testResult + " [" + test + "] [" + calculator.getCurrentValue() + "]  desired result: [" + result + "]";
    }

    // run individual test
    private static String runTest(Calculator calculator, String test) {
        String[] token = test.split("[ ]");
        calculator.clear();
        for (int i = 0; i < token.length; i++) {
            calculator.enter(token[i]);
        }
        return "[" + test + "] [" + calculator.getCurrentValue() + "]";
    }

    // run full suit of tests
    private static String runTests() {
        String testResults = "";
        String newLine = "";
        Calculator calculator = new Calculator();
        for (int i = 0; i < TESTS.length; i++) {
            testResults += newLine + runTest(calculator, TESTS[i][0], TESTS[i][1]);
            newLine = "\n";
	}
        return testResults;
    }

    // main
    public static void main(String[] args) {
        String result = null;
        if (args.length == 0) {
            result = runTests();
        }
        else {
            String test = "";
            String delimiter = "";
            for (int i = 0; i < args.length; i++) {
                test += delimiter + args[i];
                delimiter = " ";
            }
            Calculator calculator = new Calculator();
            result = runTest(calculator, test);
        }
        System.out.println(result);
    }
}



