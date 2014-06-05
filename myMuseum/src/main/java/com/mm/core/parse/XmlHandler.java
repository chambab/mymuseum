/*
 * Created on 2005. 11. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mm.core.parse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XmlHandler 
{
    private String xmlFilePath = null;
    private Document doc  = null;		//	xmlFilePath�� Document
    private Element  root = null;
    private NodeList nodes = null;		//	xmlFilePath�� ��� Element�� �����Ѵ�.
    
    public XmlHandler() {}
    public XmlHandler(String xmlFilePath) 
    {
        this.xmlFilePath = xmlFilePath;
        this.load("*");
    }
    public XmlHandler(String xmlFilePath, String tagName)
    {
        this.xmlFilePath = xmlFilePath;
        this.load(tagName);
    }
    /**
     * xmlFilePathsearch.xml TagName(*,Folder,Project)
     * ���ؼ� NodeList ���ؼ� �����Ѵ�.
     * @param TagName
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private synchronized void load(String TagName)
    {        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);   
        DocumentBuilder builder;
        try 
        {
	        builder = factory.newDocumentBuilder();
	        this.doc = builder.parse(this.xmlFilePath);
	        this.root = doc.getDocumentElement();
	        nodes = doc.getElementsByTagName(TagName);
        }
        catch(Exception e)
        {
            System.out.println("[ERROR:Cannot load " + this.xmlFilePath + "] " + e.toString());
        }
    }
    /**
     * attribut
     * @param attrName attribute
     * @param attrValue attribute
     * @return
     */
    public Element find(String nodeName, String attrName, String attrValue)
    {
        Element selectedNode = null;
        Element node = null;
        for(int i=0; i < nodes.getLength(); i++)
        {
            node = (Element)nodes.item(i);    
            
            if(node.getNodeName().equals(nodeName) &&
               node.getAttribute(attrName).equals(attrValue))
            {
                selectedNode = node;
                break;
            }
        }        
        return selectedNode;
    }
    /**
     * element
     * @param attrName
     * @param attrValue
     * @return
     */
    public Element find(ArrayList attrName, ArrayList attrValue)
    {
        Element selectedNode = null;
        Element node = null;
        boolean bMatch = false;
        for(int i=0; i < nodes.getLength(); i++)
        {
            node = (Element)nodes.item(i);
            
            bMatch = true;
            for(int j=0; j < attrName.size(); j++)
            {
	            if(!node.getAttribute((String)attrName.get(j)).equals((String)attrValue.get(j)))
	            {
	                bMatch = false;
	                break;
	            }
            }
            if(bMatch)
            {
                selectedNode = node;
                break;
            }
        }
        return selectedNode;
    }
    /**
     * load
     * 
     * @return
     * @throws TransformerException
     * @throws IOException 
     */
    public synchronized boolean  save()
    {
        String newXml = null;
        try {
        FileOutputStream fos = new FileOutputStream(this.xmlFilePath);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
        transformer.setOutputProperty(OutputKeys.METHOD, "xml"); 
        //transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "item.dtd"); 
        //transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN");         
        transformer.setOutputProperty(OutputKeys.ENCODING, "euc-kr"); 
        transformer.transform(new DOMSource(doc), new StreamResult(fos));

        fos.close();
        } catch(TransformerException e) {
            System.out.println("[Error]: " + e.toString());
            return false;
        } catch(IOException e1) {
            System.out.println("[Error]: " + e1.toString());
            return false;
        }        
        return true;
    }    
    /**
     * @return Returns the doc.
     */
    public Document getDoc() {
        return doc;
    }
    /**
     * @return Returns the root.
     */
    public Element getRoot() {
        return root;
    }
    /**
     * @return Returns the nodes.
     */
    public NodeList getNodes() {
        return nodes;
    }
}
