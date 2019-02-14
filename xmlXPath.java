package xPath;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xmlXPath {
	
	public String[] searchSR(int[] array) {
		System.out.println("SERVICE RATE 가지고 오자" + Arrays.toString(array));
		String arraypid[] = new String[146];		//146
		  try { 
		         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		         DocumentBuilder db = dbf.newDocumentBuilder();
		         Document doc = null;
		         Document doc1 = null;
		         XPath xp = XPathFactory.newInstance().newXPath();
		         
		         for(int i=0; i<array.length; i++) {
		        	 doc = db.parse("src\\data\\F_"+array[i]+"_TB.xml");
		        	 doc1 = db.parse("src\\data\\P_"+array[i]+"_TB.xml");
		        	 
//		        	 System.out.println("doc의 갑" + doc.getDocumentURI());
//		        	 System.out.println("doc1의 값" + doc1.getDocumentURI());
		        	 
			         NodeList nl = (NodeList) xp.compile("//ROWS/ROW[SIMILAR_RATE div 100 > 15]").evaluate(doc, XPathConstants.NODESET);
//			         System.out.println("노드의 길이는? " +nl.getLength());
			         
			         NodeList nl1 = (NodeList) xp.compile("//ROWS/ROW").evaluate(doc1, XPathConstants.NODESET);
			         
			         Map<String, Node> pmap = new HashMap<String, Node>(); 
			         
			         for(int k=0; k<nl1.getLength(); k++) {
			        	 Node xpnode = getNodeByTagName(nl1.item(k),"P_ID");
			        	 pmap.put(xpnode.getTextContent(), nl1.item(k));
			         }
			         
			         for(int j=0; j<nl.getLength(); j++) {
			        	 Node xpnode = getNodeByTagName(nl.item(j),"P_ID");
			        	 
			        	 if(pmap.containsKey(xpnode.getTextContent())) {
			        		 Node nodeLIC = getNodeByTagName(pmap.get(xpnode.getTextContent()),"LICENSE_ID");
				        	
			        		 Node comment = getNodeByTagName(nl.item(j),"COMMENT");
				        	 comment.setTextContent(nodeLIC.getTextContent());
			        	 }
			         }
			        
			         
			         DOMSource xmlDOM = new DOMSource(doc);
			         StreamResult xmlFile = new StreamResult(new File("src\\data\\test\\T_"+array[i]+"_TB.xml"));
			         TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);
			         
			         
		         }
		         
		  	  } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | TransformerException | TransformerFactoryConfigurationError e) {
		         e.printStackTrace();
		      }

		  return arraypid;
	}	
	
	//basefile에서 파일 숫자 찾기
	public int[] searchBF() {
		String val = "";
		int[] array = new int[112];
		
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = null;

			doc = builder.parse("src\\data\\T_BASEFILE_TB.xml");
			
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression srexpr = xpath.compile("//ROWS/ROW");
			
			Object srresult = (NodeList) srexpr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) srresult;

				for(int i=0; i<=nodes.getLength()-1; i++) {
					val = nodes.item(i).getChildNodes().item(1).getChildNodes().item(0).getTextContent();
//					System.out.println(nodes.item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
					
//					System.out.println(i + " 값 " +val);
					
					array[i] = Integer.parseInt(val);
				}
			
//			System.out.println(Arrays.toString(array));
			
		} catch (NumberFormatException | XPathExpressionException | DOMException | ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	
	public Node getNodeByTagName(Node parent, String tagName)
	{
		Node result = null;
		
		NodeList nl = parent.getChildNodes();
		
		for(int i=0; i < nl.getLength(); i++)
		{
			Node node = nl.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE && tagName.compareTo(node.getNodeName()) == 0)
			{
				result = nl.item(i);
				break;
			}
		}
		
		return result;
	}
	
}
