package DistributorNature;

import DistributorNature.Behaviors.WaitConsumer;
import jade.core.Agent;

/**
 * Класс Агента-Распределителя
 * Является промежуточным звеном между Агентом-Производителем и Агентом-Потребителем
 * При получении запроса на покупку электроэнергии обращается к Агентам-Производителям с
 * предложением организовать торги. Он же их и заканчивает, выбирая наилучшую цену и заключая контракт
 * с Производителем. Передает купленную электроэнергию обратно Потребителю
 */
public class AgentDistributor extends Agent {
    @Override
    protected void setup() {
        System.out.println("Hey, i am "+getLocalName());
        addBehaviour(new WaitConsumer());
    }
}
