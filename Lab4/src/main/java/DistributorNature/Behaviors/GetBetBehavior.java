package DistributorNature.Behaviors;

import TimeControlAndServices.TimeUtil;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

public class GetBetBehavior extends Behaviour {

    private int pieceOfPie = 1;
    private AID topic;
    private double quantity;
    private boolean keyExit;
    private boolean getTest;
    private int till;
    private int tilt;
    double initBet = 500_000;
    private double bestBet = initBet;

    public GetBetBehavior(double quantity, int till) {
        this.quantity = quantity;
        this.tilt = till;
    }

    @Override
    public void onStart() {
        keyExit = false;
        getTest = false;
        till = tilt;

        initBet = 500_000;
        bestBet = initBet;

        MessageTemplate tmm = MessageTemplate.MatchProtocol("Test");

        // ждем получения названия топика, чтобы начать сканирование лучшей цены
        while (!getTest) {
            ACLMessage receive = getAgent().receive(tmm);
            if (receive != null) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getTest = true;
                topic = new AID(receive.getContent(), true);
                // начальная ставка - флаг, от которой начинают отталкиваться производителя в торгах
                covertInMessage(topic, String.valueOf(initBet), "GetBet");
                // создаем Waker, заканчивающий торги и помогающий впоследствии объявить победителя
                getAgent().addBehaviour(new BetEndingBehavior(myAgent, 15000, topic));
            }
        }
    }

    @Override
    public void action() {

        MessageTemplate mt =
                MessageTemplate.and(
                        MessageTemplate.MatchTopic(topic),
                        MessageTemplate.MatchProtocol("GetBet")
                );

        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            double contentBet = Double.parseDouble(msg.getContent());
            if (contentBet < bestBet) {
                bestBet = contentBet;
                covertInMessage(topic, String.valueOf(bestBet), "GetBet");
            }
        }

        MessageTemplate mt1 = MessageTemplate.and(
                MessageTemplate.MatchTopic(topic),
                MessageTemplate.MatchProtocol("KeyExit")
        );
        ACLMessage receive = getAgent().receive(mt1);
        if (receive != null) {
            quantity -= pieceOfPie;
            keyExit = true;
        }
    }

    public boolean done() {
        return keyExit;
    }

    @Override
    public int onEnd() {
        if (bestBet != initBet) {

            covertInMessage(ifIf(getAgent().getLocalName()),
                    String.valueOf(pieceOfPie), "Get");
        } else {
            System.err.println(" !!! empty topic " + topic.getLocalName() + "!!!");
        }

        // переменная, следящая за истечением срока удовлетворения нужд потребителя
        till -= (System.currentTimeMillis() - TimeUtil.iniTime) % TimeUtil.hourDuration;
        long limit = 18000;

        if (quantity > 0) { // нужды потребителя не восполнены в должной мере
            if (till < limit) { // дистрибьютор не успевает уложиться, прерываем ФСМ
                System.out.println("Времени мало!");
                return 0;
            }
            // времени еще
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // идем на новый круг торгов
            return 1;
        }
        // все нужды удовлетворены, FSM-поведение заканчивается
        return 0;
    }

    /**
     * метод для конвертации данных в сообщение
     * @param agentTarget получатель сообщения
     * @param content содержимое сообщения
     * @param protocol протокол
     */
    private void covertInMessage(AID agentTarget,
                                 String content, String protocol) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(agentTarget);
        msg.setContent(content);
        msg.setProtocol(protocol);
        getAgent().send(msg);
    }

    private AID ifIf(String agentName) {
        switch (agentName) {
            case "AgentDistributor1":
                return new AID("AgentConsumer1", false);
            case "AgentDistributor2":
                return new AID("AgentConsumer2", false);
            case "AgentDistributor3":
                return new AID("AgentConsumer3", false);
        }
        return null;
    }
}
