import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;
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
            // Create a SAX parser instance
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            // Set the custom handler
            UserHandler userHandler = new UserHandler(fieldsToPrint);
            xmlReader.setContentHandler(userHandler);

            // Parse the XML file
            xmlReader.parse("records.xml");

            // Get the JSON array from the handler and print it
            JSONArray jsonArray = userHandler.getJsonArray();
            System.out.println(jsonArray.toString(4)); // Pretty print with an indentation of 4 spaces
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserHandler extends DefaultHandler {
    private Set<String> fieldsToPrint;
    private JSONArray jsonArray = new JSONArray();
    private JSONObject currentRecord;
    private StringBuilder currentValue;
    private boolean isValidElement;

    public UserHandler(Set<String> fieldsToPrint) {
        this.fieldsToPrint = fieldsToPrint;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            currentRecord = new JSONObject();
        } else if (fieldsToPrint.contains(qName)) {
            currentValue = new StringBuilder();
            isValidElement = true;
        } else {
            isValidElement = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isValidElement) {
            currentValue.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            jsonArray.put(currentRecord);
        } else if (fieldsToPrint.contains(qName)) {
            if (currentValue.length() > 0) {
                currentRecord.put(qName, currentValue.toString());
            } else {
                currentRecord.put(qName, "Field not found in XML");
            }
        }
    }

}