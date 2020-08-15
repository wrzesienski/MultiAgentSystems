import jade.core.Agent;

public class HelloAgent extends Agent {

    @Override
    protected void setup() {

        switch (getLocalName()){
            case "Sender":
                addBehaviour(new SendMessageBehavior());
                break;
            case "Reader":
                addBehaviour(new GetterBehavior());
                break;
        }
    }
}
