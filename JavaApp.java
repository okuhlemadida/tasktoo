import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXMLFile {
    public static void main(String[] args) {
        try {
            // File path to the XML file
            File xmlFile = new File("records.xml");
            
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            
            // Create a DocumentBuilder
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            // Parse the XML file and get the Document object
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            // Get all <record> elements
            NodeList recordList = doc.getElementsByTagName("record");
            
            // Iterate over each <record> element
            for (int i = 0; i < recordList.getLength(); i++) {
                Element recordElement = (Element) recordList.item(i);
                
                // Get and print <name> element
                String name = recordElement.getElementsByTagName("name").item(0).getTextContent();
                System.out.println("Name: " + name);
                
                // Get and print <postalZip> element
                String postalZip = recordElement.getElementsByTagName("postalZip").item(0).getTextContent();
                System.out.println("Postal Zip: " + postalZip);
                
                // Get and print <region> element
                String region = recordElement.getElementsByTagName("region").item(0).getTextContent();
                System.out.println("Region: " + region);
                
                // Get and print <country> element
                String country = recordElement.getElementsByTagName("country").item(0).getTextContent();
                System.out.println("Country: " + country);
                
                // Get and print <address> element
                String address = recordElement.getElementsByTagName("address").item(0).getTextContent();
                System.out.println("Address: " + address);
                
                // Get and print <list> element
                String list = recordElement.getElementsByTagName("list").item(0).getTextContent();
                System.out.println("List: " + list);
                
                // Print a blank line for better readability
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}