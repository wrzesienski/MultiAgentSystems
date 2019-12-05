import static java.lang.Math.PI;
import static java.lang.Math.sin;

class Functions {

    static double CalcPos(String a, double x) {
        double func = 0;

        switch (a) {

            case ("Func1"):
                return func = -(x * x) + 5;
            case ("Func2"):
                return func = (2 * x) + 2;
            case ("Func3"):
                return func = sin(x / PI * 180);
        }

        return func;
    }
}
