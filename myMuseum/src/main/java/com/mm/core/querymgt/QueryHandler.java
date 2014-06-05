package com.mm.core.querymgt;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mm.core.parse.XmlHandler;

/**
 * @author
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryHandler {
	private XmlHandler queryXml = null;
	private String queryXmlPath = null;
	
	private QueryHandler(String queryXmlName)
	{
		this.queryXmlPath = "properties/" + queryXmlName;
	}	
	/**
	 * QueryID
	 * @param queryId
	 * @return
	 */
	public String getQuery(String queryId)
	{
		//queryXml = new XmlHandler("D:\\eclipse\\workspace\\Test\\src\\conv\\db\\VOL_CONV.xml");
		//queryXml = new XmlHandler("properties/VOL_CONV.xml");
		queryXml = new XmlHandler(this.queryXmlPath);
		String query = null;
		Element node = this.queryXml.find("query", "id", queryId);
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
