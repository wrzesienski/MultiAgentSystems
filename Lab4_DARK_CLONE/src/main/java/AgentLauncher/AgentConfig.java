package AgentLauncher;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.FIELD)
public class AgentConfig {
    @XmlElement(name = "agentDefinition")
    @XmlElementWrapper(name = "agents")
    private List<AgentDefinition> agentDefinitions;

    public List<AgentDefinition> getAgentDefinitions() {
        return agentDefinitions;
    }
}
