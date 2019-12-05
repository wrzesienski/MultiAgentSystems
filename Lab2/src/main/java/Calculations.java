import jade.core.Agent;

public class Calculations extends Agent {

    protected void setup() {

        RegistrationOfAgent.registration(this);

        double maxFuncX = Double.NEGATIVE_INFINITY,
                step = 1,
                x = 1;

        switch (getLocalName()) {
            case "Func1":
                addBehaviour(new MainAgent(x, maxFuncX, step));
                break;
            case "Func2":
            case "Func3":
                addBehaviour(new SlaveAgent());

                break;
        }
    }
}

