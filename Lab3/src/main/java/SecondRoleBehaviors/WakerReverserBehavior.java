package SecondRoleBehaviors;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Класс передает сообщение самому себе о прекращении выбора наилучшего пути
 */
public class WakerReverserBehavior extends WakerBehaviour {


    public static boolean switcher = false;

    public WakerReverserBehavior(Agent a, long timeout) {
        super(a, timeout);
    }


    @Override
    protected void onWake() {
        switcher = true;
    }
}
