package ConsumerNature.Behaviors;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;

/**
 * child class
 */
public class StayAliveBehavior extends ParallelBehaviour {

    private Behaviour buyEnergy;
    private WakerBehaviour stopBuying;

    StayAliveBehavior(Behaviour buyEnergy, WakerBehaviour stopBuying) {
        this.buyEnergy = buyEnergy;
        this.stopBuying = stopBuying;
    }
    @Override
    public void onStart() {
        addSubBehaviour(buyEnergy);
        addSubBehaviour(stopBuying);
    }
}
