package BuyerBehaviors;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendWinner extends OneShotBehaviour {

    private AID bestSeller;

    SendWinner(AID bestSeller){
        this.bestSeller = bestSeller;}
    public void action() {

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(bestSeller);
        msg.setProtocol("Contract");
        msg.setContent("You're winner");
        getAgent().send(msg);
    }
}
