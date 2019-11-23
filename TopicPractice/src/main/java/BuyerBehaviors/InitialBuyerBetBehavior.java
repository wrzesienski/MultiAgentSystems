package BuyerBehaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class InitialBuyerBetBehavior extends WakerBehaviour {

    AID topic;
    double startBet;

    public InitialBuyerBetBehavior(Agent a, int time, AID topic, double startBet) {
        super(a, time);
        this.topic = topic;
        this.startBet = startBet;


    }
    public void onWake() {

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(topic);
//        System.out.println(topic.getAllAddresses());
            msg.setProtocol("GetBet");
            msg.setContent(Double.toString(startBet));
            getAgent().send(msg);

        }

}
