package SellerBehaviors;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.gui.ACLAIDDialog;

public class MakeContractBehavior extends Behaviour {
    public void action() {

        MessageTemplate tm = MessageTemplate.MatchProtocol("Contract");

        ACLMessage receive = getAgent().receive(tm);
        if (receive != null){
            System.out.println(getAgent().getLocalName() + " makes a contract with "
                    + receive.getSender().getLocalName());
        }

    }

    public boolean done() {
        return false;
    }
}
