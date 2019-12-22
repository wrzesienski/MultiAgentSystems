package BuyerBehaviors;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;

public class GetThenEndBehavior extends ParallelBehaviour {
    private Behaviour GetBetBehavior, WakerBehavior;
    public GetThenEndBehavior(Behaviour GetBetBehavior, WakerBehaviour WakerBehavior) {
        this.GetBetBehavior = GetBetBehavior;
        this.WakerBehavior = WakerBehavior;
    }
    @Override
    public void onStart() {
        addSubBehaviour(GetBetBehavior);
        addSubBehaviour(WakerBehavior);

    }


}
