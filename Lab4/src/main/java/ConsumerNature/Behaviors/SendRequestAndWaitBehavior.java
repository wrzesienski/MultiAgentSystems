package ConsumerNature.Behaviors;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Класс поведения Агента-Потребителя
 * На входе отправляет сообщение о количестве необходимой ему энергии для
 * покрытия графика нагрузки на следующий час. Принимает сообщения от Агента-Дистрибьютора
 * о количестве купленной энергии. Если дистрибьютор успевает купить нужное количество энергии до
 * истечения часа, то поведение извещает об этом. Параллельно принимает сообщения от WakerBehavior.
 * Если при принятии сообщения об окончании поведения необходимое количество ЭЭ
 * не доставлено, то план на час не выполнен и агент извещает об этом.
 */

public class SendRequestAndWaitBehavior extends Behaviour {

    static boolean keyExit;
    private double energyLess;
    private boolean flag = false;

    /**
     * @param energyLess количество необходимой потребителю энергии на следующий час
     */
    SendRequestAndWaitBehavior(double energyLess) {
        this.energyLess = energyLess;
    }

    @Override
    public void onStart() {
        // сообщение-инициация о потребности в электроэнергии
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(String.valueOf(energyLess));
        msg.setProtocol("GetEl");
        msg.addReceiver(myFriend(getAgent().getLocalName()));
        getAgent().send(msg);

        keyExit = true;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.MatchProtocol("Get");

        // получает сообщение, содержащее купленную для потребителя энергию
        ACLMessage receive2 = getAgent().receive(mt);
        if (receive2 != null) {
            // из количества необходимой энергии вычитается полученная
            energyLess = energyLess - Double.parseDouble(receive2.getContent());
            if (energyLess <= 0) {
                System.out.println("Got all quantity");
                flag = true;
            } else System.out.println("Потребителю " + getAgent().getLocalName() + " осталось покрыть " + energyLess);
        }

        // при пробуждении Waker с ключом приходит оповещение о конце ожидания
        if (!keyExit) {
            System.out.println("Distributor is failed! Not Enough energy!");
            flag = true;
        }

    }

    /**
     * Костылек
     * @param agentName задается имя агента, выполняющего операцию
     * @return имя получателя сообщения
     */
    AID myFriend(String agentName){

        switch (agentName) {
            case "AgentConsumer1":
                return new AID("AgentDistributor1", false);
            case "AgentConsumer2":
                return new AID("AgentDistributor2", false);
            case "AgentConsumer3":
                return new AID("AgentDistributor3", false);
        }
        return null;
        }

    @Override
    public boolean done() {
        return flag;
    }
}
