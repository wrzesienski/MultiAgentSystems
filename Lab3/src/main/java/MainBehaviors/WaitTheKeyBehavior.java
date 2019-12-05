package MainBehaviors;

import SecondRoleBehaviors.ChooseBestWeightBehavior;
import SecondRoleBehaviors.GetAndWakeBehavior;
import SecondRoleBehaviors.WakerReverserBehavior;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Collections;

public class WaitTheKeyBehavior extends Behaviour {
    /**
     * Класс является переходным для определения агента - конечного узла и
     * начала оценки наилучшего пути
     */

    private boolean keyExit;

    @Override
    public void action() {

        MessageTemplate tm = MessageTemplate.MatchProtocol("ToComparing");
        ACLMessage receive = getAgent().receive(tm);

        if (receive != null){

            System.out.println("Key was switched by "+  getAgent().getLocalName());
            ArrayList<String> way = new ArrayList<>();
            keyExit = true;
            String[] backpack = receive.getContent().split(";");
            String[] forParsing = backpack[0].replaceAll("[\\[\\]]", "").split(", ");
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
