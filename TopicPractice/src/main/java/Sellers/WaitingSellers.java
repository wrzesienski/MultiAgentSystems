package Sellers;

import SellerBehaviors.FightBehavior;
import SellerBehaviors.MakeContractBehavior;
import SellerBehaviors.SubscribeBehavior;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class WaitingSellers extends Agent {




    @Override
    protected void setup() {

        registration(this);

        addBehaviour(new SubscribeBehavior());

//        addBehaviour(new Behaviour() {
//            public void action() {
//                MessageTemplate mt = MessageTemplate.MatchProtocol("Registration");
//                ACLMessage msg = getAgent().receive(mt);
//                if (msg != null) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    final AID topic = subsTopic(msg.getContent());
//
//
//
//                    addBehaviour(new FightBehavior(topic));
//                    addBehaviour(new MakeContractBehavior());
//                    System.out.println(getAgent().getLocalName() + " got inviting from " +
//                            msg.getSender().getLocalName() + " to enter a " + msg.getContent() + " Topic");
//                } else block();
//
//            }
//
//
//            public boolean done() {
//                return false;
//            }
//
//
//        });

    }



    private static void registration(Agent agent) {
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(agent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(Service.books.toString());
        sd.setName(Service.books.toString() + agent.getLocalName());
        dfad.addServices(sd);

        try {
            DFService.register(agent, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    private AID subsTopic(String name){
        TopicManagementHelper topicHelper = null;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(name);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }

}
