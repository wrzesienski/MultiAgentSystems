package MainBehaviors;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Message receiving and processing behavior class.
 */

public class WaitingYourLetter extends Behaviour {

    private boolean keyExit;

    @Override
    public void action() {

        String lastWord;
        ArrayList<String> way = new ArrayList<>();
        double actualWayWeight;

        ACLMessage receive = getAgent().receive(
                MessageTemplate.MatchProtocol("HelloNeighbor"));

        if (receive != null) {

            String[] backpack = receive.getContent().split(";");
            String[] forParsing = backpack[0]
                    .replaceAll("[\\[\\]]", "")
                    .split(", ");
            Collections.addAll(way, forParsing);

            actualWayWeight = Double.parseDouble(backpack[1]);
            lastWord = backpack[2];


            //filter for first entering in finish node
            if (lastWord.equals(getAgent().getLocalName())) {

                System.out.println(getAgent().getLocalName() + " got from " + receive.getSender().getLocalName()
                        + " way: " + way + " weight: " + actualWayWeight + " key: " + lastWord);

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setProtocol("ToComparing");
                msg.setContent(receive.getContent());
                msg.addReceiver(getAgent().getAID());
                getAgent().send(msg);
            }

            // если на обратном пути диалог доходит до начального узла, то происходит заверешние алгоритма
            else if (getAgent().getLocalName().equals(way.get(0))
                    & lastWord.equals("ToStart")) {

                System.out.println("Algorithm has returned to start");
                System.out.printf("Best way: %s\nBestWeight: %2f", way, actualWayWeight);
                keyExit = true;

            } else {
                getAgent().addBehaviour(new ReadAndSendBehaviour(way, actualWayWeight, lastWord));
            }
        }
    }

    @Override
    public boolean done() {
        return keyExit;
    }



}
