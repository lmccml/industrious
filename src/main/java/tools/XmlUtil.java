package tools;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * xml工具类
 */
public class XmlUtil {
	/**
	 * xml转换成javaBean
	 * @param t
	 * @param xml
	 * @param encoding
	 * @return
	 */
	public static <T> T xmlToObj(Class<T> t, String xml, String encoding){
		try {
			JAXBContext jaxbContext= JAXBContext.newInstance(t);
			Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
			ByteArrayInputStream bais=new ByteArrayInputStream(xml.getBytes(encoding));
			@SuppressWarnings("unchecked")
			T obj=	(T) unmarshaller.unmarshal(bais);
			bais.close();
			return obj;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static Map<String, String> parseXmlStrList(String xmlStr) throws Exception {
		System.out.println("--------进入parseXmlStrList----------");
		// 将解析结果存储在HashMap中
		Map<String, Object> map = new HashMap<String, Object>();
		// 创建一个新的字符串   
	    StringReader xmlString = new StringReader(xmlStr);
	    // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入   
	    InputSource source = new InputSource(xmlString);
	    SAXBuilder saxb = new SAXBuilder();
	    org.jdom.Document document = null;
		try {
			document = saxb.build(source);
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		// 得到xml根元素
		Element root = document.getRootElement();
		System.out.println("--------已得到根元素----------");
		// 得到根元素的所有子节点
		List<Element> elementList = ((Element) root).getChildren();
		System.out.println("--------根元素的所有子节点----------"+elementList.size());
		Map<String, String> list=new HashMap<String,String>();
		// 遍历所有子节点
		for (Element e : elementList) {
			System.out.println("--------遍历所有子节点----------");
				
			list.put(e.getName(), e.getText());
			
		}
		System.out.println("--------返回list----------");
		return list;
	}
	
	public static <T> String objToXml(Class<T> t, Object object, String encoding){
//  StringWriter writer = new StringWriter();
  String xml="";
   try {
     JAXBContext jaxbContext= JAXBContext.newInstance(t);
     Marshaller marshaller = jaxbContext.createMarshaller();
     marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
     marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
     
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(baos, (String) marshaller.getProperty(Marshaller.JAXB_ENCODING));
     xmlStreamWriter.writeStartDocument((String) marshaller.getProperty(Marshaller.JAXB_ENCODING), "1.0");
     marshaller.marshal(object, xmlStreamWriter);
     xmlStreamWriter.writeEndDocument();
     xmlStreamWriter.close();
     xml= new String(baos.toByteArray(),encoding);
     
//     if (StringUtils.isNotBlank(encoding)) {
//         marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
//     }
//     marshaller.marshal(object, writer);
     
   } catch (JAXBException e) {
     e.printStackTrace();
   } catch (Exception e) {
     e.printStackTrace();
   }
   return xml;
//   return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+writer.toString();
 }


	/**
	 *
	 * 功能：对象转换成xml
	 *
	 * @param map 对象已使用jaxb标签标注
	 * @return
	 */
	public static String map2Xmlstring(Map map){
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");

		Set<String> set = map.keySet();
		for(Iterator<String> it = set.iterator(); it.hasNext();){
			String key = it.next();
			Object value = map.get(key);
			sb.append("<").append(key).append(">");
			sb.append(value);
			sb.append("</").append(key).append(">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
