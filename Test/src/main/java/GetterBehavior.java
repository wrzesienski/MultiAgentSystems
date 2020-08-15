import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class GetterBehavior extends Behaviour {

    private boolean flag = false;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void action() {
        ACLMessage receiveMessage = getAgent().receive();

        if(receiveMessage != null){
            System.out.println(receiveMessage.getContent());
            flag = true;
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

}
