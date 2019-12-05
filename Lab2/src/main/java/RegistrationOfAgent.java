import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

class RegistrationOfAgent extends Agent {

    static void registration(Agent Smith) {
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(Smith.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(Service.functions.toString());
        sd.setName(Service.functions.toString() + Smith.getLocalName());
        dfad.addServices(sd);

        try {
            DFService.register(Smith, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
