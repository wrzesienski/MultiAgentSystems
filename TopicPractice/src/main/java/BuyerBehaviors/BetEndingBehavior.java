package BuyerBehaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class BetEndingBehavior extends WakerBehaviour {

    AID test;

    public BetEndingBehavior(Agent a, long timeout, AID test) {
        super(a, timeout);
        this.test = test;
    }

    private DFAgentDescription[] foundAgents;
    private DFAgentDescription dfad;


    @Override
    protected void onWake() {

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setProtocol("KeyExit");
            msg.setContent(myAgent.getLocalName());
            msg.addReceiver(test);
            myAgent.send(msg);


        }
    }
