package com.example.xmlpullparser;
/*
 * http://www.anddev.org/write_a_simple_xml_file_in_the_sd_card_using_xmlserializer-t8350.html
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLPullParserHandler {
	 List<Employee> employees;
	    private Employee employee;
	    private String text;
	 
	    public XMLPullParserHandler() {
	        employees = new ArrayList<Employee>();
	    }
	 
	    public List<Employee> getEmployees() {
	        return employees;
	    }
	 
	    public List<Employee> parse(InputStream is) {
	        XmlPullParserFactory factory = null;
	        XmlPullParser parser = null;
	        try {
	            factory = XmlPullParserFactory.newInstance();
	            factory.setNamespaceAware(true);
	            parser = factory.newPullParser();
	 
	            parser.setInput(is, null);
	 
	            int eventType = parser.getEventType();
	            while (eventType != XmlPullParser.END_DOCUMENT) {
	                String tagname = parser.getName();
	                switch (eventType) {
	                case XmlPullParser.START_TAG:
	                    if (tagname.equalsIgnoreCase("employee")) {
	                        // create a new instance of employee
	                        employee = new Employee();
	                    }
	                    break;
	 
	                case XmlPullParser.TEXT:
	                    text = parser.getText();
	                    break;
	 
	                case XmlPullParser.END_TAG:
	                    if (tagname.equalsIgnoreCase("employee")) {
	                        // add employee object to list
	                        employees.add(employee);
	                    } else if (tagname.equalsIgnoreCase("name")) {
	                        employee.setName(text);
	                    } else if (tagname.equalsIgnoreCase("id")) {
	                        employee.setId(Integer.parseInt(text));
	                    } else if (tagname.equalsIgnoreCase("department")) {
	                        employee.setDepartment(text);
	                    } else if (tagname.equalsIgnoreCase("email")) {
	                        employee.setEmail(text);
	                    } else if (tagname.equalsIgnoreCase("type")) {
	                        employee.setType(text);
	                    }
	                    break;
	 
	                default:
	                    break;
	                }
	                eventType = parser.next();
	            }
	 
	        } catch (XmlPullParserException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        return employees;
	    }
}
