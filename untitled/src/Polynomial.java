import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class Polynomial {
    private int degree;
    private double[] coefficients;

    public Polynomial(int degree, double[] coefficients) {
        if (degree < 0) {
            throw new IllegalArgumentException("Степень многочлена не может быть отрицательной.");
        }
        if (coefficients.length != degree + 1) {
            throw new IllegalArgumentException("Количество коэффициентов должно быть равно степени + 1.");
        }
        this.degree = degree;
        this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
    }

    public double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i <= degree; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public Polynomial add(Polynomial other) {
        int maxDegree = Math.max(this.degree, other.degree);
        double[] resultCoefficients = new double[maxDegree + 1];

        for (int i = 0; i <= maxDegree; i++) {
            double thisCoeff = (i <= this.degree) ? this.coefficients[i] : 0;
            double otherCoeff = (i <= other.degree) ? other.coefficients[i] : 0;
            resultCoefficients[i] = thisCoeff + otherCoeff;
        }

        return new Polynomial(maxDegree, resultCoefficients);
    }

    public Polynomial subtract(Polynomial other) {
        int maxDegree = Math.max(this.degree, other.degree);
        double[] resultCoefficients = new double[maxDegree + 1];

        for (int i = 0; i <= maxDegree; i++) {
            double thisCoeff = (i <= this.degree) ? this.coefficients[i] : 0;
            double otherCoeff = (i <= other.degree) ? other.coefficients[i] : 0;
            resultCoefficients[i] = thisCoeff - otherCoeff;
        }

        return new Polynomial(maxDegree, resultCoefficients);
    }

    public Polynomial multiply(Polynomial other) {
        int resultDegree = this.degree + other.degree;
        double[] resultCoefficients = new double[resultDegree + 1];

        for (int i = 0; i <= this.degree; i++) {
            for (int j = 0; j <= other.degree; j++) {
                resultCoefficients[i + j] += this.coefficients[i] * other.coefficients[j];
            }
        }

        return new Polynomial(resultDegree, resultCoefficients);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = degree; i >= 0; i--) {
            if (coefficients[i] != 0) {
                if (sb.length() > 0) {
                    sb.append(" + ");
                }
                sb.append(coefficients[i]);
                if (i > 0) {
                    sb.append("x");
                    if (i > 1) {
                        sb.append("^").append(i);
                    }
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(2, new double[]{3, 2, 1});
        Polynomial p2 = new Polynomial(1, new double[]{1, 2});

        // Create the GUI frame
        Frame frame = new Frame("Polynomial Operations");
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        // Add polynomial displays to the GUI
        TextArea outputArea = new TextArea(10, 40);
        outputArea.setEditable(false);

        outputArea.append("Polynomial p1: " + p1.toString() + "\n");
        outputArea.append("Polynomial p2: " + p2.toString() + "\n");

        Polynomial sum = p1.add(p2);
        outputArea.append("Sum (p1 + p2): " + sum.toString() + "\n");

        Polynomial difference = p1.subtract(p2);
        outputArea.append("Difference (p1 - p2): " + difference.toString() + "\n");

        Polynomial product = p1.multiply(p2);
        outputArea.append("Product (p1 * p2): " + product.toString() + "\n");

        double value = p1.evaluate(2);
        outputArea.append("Value of p1 at x=2: " + value + "\n");

        // Add components to the frame
        frame.add(outputArea);

        // Add window closing functionality
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
            }
        });

        // Set frame visibility
        frame.setVisible(true);
    }
}
