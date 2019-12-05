import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MainAgent extends OneShotBehaviour {
    private double x, Fx = 0, maxFx, f, k, step, changeFunc = 0;
    private ACLMessage receive = null;
    private DFAgentDescription[] foundAgents;
    private DFAgentDescription dfad;

    MainAgent(double x, double maxFx, double step) {
        this.x = x;
        this.maxFx = maxFx;
        this.step = step;
    }

    @Override
    public void action() {

        try {
            foundAgents = DFService.search(getAgent(), dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        System.out.println("Abscissa point: " + x);
        for (double i = x - step; i <= x + step; i += step) {

            Fx += Functions.CalcPos(getAgent().getLocalName(), i);

            for (DFAgentDescription foundAgent : foundAgents) {
                if (!foundAgent.getName().getLocalName().equals(getAgent().getLocalName())) {

                    ACLSample msg = new ACLSample(String.valueOf(i), "X", foundAgent.getName());
                    getAgent().send(msg);

                    MessageTemplate tm = MessageTemplate.MatchProtocol("Fx");

                    while (receive == null) {
                        try {
                            Thread.sleep(500);
                            receive = getAgent().receive(tm);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    String s = receive.getContent();
                    double a = Double.parseDouble(s);
                    Fx += a;
                    receive = null;
                }

                if (Fx >= changeFunc) {
                    changeFunc = Fx;
                    k = i;
                }

            }
            Fx = 0;
        }

        if (changeFunc > maxFx) {
            maxFx = changeFunc;
            f = k;
        } else {
            System.out.println("Mountain was reached");
            step /= 2;

            f = x;
        }

        System.out.println("Sum of functions: " + maxFx);

        if (step <= 0.1) {
            System.out.println("Maximum of step");
        }
        else{

            String kingdom = RandomNumber.number(getAgent().getLocalName());
            System.out.println("kingdom is going to " + kingdom);

            getAgent().addBehaviour(new SkipClass(kingdom, f, maxFx, step, foundAgents, getAgent().getLocalName()));
        }

//
//        String[] xArr = new String[4];
//        xArr[0] = kingdom;
//        xArr[1] = String.valueOf(f);
//        xArr[2] = String.valueOf(maxFx);
//        xArr[3] = String.valueOf(step);
//
//        ACLSample king = new ACLSample(getAgent().getLocalName(), xArr, "King", foundAgents);
//        getAgent().send(king);
//        System.out.println(getAgent().getLocalName() + " was thrown down");

    }

    @Override
    public int onEnd() {

        getAgent().addBehaviour(new SlaveAgent());
        return super.onEnd();

    }
}
