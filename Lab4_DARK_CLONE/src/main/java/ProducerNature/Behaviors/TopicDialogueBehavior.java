package ProducerNature.Behaviors;

import ProducerNature.Battery;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Поведение Агента-Производителя
 * Имитирует участие в торгах с другими производителями
 */
public class TopicDialogueBehavior extends Behaviour {

    private int pieceOfPie = 1;
    private AID topic;
    private boolean key = false;
    private double bet = Double.POSITIVE_INFINITY;
    private Battery bat;
    private double limit;
    String kingName;

    TopicDialogueBehavior(AID topic, Battery bat, String kingName){
        this.topic = topic;
        this.bat = bat;
        this.kingName = kingName;
    }


    @Override
    public void onStart() {
        limit = 1000/(bat.batteryCharge + 1);
    }

    @Override
    public void action() {

            MessageTemplate[] tm = {
                    MessageTemplate.and(
                            MessageTemplate.MatchTopic(topic),
                            MessageTemplate.MatchProtocol("GetBet")
                    ),
                    MessageTemplate.and(
                            MessageTemplate.MatchTopic(topic),
                            MessageTemplate.MatchProtocol("KeyExit")
                    )
            };

        ACLMessage msg1 = getAgent().receive(tm[0]);
            if (msg1 != null){

                if (msg1.getSender().getLocalName().equals(kingName)) {
                    double betRival = Double.parseDouble(msg1.getContent());
                    if (betRival <= bet) {

                        if (0.7 * betRival >= limit) {
                            bet = 0.7 * betRival;
                        } else if (betRival >= limit) {
                            bet = limit;
                        } else {
                            key = true;
                            System.err.println("!!! " + getAgent().getLocalName() +
                                    " is going out of betting of " + topic.getLocalName() + "!!!");
                            // возвращаем значение проигравшему в торгах обратно
                            bat.batteryCharge +=pieceOfPie;

                        }
                    }
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(topic);
                    msg.setProtocol("GetBet");
                    msg.setContent(Double.toString(bet));
                    getAgent().send(msg);
                }


            }

        ACLMessage msg2 = getAgent().receive(tm[1]);
            if (msg2 != null) {
                key = true;
                System.out.println("I am " + getAgent().getLocalName()
                        + " a winner in a topic " + topic.getName());
            }
        }


    @Override
    public boolean done() {
        return key;
    }


}
