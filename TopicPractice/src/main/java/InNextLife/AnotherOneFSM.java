package InNextLife;

import SellerBehaviors.FightBehavior;
import SellerBehaviors.MakeContractBehavior;
import Sellers.Service;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;

public class AnotherOneFSM extends FSMBehaviour {

    private String mmm;
    AID topic;
    ACLMessage msg, msg1, msg2;
    boolean key = false;
    double bet = 6;
    Random rnd = new Random(System.currentTimeMillis());
    double limit = 1.0 + rnd.nextInt(5 - 1);
    boolean key1 = false;

    @Override
    public void onStart() {
        registration(getAgent());

        registerFirstState(new Behaviour() {


            public void action() {


                MessageTemplate mt = MessageTemplate.MatchProtocol("Registration");
                ACLMessage msg = getAgent().receive(mt);
                if (msg != null) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final AID topic = subsTopic(msg.getContent());

                    if (topic != null){
                        System.out.println("Топик " + topic.getLocalName() + "создан");
                    }
                    else{
                        System.out.println("BORN DEAD");

                    }

                    getAgent().addBehaviour(new FightBehavior(topic));
                    System.out.println(getAgent().getLocalName() + " Принял приглашение " + msg.getContent());
                } else block();
                }



            public boolean done() {
                System.out.println("Топик: " + topic);
                return key;
            }

        }, "START");
        registerLastState(new Behaviour() {



             final AID topic = subsTopic(mmm);

            @Override
            public void onStart() {
                System.out.println("Читается ли топик: " + topic);
            }
            @Override
            public void action() {

                MessageTemplate[] tm = {
                        MessageTemplate.MatchProtocol("GetBet"),
                        MessageTemplate.MatchProtocol("KeyExit"),
                };

                msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(topic);
                msg.setContent(Double.toString(bet));
                msg.setProtocol("GetBet");

                msg1 = getAgent().receive(tm[0]);
                if (msg1 != null){
                    System.out.println("Message: "+ msg1.getContent());
                    double betRival = Double.parseDouble(msg1.getContent());
                    if (betRival < bet) {

                        if (0.9 * betRival >= limit) {
                            bet = 0.9 * betRival;
                        } else if (betRival >= limit) {
                            bet = limit;
                        } else {
                            key = true;
                            System.out.println("I'm going out of betting");
                        }
                    }

                }


                msg2 = getAgent().receive(tm[1]);
                if (msg2 != null) {
                    key = true;
                    System.out.println("I am " + getAgent().getLocalName() + " a winner");
                    getAgent().addBehaviour(new MakeContractBehavior());
                }

            }

            public boolean done() {
                return key;
            }
        }, "Gap");
//        registerLastState(new Fight(topic, limit),"Gap");
//        System.out.println("222");



        registerDefaultTransition("START", "Gap");

//        registerTransition("START","Gap",1);
//        registerTransition("Gap","fail",2);
    }


    private AID subsTopic(String name){
        TopicManagementHelper topicHelper = null;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    getAgent().getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(name);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }


    static void registration(Agent agent) {
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
}
