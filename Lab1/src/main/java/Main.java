public class Main {
    public static void main(String[] args) {
        FixedRateRandomProducer p = new FixedRateRandomProducer();
        FixedRateRandomProducer f = new FixedRateRandomProducer(5, 2000);

       ConsumerImpl consumer = new ConsumerImpl() ;
       consumer.addProducer(p,f);
       consumer.startConsuming();

    }
}
