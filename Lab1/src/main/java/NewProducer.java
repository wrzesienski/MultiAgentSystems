import java.util.Random;

public class NewProducer extends FixedRateRandomProducer {
    private double value = 0;
    private double r = 5;
    private double price;
    private Thread thread;

    // рандомная выдержка времени и рандомное количество
    public void runProducer() {
        thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // nextDouble выдает от 0 до 1 и домножает на 20
                value += r;
                if (value >= 100) {
                    value = 100;

                }
                // sout быстрый вывод System.out.println();
                System.out.println("value 2 =" + value);
            }
        });
        thread.start();
    }

}
