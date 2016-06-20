package tuxt.xml.demo;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class XmlDemoTest {
	static XmlDemo xml;
	@BeforeClass
	public static void beforeClass() {
		xml=new XmlDemo();
	}
	@Test
	public void testModiXMLFile() {
		String filename="E:/eclipse/dom4j-demo/src/main/resources/contact.xml",
				newfilename="E:/eclipse/dom4j-demo/src/main/resources/contact1.xml";
		xml.modiXMLFile(filename, newfilename);
	}

}
