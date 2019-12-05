package WorkWithXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class let to work with creating and parsing XML's
 */
public class WorkWithCfgs {




    /**
     * Method let's to parse parameters out XML file.
     * @param outPutFileName actual name of agent using the class
     * @return parsed parameters list of agent's neighbors
     */

    public static String[][] unMarshalAny(String outPutFileName) {
        String[][] strings = new String[0][];
        AgentCfg object;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(AgentCfg.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object obj = jaxbUnmarshaller.unmarshal(new File(outPutFileName));
            object = (AgentCfg) obj;

            int i = 0;
            int num = object.getNeighbors().size();

            strings = new String[num][2];
            for (Neighbor obb: object.getNeighbors()){
                strings[i][0] = obb.getAgentName();
                strings[i][1] = String.valueOf(obb.getWeight());
//                System.out.println(obb.getAgentName());
//                strings.add(obb.getAgentName());
//                strings.add(obb.getWeight().toString());
                i++;

            }
        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
        return strings;
    }

    /**
     *
     * @param clazz
     * @param information
     * @param outPutFileName
     * @param <T>
     */
    public static <T> void marshalAny(Class<T> clazz, T information, String outPutFileName) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(information, new File(outPutFileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
