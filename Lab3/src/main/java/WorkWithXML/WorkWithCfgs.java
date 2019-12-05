package WorkWithXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.rmi.UnmarshalException;

public class WorkWithCfgs {

    public static <T> T unMarshalAny(Class<T> clazz, String outPutFileName) {
        T object = null;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object obj = jaxbUnmarshaller.unmarshal(new File(outPutFileName));
            object = (T) obj;
        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
        return object;
    }

//                WorkWithCfgs.marshalAny(AgentCfg.class, agentcfg, "A" + i + ".xml");

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
