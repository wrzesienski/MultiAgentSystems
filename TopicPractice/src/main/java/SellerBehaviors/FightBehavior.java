package SellerBehaviors;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;

public class FightBehavior extends Behaviour {

    private AID topic;
    ACLMessage msg, msg1, msg2;
    private boolean key = false;
    private double bet = Double.POSITIVE_INFINITY ;


    Random rnd = new Random();
    private double limit = 10 + rnd.nextInt(40);
//    private double limit = 1;




    public FightBehavior(AID topic){
        this.topic = topic;

    }


    @Override
    public void onStart() {
//        System.out.println("Читается ли топик: " + topic);
        System.out.println("My " + getAgent().getLocalName() + " bet minimum: " + limit);



    }

    public void action() {

        MessageTemplate[] tm = {
                MessageTemplate.and(
                        MessageTemplate.MatchTopic(topic),
                        MessageTemplate.MatchProtocol("GetBet")
                ),
//                MessageTemplate.MatchTopic(topic),
                MessageTemplate.MatchProtocol("KeyExit"),
                MessageTemplate.MatchProtocol("GetBet")
        };


        msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(topic);
        msg.setProtocol("GetBet");
        msg.setContent(Double.toString(bet));
        getAgent().send(msg);

        msg1 = getAgent().receive(tm[0]);
        if (msg1 != null){
//            System.out.println("Message: "+ msg1.getContent());
//            System.out.println("Bet: " + bet);

            if (!msg1.getSender().getLocalName().equals(getAgent().getLocalName())) {
                double betRival = Double.parseDouble(msg1.getContent());
                if (betRival <= bet) {

                    if (0.8 * betRival >= limit) {
                        bet = 0.8 * betRival;
//                    System.out.println("Что я " + getAgent().getLocalName() +" пошлю: "+ bet);
                    } else if (betRival >= limit) {
                        bet = limit;
                    } else {
                        key = true;
                        System.out.println("!!! " + getAgent().getLocalName() + " going out of betting !!!");
                    }
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


}
