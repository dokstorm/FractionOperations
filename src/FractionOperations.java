import src.fractions.Fraction;
import java.util.Scanner;

/**
 * Реализует консольное приложение, позволяющее проводить действия над дробями.
 */
public class FractionOperations {
    /**
     * Проверяет, является ли переданная строка дробью.
     * @param str строка для проверки
     */
    public static boolean isFraction(String str) {
        if (str.matches("[-]?[0-9]+/[0-9]+")) {
            if (Integer.parseInt(str.substring(str.indexOf('/') + 1)) != 0)
                return true;
            else {
                System.out.println("Denominator can't be zero");
                return false;
            }
        }
        else {
            System.out.println("Expression is not an fraction");
            return false;
        }
    }

    /**
     * Преобразует переданную строку в дробь.
     * @param str строка для преобразования
     */
    public static Fraction parseFraction(String str) {
        int slashpos = str.indexOf('/');
        int numerator = Integer.parseInt(str.substring(0, slashpos));
        int denominator = Integer.parseInt(str.substring(slashpos + 1));
        Fraction f = new Fraction(numerator, denominator);
        return f;
    }

    /**
     * Проверяет, является ли переданная строка одной из операций.
     * Операции - сложение "+", вычитание "-", умножение "*" или деление "/")
     * @param str строка для проверки
     */
    public static boolean isOperation(String str) {
        if (str.matches("[+-/*]{1}"))
            return true;
        else {
            System.out.println("Expression is not an operation");
            return false;
        }

    }

    /**
     * Проводит переданное в строке действие над дробями
     * @param f1 первая дробь
     * @param f2 вторая дробь
     * @param str строка, определяющая операцию
     */
    public static Fraction parseOperation(Fraction f1, Fraction f2, String str) {
        switch (str) {
            case "+":
                return f1.sum(f2);
            case "-":
                return f1.subtract(f2);
            case "*":
                return f1.multiply(f2);
            default:
                return f1.divide(f2);
        }
    }

    /**
     * Отвечает за ввод дроби из консоли.
     * Выполняется, пока введённое выражение не будет соответствовать виду дроби.
     */
    public static Fraction inputFraction() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        while (!isFraction(s)) {
            s = in.nextLine();
        }

        return parseFraction(s);
    }

    /**
     * Отвечает за ввод дроби из консоли.
     * Выполняется, пока введённое выражение не будет соответствовать одной из возможных операций.
     */
    public static Fraction inputOperation(Fraction f1, Fraction f2) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        while (!isOperation(s)) {
            s = in.nextLine();
        }
        return parseOperation(f1, f2, s);
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
