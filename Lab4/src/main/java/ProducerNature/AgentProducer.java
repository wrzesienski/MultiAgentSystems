package ProducerNature;


import ProducerNature.Behaviors.WaitInviteBehavior;
import TimeControlAndServices.Services;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Класс Агента-Производителя
 * Производит электроэнергию, по предложению Агента-Распределителя участвует в торгах
 * за право продать ему электроэнергию
 */
public class AgentProducer extends Agent {

    @Override
    protected void setup() {

        // создание нового объекта класса "батарея"
        Battery bat = new Battery(getLocalName());
        // начало накопления электроэнергии
        bat.charge();

        System.out.println("Im producer " + getLocalName() + " !");

        // регистрация производителя в желтой книге
        registration(this);

        addBehaviour(new WaitInviteBehavior(bat));
    }

    private static void registration(Agent agent) {
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(agent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(Services.Producer.toString());
        sd.setName(Services.Producer.toString() + agent.getLocalName());
        dfad.addServices(sd);

        try {
            DFService.register(agent, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
    }
