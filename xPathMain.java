package xPath;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathMain {

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		
		xmlXPath sr = new xmlXPath();
		int[] arrayFN = sr.searchBF();
		
		sr.searchSR(arrayFN);
		
		
		long end = System.currentTimeMillis();
		System.out.print("끝난시간!:");
		System.out.println((end - start)/1000.0 + "초");
		
		
	}

}