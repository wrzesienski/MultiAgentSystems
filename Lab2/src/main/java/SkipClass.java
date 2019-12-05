import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class SkipClass extends OneShotBehaviour {

    private String[] xArr;
    private DFAgentDescription[] foundAgents;
    private String nameAgent;


    public SkipClass(String kingdom, double f, double maxFx, double step, DFAgentDescription[] foundAgents, String nameAgent){

        xArr = new String[4];
        xArr[0] = kingdom;
        xArr[1] = String.valueOf(f);
        xArr[2] = String.valueOf(maxFx);
        xArr[3] = String.valueOf(step);

        this.foundAgents = foundAgents;
        this.nameAgent = nameAgent;

}

    @Override
    public void action() {

        ACLSample king = new ACLSample(nameAgent, xArr, "King", foundAgents);
        getAgent().send(king);
        System.out.println(getAgent().getLocalName() + " was thrown down");

    }
}

