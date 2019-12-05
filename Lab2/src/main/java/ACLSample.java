import jade.core.AID;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

    class ACLSample extends ACLMessage{

        ACLSample(String a, String b, AID c) {

            setContent(a);
            setProtocol(b);
            addReceiver(c);
        }

        ACLSample(String nameAgent, String[] a, String b, DFAgentDescription[] c) {

            setProtocol(b);

            for (DFAgentDescription foundAgent : c) {
                if (!foundAgent.getName().getLocalName().equals(nameAgent)) {
                    addReceiver(foundAgent.getName());

                }
            }

            StringBuilder content = new StringBuilder();

            for (String s : a) {
                content.append(s).append(";");
            }
            setContent(content.toString());

        }

    }
