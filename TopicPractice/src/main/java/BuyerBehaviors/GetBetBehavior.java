package BuyerBehaviors;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GetBetBehavior extends Behaviour {

    AID topic;
    double contentBet;
    double betsy;
    private DFAgentDescription[] foundAgents;
    private DFAgentDescription dfad;
    double bestBet;
    AID bestSeller;
    boolean key = false;

    public GetBetBehavior(AID topic, double startBet){
     this.topic = topic ;
     this.bestBet = startBet;
    }

    @Override
    public void onStart() {
        System.out.println( "*************" + "\nStartBet: " + bestBet + "\n*************");
    }

    public void action() {


        MessageTemplate[] tm = {
                MessageTemplate.and(
                        MessageTemplate.MatchTopic(topic),
                        MessageTemplate.MatchProtocol("GetBet")
                        ),
//                MessageTemplate.MatchTopic(topic),
//                MessageTemplate.MatchProtocol("GetBet"),
                MessageTemplate.MatchProtocol("KeyExit"),

        };


        ACLMessage msg =  getAgent().receive(tm[0]);
        if (msg != null) {
            contentBet = Double.parseDouble(msg.getContent());
            if (contentBet < bestBet){
                bestBet = contentBet;
                bestSeller = msg.getSender();
                System.out.printf("Actual best price: %.3f \n", bestBet);
            }

        }


        ACLMessage keyMsg =  getAgent().receive(tm[1]);
        if (keyMsg != null) {
            System.out.printf("Bets r ended, %s has won with bet %.3f \n", bestSeller.getLocalName(), bestBet);
            key = true;
            getAgent().addBehaviour(new SendWinner(bestBet, bestSeller));
        }


    }

    public boolean done() {
        return key;
    }

//    @Override
//    public int onEnd() {
//        getAgent().addBehaviour(new SendWinner(bestBet, bestSeller));
//    }
}
