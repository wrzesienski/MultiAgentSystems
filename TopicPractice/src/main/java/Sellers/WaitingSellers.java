package Sellers;


import SellerBehaviors.SubscribeBehavior;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class WaitingSellers extends Agent {




    @Override
    protected void setup() {

        registration(this);

        addBehaviour(new SubscribeBehavior());


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
