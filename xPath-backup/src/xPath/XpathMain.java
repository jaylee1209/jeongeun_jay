package xPath;

import java.io.File;
import java.io.IOException;
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

public class xPathMain {

	public static void main(String[] args) {
		Runtime.getRuntime().gc();
		
		long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), endMemory = 0;
		long start = System.currentTimeMillis();
		
		
		xPathMain sr = new xPathMain();
		int[] arrayFN = sr.searchBF();
		
		sr.searchSR(arrayFN);
		
		endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long end = System.currentTimeMillis();
		System.out.print("끝난시간!:");
		System.out.print((end - start)/1000.0 + "초");
		System.out.print(", 메모리 사용량 : " + String.format("%,d", (endMemory - startMemory) / 1000) + " kbyte");		
	}

	/**
	 * 해당 메소드는 5가지 일을 수행한다
	 * 1. F로 시작되는 xml 파일에서 SIMILAR_RATE 를 100으로 나누었을 때의 값이 15이상인것을찾는다.
	 * 2. P로 시작되는 xml 파일에서 ROW에 대한 정보를 모두 추출한다.
	 * 3. 파일의 경로를 지정해주고, 해당 폴더가 존재하지않으면은 생성하고 그렇지않으면은 이미 폴더가 있다는 메세지 출력
	 * 4. PID의 content를 찾아 key로 할당하고 ROW태그 전체를 value 로 할당한다.
	 * 5. P파일에서 PID를 찾고 하위 노드에 LICENSE 번호를 가져와 COMMENT태그에 쓴다
	 * 6. 작업이 끝나면은 파일을 생성해준다.
	 * 
	 * 해당 작업이 끝날 때마다 T로 시작되는 파일로 생성한다.
	 * 
	 * @param array
	 * @return
	 */
	private String[] searchSR(int[] array) {
		String arraypid[] = new String[146];		//146
		  try { 
		         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		         DocumentBuilder db = dbf.newDocumentBuilder();
		         Document doc = null;
		         Document doc1 = null;
		         XPath xp = XPathFactory.newInstance().newXPath();
		         
		         //3
		         String path = "C:\\Users\\meta\\eclipse-workspace\\xPath-backup2\\src\\data\\test";
		         File Folder = new File(path);
		         
		         if(!Folder.exists()) {
		        	 try {
						Folder.mkdir();
						 System.out.println("폴더가 생성되었습니다.");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         } else {
		        	 System.out.println("이미 폴더가 있습니다.");
		         }
		         //end of 3
		         
		         for(int i=0; i<array.length; i++) {
		        	 doc = db.parse("src\\data\\F_"+array[i]+"_TB.xml");
		        	 doc1 = db.parse("src\\data\\P_"+array[i]+"_TB.xml");
		        	 		        	 
		        	 //1
			         NodeList nl = (NodeList) xp.compile("//ROWS/ROW[SIMILAR_RATE div 100 > 15]").evaluate(doc, XPathConstants.NODESET);
			         //end of 1
			         
			         //2
			         NodeList nl1 = (NodeList) xp.compile("//ROWS/ROW").evaluate(doc1, XPathConstants.NODESET);
			         //end of 2
			         
			         Map<String, Node> pmap = new HashMap<String, Node>(); 
			         
			         //4
			         for(int k=0; k<nl1.getLength(); k++) {
			        	 Node xpnode = getNodeByTagName(nl1.item(k),"P_ID");
			        	 pmap.put(xpnode.getTextContent(), nl1.item(k));
			         }
			         //end of 4
			         
			         //5
			         for(int j=0; j<nl.getLength(); j++) {
			        	 Node xpnode = getNodeByTagName(nl.item(j),"P_ID");
			        	 
			        	 if(pmap.containsKey(xpnode.getTextContent())) {
			        		 Node nodeLIC = getNodeByTagName(pmap.get(xpnode.getTextContent()),"LICENSE_ID");
				        	
			        		 Node comment = getNodeByTagName(nl.item(j),"COMMENT");
				        	 comment.setTextContent(nodeLIC.getTextContent());
			        	 }
			         }
			         //end of 5
			         
			         //6
			         DOMSource xmlDOM = new DOMSource(doc);
			         StreamResult xmlFile = new StreamResult(new File("src\\data\\test\\T_"+array[i]+"_TB.xml"));
			         TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);
			         //end of 6
		         }
		         
		  	  } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | TransformerException | TransformerFactoryConfigurationError e) {
		         e.printStackTrace();
		      }

		  return arraypid;
	}	
	
	//basefile에서 파일 숫자 찾기
	private int[] searchBF() {
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
	
	//태그의 이름을 사용하여 노드찾기
	private Node getNodeByTagName(Node parent, String tagName)
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
