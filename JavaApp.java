import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReadXMLFile {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify the fields to output.");
            System.out.println("Usage: java ReadXMLFile <field1> <field2> ...");
            System.out.println("Available fields: name, postalZip, region, country, address, list");
            return;
        }

        // Convert arguments to a set for easy lookup
        Set<String> fieldsToPrint = new HashSet<>(Arrays.asList(args));

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
            
            // Create a JSON array to hold all records
            JSONArray jsonArray = new JSONArray();
            
            // Iterate over each <record> element
            for (int i = 0; i < recordList.getLength(); i++) {
                Element recordElement = (Element) recordList.item(i);
                
                // Create a JSON object for each record
                JSONObject jsonObject = new JSONObject();
                
                // Conditionally add fields based on user input
                if (fieldsToPrint.contains("name")) {
                    String name = recordElement.getElementsByTagName("name").item(0).getTextContent();
                    jsonObject.put("name", name);
                }
                
                if (fieldsToPrint.contains("postalZip")) {
                    String postalZip = recordElement.getElementsByTagName("postalZip").item(0).getTextContent();
                    jsonObject.put("postalZip", postalZip);
                }
                
                if (fieldsToPrint.contains("region")) {
                    String region = recordElement.getElementsByTagName("region").item(0).getTextContent();
                    jsonObject.put("region", region);
                }
                
                if (fieldsToPrint.contains("country")) {
                    String country = recordElement.getElementsByTagName("country").item(0).getTextContent();
                    jsonObject.put("country", country);
                }
                
                if (fieldsToPrint.contains("address")) {
                    String address = recordElement.getElementsByTagName("address").item(0).getTextContent();
                    jsonObject.put("address", address);
                }
                
                if (fieldsToPrint.contains("list")) {
                    String list = recordElement.getElementsByTagName("list").item(0).getTextContent();
                    jsonObject.put("list", list);
                }

                // Add the JSON object to the JSON array
                jsonArray.put(jsonObject);
            }
            
            // Print the JSON array
            System.out.println(jsonArray.toString(4)); // Pretty print with an indentation of 4 spaces
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}