package SecondRoleBehaviors;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;

/**
 * child class
 */
public class GetAndWakeBehavior extends ParallelBehaviour {

    private Behaviour GetBetBehavior;
    private WakerBehaviour WakerrBehavior;

    public GetAndWakeBehavior(Behaviour GetBetBehavior, WakerBehaviour WakerrBehavior) {
        this.GetBetBehavior = GetBetBehavior;
        this.WakerrBehavior = WakerrBehavior;
    }
    @Override
    public void onStart() {
        addSubBehaviour(GetBetBehavior);
        addSubBehaviour(WakerrBehavior);

    }
}
