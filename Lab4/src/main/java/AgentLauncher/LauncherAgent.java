package AgentLauncher;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;


public class LauncherAgent extends Agent {

    @Override
    protected void setup() {
        String path = getArguments()[0].toString();
        AgentConfig agentConfig = WorkWithConfigurationFiles.unMarshalAny(AgentConfig.class, path);

        for (AgentDefinition agentDefinition : agentConfig.getAgentDefinitions()) {
            try {
                AgentController createdAgent = getContainerController().createNewAgent(
                        agentDefinition.getAgentName(),
                        agentDefinition.getClassName(),
                        new Object[]{agentDefinition.getFilepath()});
                createdAgent.start();

            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }
}
