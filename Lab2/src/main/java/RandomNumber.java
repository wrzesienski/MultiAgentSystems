import java.util.Random;

class RandomNumber {
    // выбор следующего случайного ведущего агента для децентрализации
// системы
    static String number(String a) {
        boolean boo = true;
        String choice = null;

        while (boo) {

            Random rnd = new Random(System.currentTimeMillis());

            int number = 1 + rnd.nextInt(4 - 1);

            switch (number) {
                case (1):
                    choice = "Func1";
                    break;
                case (2):
                    choice = "Func2";
                    break;
                case (3):
                    choice = "Func3";
                    break;
            }

            if (!a.equals(choice)) {
                boo = false;
            }

        }
        return choice;
    }

}
