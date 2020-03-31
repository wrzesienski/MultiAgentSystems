package ProducerNature;

import AgentLauncher.AgentConfig;
import AgentLauncher.AgentDefinition;
import TimeControlAndServices.TimeUtil;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Battery{

    private static int turnInAHour = 6;
    private String agentName;
    public double batteryCharge = 0;
    private long tik = TimeUtil.hourDuration/turnInAHour -(turnInAHour *(System.currentTimeMillis() - TimeUtil.iniTime)) % TimeUtil.hourDuration;
    public Battery(String agentName){
        this.agentName = agentName;
    }

    public Battery() {}


    public void charge(){
        // забирает из xml-файла значение необходимой энергии на следующий час
        Thread tready = new Thread(() -> {
            while (true) {

                int curHour = (int) TimeUtil.getCurrentHour();

                // забирает из xml-файла значение необходимой энергии на следующий час
                batteryCharge += takeUsages(curHour, agentName);

                System.out.println("Актуальный запас агента " + agentName + ": "+ batteryCharge);

                try {
                    Thread.sleep(tik);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tready.start();
    }


    private static double takeUsages(int time, String agentName) {
        // вытаскивает из xml все содержимое, можно сделать проще
        double returnMean = 0;
        AgentConfig object;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(AgentConfig.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object obj = jaxbUnmarshaller.unmarshal(new File("src/main/resources/pl1.xml"));
            object = (AgentConfig) obj;

            for (AgentDefinition obb: object.getAgentDefinitions()){
                if (obb.getAgentName().equals(agentName)){
                    // возвращает из массива значений нагрузки нагрузку на актуальный час
                    returnMean = obb.getFilepath().get(time);
                }
            }
        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
        return returnMean/turnInAHour;
    }
}