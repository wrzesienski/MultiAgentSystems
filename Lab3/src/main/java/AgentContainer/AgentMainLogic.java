package AgentContainer;


import MainBehaviors.ReadAndSendBehaviour;
import MainBehaviors.WaitTheKeyBehavior;
import MainBehaviors.WaitingYourLetter;
import WorkWithXML.AgentCfg;
import WorkWithXML.Neighbor;
import WorkWithXML.WorkWithCfgs;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

public class AgentMainLogic extends Agent {

    private double[][] matrixB = {

            {0, 1, 4, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 2, 0, 0, 0, 0, 0, 0},
            {4, 0, 0, 2, 0, 6, 0, 0, 0, 0},
            {0, 2, 2, 0, 3, 0, 5, 0, 0, 0},
            {0, 0, 0, 3, 0, 4, 0, 7, 0, 0},
            {0, 0, 6, 0, 4, 0, 0, 0, 2, 0},
            {0, 0, 0, 5, 0, 0, 0, 6, 0, 1},
            {0, 0, 0, 0, 7, 0, 6, 0, 8, 0},
            {0, 0, 0, 0, 0, 2, 0, 8, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0}

    };

    private static int[] nodes = {0, 4};


    @Override
    protected void setup() {

        readingMatrix(matrixB);

        addBehaviour(new WaitingYourLetter());
        addBehaviour(new WaitTheKeyBehavior());

        if (getLocalName().equals("A" + nodes[0])) {
            addBehaviour(new ReadAndSendBehaviour(new ArrayList<String>(), 0, "A" + nodes[1]));
        }
    }

    private static void readingMatrix(double[][] matrixB) {

        AgentCfg agentcfg = new AgentCfg();

        for (int i = 0; i < matrixB.length; i++) {

            List<Neighbor> neighbors = new ArrayList<>();
            double[] matrix = matrixB[i];

            for (int j = 0; j < matrix.length; j++) {

                if (matrix[j] != 0) {

                    neighbors.add(new Neighbor("A" + j, matrix[j]));
                }
            }
            agentcfg.setName("A" + i);
            agentcfg.setNeighbors(neighbors);
            WorkWithCfgs.marshalAny(AgentCfg.class, agentcfg, "A" + i + ".xml");

        }
    }
}

