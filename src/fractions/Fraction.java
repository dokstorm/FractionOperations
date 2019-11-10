package src.fractions;

/**
 * Описывает дроби и операции над ними (сложение, вычитание, умножение, деление).
 */
public class Fraction implements Comparable<Fraction>{
    private int numerator;
    private int denominator;

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

    private int greatestCommonDivisor(int x, int y) {
        int temp;
        while (y != 0) {
            temp = x % y;
            x = y;
            y = temp;
        }
        return x;
    }

    private int leastCommonMultiple(int x, int y){
        return x * y / greatestCommonDivisor(x, y);
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
}
