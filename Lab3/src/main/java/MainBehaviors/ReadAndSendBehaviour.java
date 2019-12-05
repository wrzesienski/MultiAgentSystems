package MainBehaviors;

import WorkWithXML.WorkWithCfgs;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;


/**
 *  ReadAndSendBehaviour simulates active behavior stage of node agent
 *  main algorythm is:
 *  1. parsing of initial agent XML-File with neighbors information in List list
 *  2. sending messages dependently of conditions
 *
 */
public class ReadAndSendBehaviour extends OneShotBehaviour {

    private ArrayList<String> way;
    private String lastWord;
    private double weightToSend;
    private double actualWeight = 0;

    /**
     * @param way actual list of nodes in route
     * @param weightToSend total weight of the way parameter
     * @param lastWord special key message
     */
    public ReadAndSendBehaviour(ArrayList<String> way, double weightToSend, String lastWord){
        this.way = way;
        this.weightToSend = weightToSend;
        this.lastWord = lastWord;

    }


    @Override
    public void action() {

        boolean addedOrNot = false;

//      parsing initial XML-File
        String[][] lists = WorkWithCfgs.unMarshalAny(getAgent().getLocalName()+".xml");

           for (String[] list : lists) {
//              filer of double node enter
                if (filtrateSenderByName(way, list[0], lastWord)
                        ) continue;
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                if (!lastWord.equals("ToStart")) {  // from start to finish filter

                    msg.addReceiver(new AID(list[0], false));
                    actualWeight = weightToSend + Double.parseDouble(list[1]);

                    // logical condition for adding agentName to path just one time
                    if (!addedOrNot) {
                        way.add(getAgent().getLocalName());
                        addedOrNot = true;
                    }
                }



                if (lastWord.equals("ToStart")){  // from finish to start filter

                    actualWeight = weightToSend;
                   int ii = way.indexOf(getAgent().getLocalName());

                   // moving back to start
                   String agentTarget = way.get(ii - 1);
                   msg.addReceiver(new AID(agentTarget, false));

                    String content = way + ";" +
                            actualWeight + ";" +
                            lastWord;
                    msg.setContent(content);

                    System.out.println(getAgent().getLocalName() + " sends message to "
                         +  agentTarget + " with good news!");
                    msg.setProtocol("HelloNeighbor");
                    getAgent().send(msg);
                    break;

               }

               String content = way + ";" +
                       actualWeight + ";" +
                       lastWord;
               msg.setContent(content);

//                System.out.println(getAgent().getLocalName() + " sends to "
//                        + list[0] + " " + content);
                msg.setProtocol("HelloNeighbor");
                getAgent().send(msg);


            }

    }

    /**
     * method compares actual agent-neighbor with agents in way-list:
     * -- on straight run: if name of agent-neighbor is already located inside the list then
     * return boolean is true
     * -- on reverse rung: return boolean is always false
     * @param list list of nodes in way
     * @param str name of agent-neighbor
     * @param key key message
     * @return boolean
     */
    private static boolean filtrateSenderByName(List<String> list, String str, String key){

        boolean boo = false;

        if (list != null) {
            for (String lists : list) {

                /* если узел уже есть в пути, то повторно уже не включается
                на обратном пути условие не выполняется априори */
                if (lists.equals(str) & !key.equals("ToStart")) {

                    boo = true;
                    break;
                }

            }
        }
        return boo;
    }
}
