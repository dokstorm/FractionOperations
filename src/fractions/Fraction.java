package src.fractions;

/**
 * Описывает дроби и операции над ними (сложение, вычитание, умножение, деление).
 */
public class Fraction implements Comparable<Fraction>{
    private int numerator;
    private int denominator;

    private enum Operation {
        ADD("+") {
            public Fraction apply(Fraction f1, Fraction f2) { return f1.sum(f2); }
        },
        SUB("-") {
            public Fraction apply(Fraction f1, Fraction f2) { return f1.subtract(f2); }
        },
        MULT("*") {
            public Fraction apply(Fraction f1, Fraction f2) { return f1.multiply(f2); }
        },
        DIV("/") {
            public Fraction apply(Fraction f1, Fraction f2) { return f1.divide(f2); }
        };

        private String operation;
        Operation(String operation) {
            this.operation = operation;
        }
        public abstract Fraction apply(Fraction f1, Fraction f2);

        public static Operation operationFromInput(String input) {
            switch (input) {
                case "+":
                    return Operation.ADD;
                case "-":
                    return Operation.SUB;
                case "*":
                    return Operation.MULT;
                case "/":
                    return Operation.DIV;
            }
            throw new IllegalArgumentException(input + "is not an operation");
        }
    }

    /**
     * Проверяет, является ли переданная строка одной из операций.
     * Операции - сложение "+", вычитание "-", умножение "*" или деление "/")
     * @param str строка для проверки
     */
    public static boolean isOperation(String str) {
        String operationRegex = "[+-/*]{1}";
        if (str.matches(operationRegex))
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
        Operation operation = Operation.operationFromInput(str);
        return operation.apply(f1, f2);
    }

    /**
     * Метод-геттер для числителя.
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Метод-сеттер для числителя.
     */
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    /**
     * Метод-геттер для знаменателя.
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Метод-сеттер для знаменателя.
     */
    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    /**
     * Создаёт объект-дробь из целого числа.
     * @param numerator целое число, из которого необходимо сделать дробь.
     */
    public Fraction(int numerator) {
        this.numerator = numerator;
        denominator = 1;
    }

    /**
     * Создаёт объект-дробь из двух чисел - числителя и знаменателя.
     * @param numerator целое число - числитель.
     * @param denominator целое число - знаменатель.
     */
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numerator;
        result = prime * result + denominator;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Fraction other = (Fraction) obj;
        if (numerator != other.numerator)
            return false;
        if (denominator != other.denominator)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    private int greatestCommonDivisor(int firstNumeric, int secondNumeric) {
        int temp;
        while (secondNumeric != 0) {
            temp = firstNumeric % secondNumeric;
            firstNumeric = secondNumeric;
            secondNumeric = temp;
        }
        return firstNumeric;
    }

    private int leastCommonMultiple(int firstNumeric, int secondNumeric){
        return firstNumeric * secondNumeric / greatestCommonDivisor(firstNumeric, secondNumeric);
    }

    private Fraction flip() {
        if (numerator < 0)
            return new Fraction(-denominator, -numerator);
        else
            return new Fraction(denominator, numerator);
    }

    /**
     * Сравнивает дробь с дробью, переданной в параметре.
     * Возвращает 0, если дроби равны, -1, если данная дробь меньше переданной, и 1, если больше.
     * @param anotherFraction дробь, с которой проводится сравнение.
     */
    @Override
    public int compareTo(Fraction anotherFraction) {
        double d1 = (double)(this.numerator) / (double)(this.denominator);
        double d2 = (double)(anotherFraction.numerator) / (double)(anotherFraction.denominator);
        if (d1 == d2) {
            return 0;
        } else if (d1 < d2) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Если возможно, сокращает дробь
     */
    public Fraction reduct() {
        int greatestCommonDivisor = greatestCommonDivisor(numerator, denominator);
        return new Fraction(numerator / greatestCommonDivisor, denominator / greatestCommonDivisor);
    }

    private void checkAndChangeDenominatorSign() {
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
    }

    /**
     * Складывает с дробью, переданной в параметре.
     * @param f дробь, с которой производится сложение.
     */
    public Fraction sum(Fraction f) {
        if (denominator == f.denominator) {
            Fraction res = new Fraction(numerator + f.numerator, denominator).reduct();
            res.checkAndChangeDenominatorSign();
            return res;
        }
        else {
            int leastCommonMultiple = leastCommonMultiple(denominator, f.denominator);
            Fraction res = new Fraction((leastCommonMultiple / denominator) * numerator  + (leastCommonMultiple / f.denominator) * f.numerator, leastCommonMultiple).reduct();
            res.checkAndChangeDenominatorSign();
            return res;
        }
    }

    /**
     * Вычитает дробь, переданную в параметре.
     * @param f дробь, которую вычитают.
     */
    public Fraction subtract(Fraction f) {
        return sum(new Fraction(-f.numerator, f.denominator));
    }

    /**
     * Умножает на дробь, переданную в параметре
     * @param f дробь, на которую умножают.
     */
    public Fraction multiply(Fraction f) {
        Fraction res = new Fraction(numerator * f.numerator, denominator * f.denominator).reduct();
        res.checkAndChangeDenominatorSign();
        return res;
    }

    /**
     * Делит на дробь, переданную в параметре.
     * @param f дробь, на которую делят.
     */
    public Fraction divide(Fraction f) {
        return multiply(f.flip());
    }

    /**
     * Проверяет, является ли переданная строка дробью.
     * @param str строка для проверки
     */
    public static boolean isFraction(String str) {
        String fractionRegex = "[-]?[0-9]+/[0-9]+";
        if (str.matches(fractionRegex)) {
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
        if (isFraction(str)) {
            int slashpos = str.indexOf('/');
            int numerator = Integer.parseInt(str.substring(0, slashpos));
            int denominator = Integer.parseInt(str.substring(slashpos + 1));
            Fraction f = new Fraction(numerator, denominator);
            return f;
        }
        else {
            throw new IllegalArgumentException("For input string: \"" + str + "\"");
        }
    }
}
