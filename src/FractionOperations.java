import src.fractions.Fraction;
import java.util.Scanner;

/**
 * Реализует консольное приложение, позволяющее проводить действия над дробями.
 */
public class FractionOperations {
    /**
     * Отвечает за ввод дроби из консоли.
     * Выполняется, пока введённое выражение не будет соответствовать виду дроби.
     */
    public static Fraction inputFraction() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        while (!Fraction.isFraction(s)) {
            s = in.nextLine();
        }

        return Fraction.parseFraction(s);
    }

    /**
     * Отвечает за ввод дроби из консоли.
     * Выполняется, пока введённое выражение не будет соответствовать одной из возможных операций.
     */
    public static Fraction inputOperation(Fraction f1, Fraction f2) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        while (!Fraction.isOperation(s)) {
            s = in.nextLine();
        }
        return Fraction.parseOperation(f1, f2, s);
    }

    /**
     * Главный метод, запускающий на выполнение приложение.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome. Possible operations with fractions: you can sum (+), subtract (-), " +
                "multiply (*) or divide (/). \nInput format - x/y, where x - numerator, y - denominator. " +
                "Enter q to quit. \nPress Enter to continue.");
        while(true) {
            if (in.nextLine().equals("q")) {
                break;
            }
            System.out.println("Enter first fraction:");
            Fraction f1 = inputFraction();
            System.out.println("Enter second fraction:");
            Fraction f2 = inputFraction();
            System.out.println("Enter an operation:");
            System.out.println(inputOperation(f1, f2));
            System.out.println("Press Enter to continue or enter q to quit.");
        }
        in.close();
    }
}
