package ConsumerNature.Behaviors;

import AgentLauncher.AgentConfig;
import AgentLauncher.AgentDefinition;
import TimeControlAndServices.TimeUtil;
import jade.core.behaviours.OneShotBehaviour;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Поведение Агента-Потребителя
 * По XML файлу сверяет график нагрузки и имеющиеся запасы электроэнергии
 * Если электроэнергии недостаточно, то запускает диалог с Агентом-распределителем энергии
 * В этом же поведении принимает сообщение от Распределителя о количестве закупленной электроэнергии
 */
public class DiscoverLoadBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        // актуальное виртуальное время и время до конца часа
        int curHour = (int) TimeUtil.getCurrentHour();
        int till = (int) TimeUtil.calcMillisTillNextHour();

        // забирает из xml-файла значение необходимой энергии на следующий час
        double energyLess =  takeUsages(curHour, getAgent().getLocalName());

        if (energyLess != 0){
            System.out.println("у агента " + getAgent().getLocalName()
                    + " на момент " + curHour + " нехватка " + energyLess);
            getAgent().addBehaviour(new StayAliveBehavior(
                    new SendRequestAndWaitBehavior(energyLess),
                    new TimeToEatBehavior(myAgent, till)
            ));
        }
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
        return returnMean;
    }

    }
