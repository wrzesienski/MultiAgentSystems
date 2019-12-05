package WorkWithXML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
@XmlAccessorType(XmlAccessType.FIELD)

public class Neighbor {
    @XmlAttribute
    private String AgentName;
    @XmlAttribute
    private Double Weight;

    public Neighbor() {
    }

    public Neighbor(String agentName, Double weight) {
        AgentName = agentName;
        Weight = weight;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    @Override
    public String toString() {
        return "Neighbor{" +
                "AgentName='" + AgentName + '\'' +
                ", Weight=" + Weight +
                '}';
    }
}
