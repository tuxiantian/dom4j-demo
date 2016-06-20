package tuxt.xml.demo;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Visitor;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Assert;
import org.junit.Test;

public class XmlDemo {
	public Document read(String fileName) throws MalformedURLException, DocumentException {
		SAXReader reader = new SAXReader();
		URL xmlpath = this.getClass().getClassLoader().getResource(fileName);
		Document document = reader.read(xmlpath);
		return document;
	}
	public Document read2(String fileName) throws DocumentException{
		SAXReader reader = new SAXReader();
		File file=new File(fileName);
		Document document = reader.read(file);
		return document;
	}

	  /**
     * 修改XML文件中内容,并另存为一个新文件
     * 重点掌握dom4j中如何添加节点,修改节点,删除节点
     * @param filename 修改对象文件
     * @param newfilename 修改后另存为该文件
     * @return 返回操作结果, 0表失败, 1表成功
     */
    public int modiXMLFile(String filename,String newfilename){
       int returnValue = 0;
       try{
           SAXReader saxReader = new SAXReader();
           Document document = saxReader.read(new File(filename));
           /** 修改内容之一: 如果book节点中show属性的内容为yes,则修改成no */
           /** 先用xpath查找对象 */
           List list = document.selectNodes("/books/book/@show" );
           Iterator iter = list.iterator();
           while(iter.hasNext()){
              Attribute attribute = (Attribute)iter.next();
              if(attribute.getValue().equals("yes")){
                  attribute.setValue("no");
              }  
           }
          
           /**
            * 修改内容之二: 把owner项内容改为Tshinghua
            * 并在owner节点中加入date节点,date节点的内容为2004-09-11,还为date节点添加一个属性type
            */
           list = document.selectNodes("/books/owner" );
           iter = list.iterator();
           if(iter.hasNext()){
              Element ownerElement = (Element)iter.next();
              ownerElement.setText("Tshinghua");
              Element dateElement = ownerElement.addElement("date");
              dateElement.setText("2004-09-11");
              dateElement.addAttribute("type","Gregorian calendar");
           }
          
           /** 修改内容之三: 若title内容为Dom4j Tutorials,则删除该节点 */
           list = document.selectNodes("/books/book");
           iter = list.iterator();
           while(iter.hasNext()){
              Element bookElement = (Element)iter.next();
              Iterator iterator = bookElement.elementIterator("title");
              while(iterator.hasNext()){
                  Element titleElement=(Element)iterator.next();
                  if(titleElement.getText().equals("Dom4j Tutorials")){
                     bookElement.remove(titleElement);
                  }
              }
           }         
          
           try{
              /** 将document中的内容写入文件中 */
              XMLWriter writer = new XMLWriter(new FileWriter(new File(newfilename)));
              writer.write(document);
              writer.close();
              /** 执行成功,需返回1 */
              returnValue = 1;
           }catch(Exception ex){
              ex.printStackTrace();
           }
          
       }catch(Exception ex){
           ex.printStackTrace();
       }
       return returnValue;
    }
	@Test
	public void test(){
	String	filename="contact1.xml";
	createXMLFile(filename);
	}
	/**
     * 建立一个XML文档,文档名由输入属性决定
     * @param filename 需建立的文件名
     * @return 返回操作结果, 0表失败, 1表成功
     */
    public int createXMLFile(String filename){
       /** 返回操作结果, 0表失败, 1表成功 */
       int returnValue = 0;
       /** 建立document对象 */
       Document document = DocumentHelper.createDocument();
       /** 建立XML文档的根books */
       Element booksElement = document.addElement("books");
       /** 加入一行注释 */
       booksElement.addComment("This is a test for dom4j, holen, 2004.9.11");
       /** 加入第一个book节点 */
       Element bookElement = booksElement.addElement("book");
       /** 加入show属性内容 */
       bookElement.addAttribute("show","yes");
       /** 加入title节点 */
       Element titleElement = bookElement.addElement("title");
       /** 为title设置内容 */
       titleElement.setText("Dom4j Tutorials");
      
       /** 类似的完成后两个book */
       bookElement = booksElement.addElement("book");
       bookElement.addAttribute("show","yes");
       titleElement = bookElement.addElement("title");
       titleElement.setText("Lucene Studing");
       bookElement = booksElement.addElement("book");
       bookElement.addAttribute("show","no");
       titleElement = bookElement.addElement("title");
       titleElement.setText("Lucene in Action");
      
       /** 加入owner节点 */
       Element ownerElement = booksElement.addElement("owner");
       ownerElement.setText("O'Reilly");
      
       try{
           /** 将document中的内容写入文件中,默认是压缩格式书写 */
         /*  XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)));
           writer.write(document);
           writer.close();*/
           // 美化格式
           OutputFormat format = OutputFormat.createPrettyPrint();
           OutputStream o=new FileOutputStream(new File(this.getClass().getClassLoader().getResource(filename).getPath()));
           format.setEncoding("utf-8");           
           XMLWriter  writer = new XMLWriter(o, format );
           writer.write( document );
           /** 执行成功,需返回1 */
           returnValue = 1;
       }catch(Exception ex){
           ex.printStackTrace();
       }
             
       return returnValue;
    }
	
	@Test
	public void test4() throws MalformedURLException, DocumentException {
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		Document document=read("ContactXMLFile.xml");
		Element root=getRootElement(document);
		List<Node> list= document.selectNodes("//Info");
		int i=1;
		for (Node node : list) {
			Map<String, Object> map=new HashMap<String, Object>();

			List<Node> list2=node.selectNodes(node.getPath()+"[@id='"+i+"']/*");
			for (Node node2 : list2) {
				map.put(node2.getName(), node2.getText());
			}
			data.add(map);
			i++;
		}
		for (Iterator<Map<String, Object>> iterator = data.iterator(); iterator.hasNext();) {
			Map<String, Object> type = (Map<String, Object>) iterator.next();
			Set<?>	set=type.keySet();
			for (Object str : set) {
				System.out.println(str+"="+type.get(str));
			}
			/*for (Iterator<Entry<String, Object>> iterator2 = type.entrySet().iterator(); iterator2
					.hasNext();) {
				Entry<String, Object> e = iterator2.next(); 
				System.out.println(e.getKey()+"="+e.getValue());				
			}*/
		}
	}
	@Test
	public void testRepairAndSave() throws MalformedURLException, DocumentException {
		String cronExpression="0/5 * * * * ?";
		/**
		 * 修改
		 */
		Document document=read("spring-quartz.xml");
		Element root=getRootElement(document);
		Iterator<Element> iterator=root.elementIterator();
		while (iterator.hasNext()) {
			Element bean=iterator.next();
			if ("testTrigger1".equals(bean.attribute("id").getText())) {
				List<Element> elements=	bean.elements("property");
				for (Element element : elements) {
					if ("cronExpression".equals(element.attributeValue("name"))) {
						element.element("value").setText(cronExpression);
						System.out.println(element.elementText("value"));
					}
				}
			}

		}
		/**
		 * 保存
		 */
		 OutputFormat format = OutputFormat.createPrettyPrint();
         OutputStream o = null;
		try {
			o = new FileOutputStream(new File(this.getClass().getClassLoader().getResource("spring-quartz.xml").getPath()));
			System.out.println(this.getClass().getClassLoader().getResource("spring-quartz.xml").getPath());
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
         format.setEncoding("utf-8");           
         XMLWriter writer = null;
		try {
			writer = new XMLWriter(o, format );
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
         try {
			writer.write( document );
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
         /**
          * 查询测试
          */
         document=read("spring-quartz.xml");
 		root=getRootElement(document);
 		iterator=root.elementIterator();
 		while (iterator.hasNext()) {
 			Element bean=iterator.next();
 			if ("testTrigger1".equals(bean.attribute("id").getText())) {
 				List<Element> elements=	bean.elements("property");
 				for (Element element : elements) {
 					if ("cronExpression".equals(element.attributeValue("name"))) {
 						Assert.assertEquals(cronExpression, element.elementText("value"));
 					}
 				}
 			}

 		}
	}
	private Element getRootElement(Document document) {
		return document.getRootElement();
	}
	@Test
	public void test3() throws MalformedURLException, DocumentException {
		Document document=read("ContactXMLFile.xml");
		Element root=getRootElement(document);
		Node node = document.selectSingleNode("//Info/Address");
		System.out.println( node.getText());
		for (Object n : document.selectNodes("//Info/Address").toArray()) {
			System.out.println( ((Node) n).getText());
			System.out.println( ((Node) n).valueOf("@id"));
		}

	}
	@Test
	public void test2() throws MalformedURLException, DocumentException {
		Document document=read("ContactXMLFile.xml");
		Element root=getRootElement(document);
		MyVisitor myVisitor=new MyVisitor();
		root.accept(new MyVisitor());
		for (Iterator<Map<String, Object>> iterator = myVisitor.getData().iterator(); iterator.hasNext();) {
			Map<String, Object> type = (Map<String, Object>) iterator.next();
			for (Iterator<Entry<String, Object>> iterator2 = type.entrySet().iterator(); iterator2
					.hasNext();) {
				Entry<String, Object> e = iterator2.next(); 
				System.out.println(e.getKey()+"="+e.getValue());				
			}
		}
	}
	@Test
	public void test1() throws MalformedURLException, DocumentException {
		Document document=read("ContactXMLFile.xml");
		Element root=getRootElement(document);
		System.out.println(root.getName());
		
		//xml元素及元素属性的遍历
		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
			Element element = (Element) i.next();
			System.out.println(element.getName());
			for ( Iterator ii = element.attributeIterator(); ii.hasNext(); ) {
				Attribute attribute = (Attribute) ii.next();
				System.out.println(attribute.getData());
			}
		}
		
		for ( Iterator i = root.elementIterator("Info"); i.hasNext();) {
			Element foo = (Element) i.next();
			System.out.println(foo.getName());		        
		}
	}
}
