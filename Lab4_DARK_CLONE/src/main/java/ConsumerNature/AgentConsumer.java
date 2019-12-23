package ConsumerNature;

import ConsumerNature.Behaviors.DiscoverLoadBehavior;
import jade.core.Agent;

/**
 * Класс агента потребителя
 * Имеет график нагрузки в XML виде, связанный со временем
 * Для покрытия этого графика делает запрос Агенту-Распределителю
 */
public class AgentConsumer extends Agent {

    @Override
    protected void setup() {
        System.out.println("Hey im " + getLocalName() + " starting consuming" );
        addBehaviour(new DiscoverLoadBehavior());
    }
}







