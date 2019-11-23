package InNextLife;

import BuyerBehaviors.GetThenEndBehavior;
import BuyerBehaviors.GetBetBehavior;
import BuyerBehaviors.InitialBuyerBetBehavior;
import BuyerBehaviors.BetEndingBehavior;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class ExperimentalFSM extends FSMBehaviour {

    AID test;
    double startBet = 5;

    DFAgentDescription[] foundAgents;
    DFAgentDescription dfad;
    String aaa ="Test";

    @Override
    public void onStart() {
        registerFirstState(new OneShotBehaviour() {
            public void action() {
                test = createTopic();
                System.out.println("Вошел");
                findAndSend(getAgent(), aaa);
            }}, "START");
        registerState(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("еще");

                getAgent().addBehaviour(new InitialBuyerBetBehavior(getAgent(), 2000, test, startBet));
            }
        }, "Gap");
        registerLastState(new GetThenEndBehavior(
                new GetBetBehavior(test, startBet),
                new BetEndingBehavior(getAgent(), 35000, test)
        ), "success");



        registerDefaultTransition("START", "Gap");

    }


    private AID createTopic() {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    getAgent().getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(getAgent().getLocalName());
            System.out.println("Выведи топик: " + jadeTopic);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }

    private void findAndSend(Agent a, String name) {

        System.out.println("и сюда");
        try {
            foundAgents = DFService.search(a, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }


        for (DFAgentDescription foundAgent : foundAgents) {
            System.out.println("Найденный агент: " + foundAgent.getName().getLocalName());
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            System.out.println(foundAgent.getName().getLocalName());

            msg.setProtocol("Registration");
            msg.setContent(getAgent().getLocalName());
            msg.addReceiver(foundAgent.getName());
            getAgent().send(msg);

        }
    }
}

