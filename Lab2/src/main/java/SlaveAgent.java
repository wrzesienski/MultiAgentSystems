import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SlaveAgent extends Behaviour {
    private String nameOfKing;
    private double newX, newFx, newKingStep;
    private ACLMessage kingMessage;


    @Override
    public void action() {
        MessageTemplate[] tm = {
                MessageTemplate.MatchProtocol("X"),
                MessageTemplate.MatchProtocol("King")};

        ACLMessage receive = getAgent().receive(tm[0]);

        if (receive != null) {

            String s = receive.getContent();
            double a = Double.parseDouble(s);
            double meaning = Functions.CalcPos(getAgent().getLocalName(), a);

            ACLSample msg = new ACLSample(String.valueOf(meaning), "Fx", receive.getSender());
            getAgent().send(msg);
        }

        kingMessage = getAgent().receive(tm[1]);

        if (kingMessage != null) {
            String[] msgs = kingMessage.getContent().split(";");
            nameOfKing = msgs[0];
            newX = Double.parseDouble(msgs[1]);
            newFx = Double.parseDouble(msgs[2]);
            newKingStep = Double.parseDouble(msgs[3]);
        }
    }

    @Override
    public boolean done() {
        return kingMessage != null;
    }

    @Override
    public int onEnd() {

        if (getAgent().getLocalName().equals(nameOfKing)) {
            System.out.println(getAgent().getLocalName() + " is the greatest king now");
            getAgent().addBehaviour(new MainAgent(newX, newFx, newKingStep));

        } else {
            System.out.println(getAgent().getLocalName() + " is a slave again");
            getAgent().addBehaviour(new SlaveAgent());
        }

        return super.onEnd();
    }
}
