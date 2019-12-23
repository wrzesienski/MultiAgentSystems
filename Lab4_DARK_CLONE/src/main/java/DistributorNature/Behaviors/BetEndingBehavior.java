package DistributorNature.Behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.apache.commons.collections4.Get;
import sun.plugin2.message.Message;

import java.util.Arrays;

public class BetEndingBehavior extends WakerBehaviour {

    private AID topic;

    public BetEndingBehavior(Agent a, long timeout, AID topic) {
        super(a, timeout);
        this.topic = topic;
    }

    @Override
    protected void onWake() {

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("KeyExit");
        msg.setContent("NotNull");
        msg.addReceiver(topic);
        myAgent.send(msg);
        }
    }
