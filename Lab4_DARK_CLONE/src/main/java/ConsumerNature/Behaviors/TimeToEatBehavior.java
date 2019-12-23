package ConsumerNature.Behaviors;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class TimeToEatBehavior extends WakerBehaviour {
   public TimeToEatBehavior(Agent a, long timeout) {
        super(a, timeout);
    }

    @Override
    protected void onWake() {
        SendRequestAndWaitBehavior.keyExit = false;
        getAgent().addBehaviour(new DiscoverLoadBehavior());
    }
}
