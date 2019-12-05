package SecondRoleBehaviors;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class WakerReverserBehavior extends WakerBehaviour {


    public WakerReverserBehavior(Agent a, long timeout) {
        super(a, timeout);
    }

    /**
     * Класс передает сообщение самому себе о прекращении выбора наилучшего пути
     */

    @Override
    protected void onWake() {

        System.out.println("It's time to return to start");
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("EndOfCalculating");
        msg.addReceiver(getAgent().getAID());
        msg.setContent("NotNull");
        getAgent().send(msg);

    }
}
