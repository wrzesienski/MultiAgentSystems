package DistributorNature.Behaviors;

import TimeControlAndServices.TimeUtil;
import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Random;


/**
 * Поведение Агента-Дистрибьютора
 * FSM-поведение выполняет функции создания топика и поиска победителей в торгах:
 * 1. FirstState: создание топика и приглашение в него потенциальных участников
 * 2. ведение учета лучшей цены во время ведения торгов между производителями. Если в результате
 * торгов вся нужная электроэнергия для потребителя была закуплена, то FSM-поведение завершается,
 * если нет - то поведение идет на новый цикл торгов
 */
public class PerfectTraderBehavior extends FSMBehaviour {

    private double quantity;
    private DFAgentDescription[] foundAgents;
    private DFAgentDescription dfad;
    private int till = (int) TimeUtil.calcMillisTillNextHour();

    PerfectTraderBehavior(double quantity){this.quantity=quantity;}

    @Override
    public void onStart() {

        registerFirstState(new OneShotBehaviour() {
            public void action() {
                // зовем в топик
                AID test = createTopic();
                findAndSend(test);
                System.out.println(getAgent().getLocalName() + " created topic " + test.getName());
                covertInMessage(getAgent().getAID(), test.getName(), "Test");
            }
        }, "Start");

        // активная фаза торгов
        registerState(new GetBetBehavior(quantity, till), "Gap");

         /* это условие должно выполняться при полном пополнении электроэнергии
        либо при досрочном выходе по истечении времени
        */
        registerLastState(new OneShotBehaviour() {
            public void action() {
                System.out.println("Stop bargains");
            }
        }, "End");

        registerDefaultTransition("Start", "Gap", new String[]{"Start", "Gap"});
        registerTransition("Gap", "Start", 1);
        registerTransition("Gap", "End", 0);
    }

    private AID createTopic() {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    getAgent().getHelper(TopicManagementHelper.SERVICE_NAME);
            // создаем топик со случайным именем
            double a = Math.random()*100;
            jadeTopic = topicHelper.createTopic("Test"+ a);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return jadeTopic;
    }

    private void findAndSend(AID aa) {

        try {
            foundAgents = DFService.search(myAgent, dfad);

        } catch (FIPAException e) {
            e.printStackTrace();
        }

        for (DFAgentDescription foundAgent : foundAgents) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setProtocol("Registration");
            msg.setContent(aa.getLocalName());
            msg.addReceiver(foundAgent.getName());
            getAgent().send(msg);

        }
    }

    private void covertInMessage(AID agentTarget,
                                 String content, String protocol) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(agentTarget);
        msg.setContent(content);
        msg.setProtocol(protocol);
        getAgent().send(msg);
    }
}

