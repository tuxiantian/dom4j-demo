package tuxt.xml.demo;


import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlDemo1 {

	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
		File file = new File("E:/eclipse/dom4j-demo/src/main/resources/books.xml");
		Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		List<Element> childElements = root.elements();
		for (Element child : childElements) {
			//未知属性名情况下
			/*List<Attribute> attributeList = child.attributes();
		   for (Attribute attr : attributeList) {
		    System.out.println(attr.getName() + ": " + attr.getValue());
		   }*/

			//已知属性名情况下
			System.out.println("id: " + child.attributeValue("id"));

			//未知子元素名情况下
			/*List<Element> elementList = child.elements();
		   for (Element ele : elementList) {
		    System.out.println(ele.getName() + ": " + ele.getText());
		   }
		   System.out.println();*/

			//已知子元素名的情况下
			System.out.println("title" + child.elementText("title"));
			System.out.println("author" + child.elementText("author"));
			System.out.println("content" + child.element("content").attributeValue("name"));
			//这行是为了格式化美观而存在
			System.out.println();
		}
	}

}
