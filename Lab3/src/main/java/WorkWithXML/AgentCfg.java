package WorkWithXML;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class AgentCfg {
    @XmlElement
    private  String name;
    @XmlElementWrapper(name="neighbors")
    @XmlElement(name="neighbor")
    private List<Neighbor> neighbors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Neighbor> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Neighbor> neighbors) {
        this.neighbors = neighbors;
    }
}
