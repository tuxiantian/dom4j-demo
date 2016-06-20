package tuxt.xml.demo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class MyVisitor extends VisitorSupport{
	List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
	 public List<Map<String, Object>> getData() {
		return data;
	}
	public void visit(Element element){
         System.out.println(element.getName());
         System.out.println(element.getData());
		if (element.getName().equals("cid")) {
			data.add((Map<String, Object>) new HashMap<String, Object>().put(element.getName(), element.getData()));
		}else if (element.getName().equals("Address")) {
			data.add((Map<String, Object>) new HashMap<String, Object>().put(element.getName(), element.getData()));
		}else if (element.getName().equals("PhoneNumber")) {
			data.add((Map<String, Object>) new HashMap<String, Object>().put(element.getName(), element.getData()));
		}else if (element.getName().equals("FaxNumber")) {
			data.add((Map<String, Object>) new HashMap<String, Object>().put(element.getName(), element.getData()));
		}else if (element.getName().equals("FaxNumber")) {
			data.add((Map<String, Object>) new HashMap<String, Object>().put(element.getName(), element.getData()));
		}else if (element.getName().equals("Email")) {
			data.add((Map<String, Object>) new HashMap<String, Object>().put(element.getName(), element.getData()));
		}else {
			
		}
		
     }
     public void visit(Attribute attr){
         System.out.println(attr.getName());
         //System.out.println(attr.getData());
     }
}
