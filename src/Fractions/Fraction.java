package src.Fractions;
public class Fraction {
    private int p;
    private int q;

    public int getNumerator() {
        return p;
    }

    public void setNumerator(int p) {
        this.p = p;
    }

    public int getDenominator() {
        return q;
    }

    public void setDenominator(int q) {
        this.q = q;
    }

    public Fraction() {
        p = 0;
        q = 1;
    }

    public Fraction(int x) {
        p = x;
        q = 1;
    }

    public Fraction(int p, int q) {
        this.p = p;
        this.q = q;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + p;
        result = prime * result + q;
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
        if (p != other.p)
            return false;
        if (q != other.q)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return p + "/" + q;
    }

    //алгоритм Евклида поиска наибольшего общего делителя
    private int gcd(int x, int y) {
        int temp;
        while (y != 0) {
            temp = x % y;
            x = y;
            y = temp;
        }
        return x;
    }

    //наименьшее общее кратное
    private int lcm(int x, int y){
        return x * y / gcd(x, y);
    }

    //переворот дроби
    public Fraction flip() {
        if (p < 0)
            return new Fraction(-q, -p);
        else
            return new Fraction(q, p);
    }

    //сокращение дроби
    public Fraction reduct() {
        int gcd = gcd(p, q);
        return new Fraction(p / gcd, q / gcd);
    }

    //вынос минуса в числитель
    private void checkDenominatorSign() {
        if (q < 0) {
            p = -p;
            q = -q;
        }
    }

    public Fraction sum(Fraction f) {
        if (q == f.q) {
            Fraction res = new Fraction(p + f.p, q).reduct();
            res.checkDenominatorSign();
            return res;
        }
        else {
            int lcm = lcm(q, f.q);
            Fraction res = new Fraction((lcm / q) * p  + (lcm / f.q) * f.p, lcm).reduct();
            res.checkDenominatorSign();
            return res;
        }
    }

    public Fraction sub(Fraction f) {
        return sum(new Fraction(-f.p, f.q));
    }

    public Fraction multiply(Fraction f) {
        Fraction res = new Fraction(p * f.p, q * f.q).reduct();
        res.checkDenominatorSign();
        return res;
    }

    public Fraction divide(Fraction f) {
        return multiply(f.flip());
    }
}
