package com.mm.core.querymgt;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mm.core.parse.XmlHandler;

public class QueryManager {

	public  static String webAppDir = null;	
	private static HashMap properties = null;
	private static QueryManager theInstance = null;
	private XmlHandler xmlHandler = null;
	/**
	 * Singleton API 
	 * @return
	 */
	public static synchronized QueryManager getInstance() {
		if(theInstance == null) {
			theInstance = new QueryManager();
		}
		return theInstance;
	}
	
//	public void load(ActionServlet servlet)
//	{
//		QueryManager instance = QueryManager.getInstance();
//		QueryManager.webAppDir = servlet.getServletContext().getRealPath("/");
//
//		String configDir = QueryManager.webAppDir;
//		configDir =  configDir + ( (configDir.charAt(configDir.length()-1)== File.separatorChar)?"":"/")+"WEB-INF/properties/properties.xml";
//		configDir = configDir.replace('/', File.separatorChar);
//		//
//		//	properties
//		instance.propertiesLoad(configDir);				
//	}
	
	public String getQuery(String queryId)
	{		
		this.xmlHandler = new XmlHandler("WEB-INF/properties/sql/VOL_BBS_CONV.xml");
		String query = null;
		Element node = this.xmlHandler.find("query", "id", queryId);
		if(node == null) return null;
		
		NodeList nodes = node.getChildNodes();		
		Node node1 = null;
		
		for(int i=0; i < nodes.getLength(); i++)
        {
    	   node1 = nodes.item(i);               
           if(node1.getNodeName().equals("statement")) 
           {
        	   //System.out.println("[" + node1.getChildNodes().item(0).getNodeValue() + "]");
        	   query = node1.getChildNodes().item(0).getNodeValue();
           }
           
        } 
		return query;		
	}
}
