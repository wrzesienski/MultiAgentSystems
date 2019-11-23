package BuyerBehaviors;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.tools.gui.ACLTextArea;

public class SendWinner extends OneShotBehaviour {

    double bestBet;
    AID bestSeller;

    SendWinner(double bestBet, AID bestSeller){this.bestBet = bestBet;
    this.bestSeller = bestSeller;}
    public void action() {

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(bestSeller);
        msg.setProtocol("Contract");
        msg.setContent("You're winner");
        getAgent().send(msg);
    }
}
