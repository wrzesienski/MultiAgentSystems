package MainBehaviors;

import SecondRoleBehaviors.ChooseBestWeightBehavior;
import SecondRoleBehaviors.GetAndWakeBehavior;
import SecondRoleBehaviors.WakerReverserBehavior;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Collections;


/**
 * The class is transitional to determine the agent - the end node
 * and begin to evaluate the best path
 */
public class WaitTheKeyBehavior extends Behaviour {


    private boolean keyExit;

    @Override
    public void action() {

        MessageTemplate tm = MessageTemplate.MatchProtocol("ToComparing");
        ACLMessage receive = getAgent().receive(tm);

        if (receive != null){

            System.out.println("Key was switched by "+  getAgent().getLocalName());
            keyExit = true;

            String[] backpack = receive.getContent().split(";");
            String[] forParsing = backpack[0].replaceAll("[\\[\\]]", "").split(", ");

            ArrayList<String> way = new ArrayList<>();
            Collections.addAll(way, forParsing);
            way.add(getAgent().getLocalName());

            double actualWayWeight = Double.parseDouble(backpack[1]);

            getAgent().addBehaviour(new GetAndWakeBehavior(
                    new ChooseBestWeightBehavior(way, actualWayWeight),
                    new WakerReverserBehavior(getAgent(), 5000)
            ));
        }
    }

    @Override
    public boolean done() {
        return keyExit;
    }
}
