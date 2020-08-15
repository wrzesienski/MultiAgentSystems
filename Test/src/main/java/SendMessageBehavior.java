import com.sun.xml.internal.ws.api.message.Message;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendMessageBehavior extends OneShotBehaviour {

    @Override
    public void action() {

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        msg.addReceiver(new AID("Reader", false));
        msg.setContent("Hello World");
        getAgent().send(msg);   getAgent().addBehaviour(new GetterBehavior());

    }
}
