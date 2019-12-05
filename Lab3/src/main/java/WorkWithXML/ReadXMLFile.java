package WorkWithXML;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Arrays;

@Deprecated
public class ReadXMLFile {



    /**
     * Old fashioned mean to parse xml file.
     * @param agentName actual name of agent using the class
     * @return parsed parameters list of agent's neighbors
     */
    public static String[][] open(String agentName) {


        String[][] lists = new String[0][];

        try {
            File fXmlFile = new File(agentName + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("neighbor");
//            System.out.println("----------------------------");
            lists = new String[nList.getLength()][2];


            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    lists[temp][0] = eElement.getAttribute("agentName");
//                    System.out.println("Staff id : "
//                            + eElement.getAttribute("agentName"));
                    lists[temp][1] = eElement.getAttribute("weight");
//                    System.out.println("Staff weight : "
//                            + eElement.getAttribute("weight"));

                }
//                System.out.println(Arrays.deepToString(lists));
            }
//            System.out.println("matrix:\n"  + Arrays.deepToString(lists));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }


    }


