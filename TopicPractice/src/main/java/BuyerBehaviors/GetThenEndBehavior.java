package BuyerBehaviors;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;

public class GetThenEndBehavior extends ParallelBehaviour {
    Behaviour GetBetBehavior, WakerBehavior;
    public GetThenEndBehavior(Behaviour GetBetBehavior, Behaviour WakerBehavior) {
        this.GetBetBehavior = GetBetBehavior;
        this.WakerBehavior = WakerBehavior;
    }
    @Override
    public void onStart() {
        addSubBehaviour(GetBetBehavior);
        addSubBehaviour(WakerBehavior);

    }


}
