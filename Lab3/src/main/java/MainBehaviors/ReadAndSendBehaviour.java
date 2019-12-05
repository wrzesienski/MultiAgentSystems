package MainBehaviors;

import WorkWithXML.ReadXMLFile;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;



public class ReadAndSendBehaviour extends OneShotBehaviour {
    /**
     *  ReadAndSendBehaviour simulates active behavior stage of node agent
     *  main algorythm is:
     *  1. parsing of initial agent XML-File with neighbors information in List list
     *  2. sending messages dependently of conditions
     *
     */



    private ArrayList<String> way;
    private String lastWord;
    private double actualWayWeight;

    public ReadAndSendBehaviour(ArrayList<String> way, double actualWayWeight, String lastWord){
        this.way = way;
        this.actualWayWeight = actualWayWeight;
        this.lastWord = lastWord;

    }


    @Override
    public void action() {

        boolean addedOrNot = false;
//      parsing initial XML-File
        String[][] lists = ReadXMLFile.open(getAgent().getLocalName());


           for (String[] list : lists) {

//              filer of double node enter
                if (filtrateSenderByName(way, list[0], lastWord)) continue;

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

                if (!lastWord.equals("ToStart")) {  // from start to finish filter

                    msg.addReceiver(new AID(list[0], false));
                    actualWayWeight += Double.parseDouble(list[1]);

                    // logical condition for adding agentName to path just one time
                    if (!addedOrNot) {
                        way.add(getAgent().getLocalName());
                        addedOrNot = true;
                    }
                }

                String content = way + ";" +
                        actualWayWeight + ";" +
                        lastWord;
                msg.setContent(content);

                if (lastWord.equals("ToStart")){  // from finish to start filter
                   int ii = way.indexOf(getAgent().getLocalName());
                   // moving back to start
                   String agentTarget = way.get(ii - 1);
                   msg.addReceiver(new AID(agentTarget, false));
                    System.out.println(getAgent().getLocalName() + " sends to "
                         +  agentTarget + " " + content);
                    msg.setProtocol("HelloNeighbor");
                    getAgent().send(msg);
                    break;

               }
                System.out.println(getAgent().getLocalName() + " sends to "
                        + list[0] + " " + content);
                msg.setProtocol("HelloNeighbor");
                getAgent().send(msg);


            }

    }

    private static boolean filtrateSenderByName(List<String> list, String str, String key){

        boolean boo = false;

        if (list != null) {
            for (String lists : list) {

                // если узел уже есть в пути, то повторно уже не включается
                // на обратном пути условие не выполняется априори
                if (lists.equals(str) & !key.equals("ToStart")) {

                    boo = true;
                    break;
                }

            }
        }
        return boo;
    }
}
