import com.sun.deploy.security.SelectableSecurityManager;

import java.util.ArrayList;
import java.util.List;

public class ConsumerImpl implements Consumer {
    private List<Producer> producers = new ArrayList<>();
    private Thread t;


    public void addProducer(Producer p, Producer f) {
        producers.add(p);
        producers.add(f);

    }

    public void startConsuming() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    double bestPrice = 0;
                    Producer bestProducer = null;
                    for (Producer producer : producers) {
                        double price = producer.getPrice(50);
                        // -1 значит отказ
                        if (bestPrice > price && price != -1) {
                            bestProducer = producer;
                            bestPrice = price;
                        }
                    }
                    if (bestProducer != null) {
                        bestProducer.buy(50);
                        System.out.println("value has been bought successfully");
                    } else {
                        System.err.println("value can't be consumed");
                    }
                }
            }
        });
        t.start();
    }
}


