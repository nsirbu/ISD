package com.settings;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.udp.helper.Constants;
import com.udp.io.Log4j;

/**
 * 
 * @author Nicolae
 *
 *         Read and set the server's configurations from the file.
 */
public class ConfigurationsManager {

	Logger log = Log4j.initLog4j(ConfigurationsManager.class);

	static String configFile = Constants.CONFIGURATION_FILE_PATH;

	/**
	 * Create server configuration file.
	 */
	public void createConfigurationFile() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("configurations");
			doc.appendChild(rootElement);

			Element dbName = doc.createElement("dbName");
			dbName.appendChild(doc.createTextNode("isd"));
			rootElement.appendChild(dbName);

			Element dbUser = doc.createElement("dbUser");
			dbUser.appendChild(doc.createTextNode("root"));
			rootElement.appendChild(dbUser);

			Element dbPassword = doc.createElement("dbPassword");
			dbPassword.appendChild(doc.createTextNode("nicolae"));
			rootElement.appendChild(dbPassword);

			Element lightThreshold = doc.createElement("lightThreshold");
			lightThreshold.appendChild(doc.createTextNode("115"));
			rootElement.appendChild(lightThreshold);

			Element HBFrequency = doc.createElement("HBFrequency");
			HBFrequency.appendChild(doc.createTextNode("60"));
			rootElement.appendChild(HBFrequency);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(configFile));
			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * Set configuration data to the configuration file.
	 * 
	 * @param field
	 *            the resource that interests us
	 * @param value
	 *            the resource value
	 */
	public void setConfigValue(String element, String value) {
		try {
			String filepath = configFile;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Node rootElement = doc.getElementsByTagName("configurations").item(0);

			NodeList list = rootElement.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);

				if (element.equals(node.getNodeName())) {
					node.setTextContent(value);
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	/**
	 * Add new configuration data to the configuration file.
	 * 
	 * @param field
	 *            the resource name to add
	 * @param value
	 *            the resource value
	 */
	public void addProperty(String element, String value) {
		try {
			String filepath = configFile;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Node rootElement = doc.getElementsByTagName("configurations").item(0);

			Element newProperty = doc.createElement(element);
			newProperty.appendChild(doc.createTextNode(value));
			rootElement.appendChild(newProperty);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	/**
	 * Get from the configuration file the value for the specified field.
	 * 
	 * @param field
	 *            the resource that interests us
	 * @return the value of the resource
	 */
	public String readConfigValue(String element) {
		String propertyValue = "";
		try {
			String filepath = configFile;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Node rootElement = doc.getElementsByTagName("configurations").item(0);

			NodeList list = rootElement.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);

				if (element.equals(node.getNodeName())) {
					propertyValue = node.getTextContent();
				}
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}

		return propertyValue;
	}
}
