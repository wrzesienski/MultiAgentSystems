import java.util.Random;

public class FixedRateRandomProducer implements Producer {
    private double value = 0;
    private Random r = new Random();
    private double price;
    private Thread thread;

    public FixedRateRandomProducer() {
        // рандомная выдержка времени и рандомное количество
            thread = new Thread(() -> {
                while (true) {
// while (!thread.isInterrupted())
//                   thread.Interrupted

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // nextDouble выдает от 0 до 1 и домножает на 20
                    value += r.nextDouble() * 20;
                    if (value >= 100) {
                        value = 100;

                    }
                    // sout быстрый вывод System.out.println();
                    System.out.println("value =" + value);
                }
            });
            thread.start();
        }




    public FixedRateRandomProducer(int f, int sec) {
            thread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // nextDouble выдает от 0 до 1 и домножает на 20
                    value += r.nextDouble() * 20;
                    if (value >= 100) {
                        value = 100;

                    }
                    // sout быстрый вывод System.out.println();
                    System.out.println("value =" + value);
                }
            });
            thread.start();
    }



    public double getPrice(int boughtValue) {
        if (value >= boughtValue) {
            return (100 - value* 2) ;
        } else {
            return -1;
        }
    }

    public void buy(int boughtValue) {
        this.value -=boughtValue;

    }

}

