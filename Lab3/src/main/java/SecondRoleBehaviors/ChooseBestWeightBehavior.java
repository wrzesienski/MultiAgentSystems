package SecondRoleBehaviors;

import MainBehaviors.ReadAndSendBehaviour;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Collections;


/**
 * The class selects the best route.
 */
public class ChooseBestWeightBehavior extends Behaviour {



    private ArrayList<String> bestWay;
    private double bestWeight;
    private boolean keyExit;

    /**
     *
     * @param way actual list of nodes in route
     * @param weights total weight of route
     */
    public ChooseBestWeightBehavior(ArrayList<String> way, double weights){
        this.bestWay = way;
        this.bestWeight = weights;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void action() {

        ACLMessage receive = getAgent().receive(
                MessageTemplate.MatchProtocol("ToComparing"));


        if (receive != null) {

            String[] backpack = receive.getContent().split(";");
            String[] forParsing = backpack[0].replaceAll("[\\[\\]]", "").split(", ");

            ArrayList<String> way = new ArrayList<>();
            Collections.addAll(way, forParsing);

            double actualWayWeight = Double.parseDouble(backpack[1]);

            if (actualWayWeight < bestWeight) {
                bestWeight = actualWayWeight;
                way.add(getAgent().getLocalName());
                bestWay = way;
                System.out.println("new BestWay:" + bestWay + " with weight " + actualWayWeight);
            }
        }

        if (WakerReverserBehavior.switcher) {
            keyExit = true;
            String keyMessage = "ToStart";
            System.out.println("Final way: " + bestWay);
            getAgent().addBehaviour(new ReadAndSendBehaviour(bestWay, bestWeight, keyMessage));

        }


    }

    @Override
    public boolean done() {
        return keyExit;
    }
}
