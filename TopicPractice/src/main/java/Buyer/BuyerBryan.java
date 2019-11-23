package Buyer;

import BuyerBehaviors.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class BuyerBryan extends Agent {

//    Creator:Buyer.AgentTopicCreator;

    private DFAgentDescription[] foundAgents;
    private DFAgentDescription dfad;
    Random rnd = new Random();
    double startBet = 60 + rnd.nextInt(40);


    @Override
    protected void setup() {
        final AID test = createTopic();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        findAndSend(test);

        addBehaviour(new GetThenEndBehavior(
                new GetBetBehavior(test, startBet),
                new BetEndingBehavior(this, 15000, test)
        ));

        addBehaviour(new InitialBuyerBetBehavior(this, 2000, test, startBet));

//        addBehaviour(new ExperimentalFSM());
    }

    private AID createTopic() {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic("Test");
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return jadeTopic;
    }

    private void findAndSend(AID aa) {

        try {
            foundAgents = DFService.search(this, dfad);

        } catch (FIPAException e) {
            e.printStackTrace();
        }

        for (DFAgentDescription foundAgent : foundAgents) {

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            System.out.println("Agent " + foundAgent.getName().getLocalName() + " was found");
            msg.setProtocol("Registration");
            msg.setContent(aa.getLocalName());
            msg.addReceiver(foundAgent.getName());
            send(msg);

        }
    }
}

