package SecondRoleBehaviors;

import MainBehaviors.ReadAndSendBehaviour;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Collections;

public class ChooseBestWeightBehavior extends Behaviour {

    /**
     * Класс занимается отбором наилучшего варианта пути
     */

    private ArrayList<String> bestWay;
    private double bestWeight;
    private boolean keyExit;

    public ChooseBestWeightBehavior(ArrayList<String> way, double weights){
        this.bestWay = way;
        this.bestWeight = weights;
    }


    @Override
    public void action() {


        MessageTemplate[] tm = {
                MessageTemplate.MatchProtocol("ToComparing"),
                MessageTemplate.MatchProtocol("EndOfCalculating")


        };

        ACLMessage receive = getAgent().receive(tm[0]);
        if (receive != null) {


            String[] backpack = receive.getContent().split(";");
            String[] forParsing = backpack[0].replaceAll("[\\[\\]]", "").split(", ");

            ArrayList<String> way = new ArrayList<>();
            Collections.addAll(way, forParsing);

            double actualWayWeight = Double.parseDouble(backpack[1]);

            System.out.println("Got weight value " + actualWayWeight);

            if (actualWayWeight < bestWeight){
                bestWeight = actualWayWeight;
                way.add(getAgent().getLocalName());
                bestWay = way;
                System.out.println("new BestWay:" + bestWay + " with weight " + actualWayWeight);
            }
            else System.out.println(bestWeight + " is better " + actualWayWeight +" yet");

        }

        ACLMessage receiveKey = getAgent().receive(tm[1]);
        if (receiveKey != null) {
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
