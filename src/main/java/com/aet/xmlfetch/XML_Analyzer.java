package com.aet.xmlfetch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author hirosh.t
 * @version 1.1
 */
public class XML_Analyzer {

	static int count_mismatchElements = 0;
	static int count_matchElements = 0;
	static int count_allElements = 0;

	static int count_matchApplications = 0;
	static int count_mismatchApplications = 0;
	static int count_partiallyMatchApplications = 0;

	public static void setCount_allElements(int count_alloptions) {
		XML_Analyzer.count_allElements = count_alloptions;
	}

	public static void setCount_mismatchElements(int count_mismatch) {
		XML_Analyzer.count_mismatchElements = count_mismatch;
	}

	public static void setCount_matchElements(int count_match) {
		XML_Analyzer.count_matchElements = count_match;
	}

	public static void setCount_matchApplications(int count_matchApplications) {
		XML_Analyzer.count_matchApplications = count_matchApplications;
	}

	public static void setCount_mismatchApplications(int count_mismatchApplications) {
		XML_Analyzer.count_mismatchApplications = count_mismatchApplications;
	}

	public static void setCount_partiallyMatchApplications(int count_partiallyMatchApplications) {
		XML_Analyzer.count_partiallyMatchApplications = count_partiallyMatchApplications;
	}

	public static void main(String[] args) {

		System.out.println("------------- XML_Analyzer ---------------");

		try {
			String xmlUrl = "D:/Project Work/XML Fetcher/sampleXMLnew.xml";
			String csvUrl = "D:/Project Work/XML Fetcher/sampleCSVnew.csv";

			// for CSV seperation details
			String line = "";
			String cvsSplitBy = ",";

			File fXmlFile = new File(xmlUrl);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("CAPS_Application_Details");

			System.out.println("--------------------------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				int count = temp + 1;

				// use 0 if reading from one row.
				// use 'temp' if reading from all rows
				String csvRowLine = Files.readAllLines(Paths.get(csvUrl)).get(1);
				String[] csvRowLineElements = csvRowLine.split(cvsSplitBy);

				System.out.println("\nCurrent Element :" + nNode.getNodeName() + " / " + count);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					// ACTUAL DATA : Data inbetween the CAPS_Applicant_Details
					String xmlLastName = eElement.getElementsByTagName("Last_Name").item(0).getTextContent();
					String xmlFirstName = eElement.getElementsByTagName("First_Name").item(0).getTextContent();
					String xmlMiddleName1 = eElement.getElementsByTagName("Middle_Name1").item(0).getTextContent();
					String xmlMiddleName2 = eElement.getElementsByTagName("Middle_Name2").item(0).getTextContent();
					String xmlMiddleName3 = eElement.getElementsByTagName("Middle_Name3").item(0).getTextContent();
					String xmlFlatNoPlotNoHouseNo = eElement.getElementsByTagName("FlatNoPlotNoHouseNo").item(0)
							.getTextContent();
					String xmlBldgNoSocietyName = eElement.getElementsByTagName("BldgNoSocietyName").item(0)
							.getTextContent();
					String xmlRoadNoNameAreaLocality = eElement.getElementsByTagName("RoadNoNameAreaLocality").item(0)
							.getTextContent();
					String xmlCity = eElement.getElementsByTagName("City").item(0).getTextContent();
					String xmlLandmark = eElement.getElementsByTagName("Landmark").item(0).getTextContent();
					String xmlState = eElement.getElementsByTagName("State").item(0).getTextContent();
					String xmlPINCode = eElement.getElementsByTagName("PINCode").item(0).getTextContent();
					String xmlCountry_Code = eElement.getElementsByTagName("Country_Code").item(0).getTextContent();

					// EXPECTED DATA : Data from the csv file
					String csvLastName = csvRowLineElements[0];
					String csvFirstName = csvRowLineElements[1];
					String csvMiddleName1 = csvRowLineElements[2];
					String csvMiddleName2 = csvRowLineElements[3];
					String csvMiddleName3 = csvRowLineElements[4];
					String csvFlatNoPlotNoHouseNo = csvRowLineElements[5];
					String csvBldgNoSocietyName = csvRowLineElements[6];
					String csvRoadNoNameAreaLocality = csvRowLineElements[7];
					String csvCity = csvRowLineElements[8];
					String csvLandmark = csvRowLineElements[9];
					String csvState = csvRowLineElements[10];
					String csvPINCode = csvRowLineElements[11];
					String csvCountry_Code = csvRowLineElements[12];

					boolean matchLastName = compareStringAndThrowWarning(xmlLastName, csvLastName, "Last_Name");
					boolean matchFirstName = compareStringAndThrowWarning(xmlFirstName, csvFirstName, "First_Name");
					boolean matchMiddleName1 = compareStringAndThrowWarning(xmlMiddleName1, csvMiddleName1,
							"Middle_Name1");
					compareStringAndThrowWarning(xmlMiddleName2, csvMiddleName2, "Middle_Name2");
					compareStringAndThrowWarning(xmlMiddleName3, csvMiddleName3, "Middle_Name3");

					boolean matchFlatNoPlotNoHouseNo = compareStringAndThrowWarning(xmlFlatNoPlotNoHouseNo,
							csvFlatNoPlotNoHouseNo, "FlatNoPlotNoHouseNo");
					compareStringAndThrowWarning(xmlBldgNoSocietyName, csvBldgNoSocietyName, "BldgNoSocietyName");
					compareStringAndThrowWarning(xmlRoadNoNameAreaLocality, csvRoadNoNameAreaLocality,
							"RoadNoNameAreaLocality");
					boolean matchCity = compareStringAndThrowWarning(xmlCity, csvCity, "City");
					compareStringAndThrowWarning(xmlLandmark, csvLandmark, "Landmark");
					boolean matchState = compareStringAndThrowWarning(xmlState, csvState, "State");
					boolean matchPinCode = compareStringAndThrowWarning(xmlPINCode, csvPINCode, "csvPINCode");
					compareStringAndThrowWarning(xmlCountry_Code, csvCountry_Code, "Country_Code");

					if (matchLastName == true && matchFirstName == true && matchFlatNoPlotNoHouseNo == true
							&& matchCity == true && matchState == true && matchPinCode == true) {
						// Fully Matched Application
						System.out.println("**Matched");
						setCount_matchApplications(count_matchApplications + 1);
					} else if (matchLastName == matchFirstName == matchFlatNoPlotNoHouseNo == true
							&& matchCity == matchState == matchPinCode == false) {
						// Partially Matched Application
						System.out.println("**Partially");
						setCount_partiallyMatchApplications(count_partiallyMatchApplications + 1);
					} else {
						// Mismatched Application
						System.out.println("**Mismatched");
						setCount_mismatchApplications(count_mismatchApplications + 1);
					}
				}
			}

			System.out.println("\n------------------ Summary -------------------");
			System.out.println("  Matched Application count : " + count_matchApplications);
			System.out.println("  Mismatched Application count : " + count_mismatchApplications);
			System.out.println("  Partially Matched Applcation count : " + count_partiallyMatchApplications);
			System.out.println("----------------------------------------------");
			System.out.println("  Total element count : " + count_allElements);
			System.out.println("  Total mismatch elements count : " + count_mismatchElements);
			System.out.println("  Total match elements count : " + count_matchElements);
			System.out.println("----------------------------------------------");

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean compareStringAndThrowWarning(String actual, String expected, String variableName) {

		setCount_allElements(count_allElements + 1);
		if (!actual.equalsIgnoreCase(expected)) {
			setCount_mismatchElements(count_mismatchElements + 1);
			System.out.println(" --- ALERT! : " + variableName + "  \"" + actual
					+ "\" in the XML is not matching with the expected \"" + expected + "\"");
			return false;
		} else if (actual.equalsIgnoreCase(expected)) {
			setCount_matchElements(count_matchElements + 1);
		}
		return true;
	}

}

// System.out.println("XML Last_Name : " + xmlLastName);
// System.out.println("XML First_Name : " + xmlFirstName);
// System.out.println("XML Middle_Name1 : " +
// xmlMiddleName1);
// System.out.println("XML Middle_Name2 : " +
// xmlMiddleName2);
// System.out.println("XML Middle_Name3 : " +
// xmlMiddleName3);
//
// System.out.println("CSV Last_Name : " + csvLastName);
// System.out.println("CSV First_Name : " + csvFirstName);
// System.out.println("CSV Middle_Name1 : " +
// csvMiddleName1);
// System.out.println("CSV Middle_Name2 : " +
// csvMiddleName2);
// System.out.println("CSV Middle_Name3 : " +
// csvMiddleName3);
