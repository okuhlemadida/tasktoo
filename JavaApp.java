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
import java.util.List;

public class ReadXMLFile {
    public static void main(String[] args) {
        // Define the list of valid fields
        List<String> validFields = Arrays.asList("name", "postalZip", "region", "country", "address", "list");

        if (args.length == 0) {
            System.out.println("Please specify the fields to output.");
            System.out.println("Usage: java ReadXMLFile <field1> <field2> ...");
            System.out.println("Available fields: " + String.join(", ", validFields));
            return;
        }

        // Convert arguments to a set for easy lookup and validation
        Set<String> fieldsToPrint = new HashSet<>(Arrays.asList(args));

        // Validate user inputs
        for (String field : fieldsToPrint) {
            if (!validFields.contains(field)) {
                System.out.println("Invalid field: " + field);
                System.out.println("Available fields: " + String.join(", ", validFields));
                return;
            }
        }

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
                for (String field : fieldsToPrint) {
                    NodeList nodeList = recordElement.getElementsByTagName(field);
                    if (nodeList.getLength() > 0) {
                        String value = nodeList.item(0).getTextContent();
                        jsonObject.put(field, value);
                    } else {
                        // Provide a meaningful message for missing elements
                        jsonObject.put(field, "Field not found in XML");
                    }
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