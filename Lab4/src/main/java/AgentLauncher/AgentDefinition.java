package AgentLauncher;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class AgentDefinition {
	@XmlAttribute
	private String agentName = null;
	@XmlAttribute
	private String className = null;
	@XmlAttribute
	private ArrayList<Double> filepath = new ArrayList<>();

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ArrayList<Double> getFilepath() {
		return filepath;
	}

	public void setFilepath(ArrayList<Double> filepath) {
		this.filepath = filepath;
	}
}
