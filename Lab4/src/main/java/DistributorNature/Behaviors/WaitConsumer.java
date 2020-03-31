package DistributorNature.Behaviors;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Поведение Агента-Распределителя
 * Ожидает запроса на покупку электроэнергии от Агента-Потребителя, создает FSM-поведение,
 * в котором происходят последующие торги
 */

public class WaitConsumer extends Behaviour {

    @Override
    public void action() {

        MessageTemplate tm = MessageTemplate.MatchProtocol("GetEl");
        ACLMessage receive = getAgent().receive(tm);

        if (receive != null) {
            // получаем сообщение от потребителей о нужде в электроэнергии
            double quantity = Double.parseDouble(receive.getContent());
            // запуск ФСМ
            getAgent().addBehaviour(new PerfectTraderBehavior(quantity));
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
