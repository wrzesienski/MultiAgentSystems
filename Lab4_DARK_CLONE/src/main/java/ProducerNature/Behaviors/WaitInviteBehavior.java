package ProducerNature.Behaviors;

import ProducerNature.Battery;
import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Поведение Агента-Производителя
 * Поведение ждет приглашения в топик и подписывает контрак с Дистрибьютором в случае победы
 * в торгах
 */
public class WaitInviteBehavior extends Behaviour {

    private int pieceOfPie = 1;
    private Battery bat;

    public WaitInviteBehavior(Battery bat) {
        this.bat = bat;
    }

    @Override
    public void action() {

        // блок ожидания сообщения о приглашении в топик
        MessageTemplate mt = MessageTemplate.MatchProtocol("Registration");
        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            if (bat.batteryCharge > pieceOfPie) {
                // изымаем некоторое количество энергии из доступного резерва на время торгов
                bat.batteryCharge -=pieceOfPie;
                // производитель подписывается на топик
                AID topic = subscribeTopic(msg.getContent());

                getAgent().addBehaviour(new TopicDialogueBehavior(topic, bat, msg.getSender().getLocalName()));
                System.out.println(getAgent().getLocalName() + " got inviting from " +
                        msg.getSender().getLocalName() + " to enter a " + msg.getContent() + " Topic");
            }
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    /**
     * метод подписывается на топик, созданный дистрибьютором
     * @param name имя агента-производителя, подписывающегося на топик
     * @return
     */
    private AID subscribeTopic(String name) {
        TopicManagementHelper topicHelper;
        AID jadeTopic = null;
        try {
            topicHelper = (TopicManagementHelper)
                    getAgent().getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(name);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }
}
