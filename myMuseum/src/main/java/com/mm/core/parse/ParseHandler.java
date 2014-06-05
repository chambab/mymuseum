package com.mm.core.parse;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;

import com.mm.core.resource.User;

public class ParseHandler {

	/**
	 * Object to XML 로 변환
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String parseObject(Object obj) throws Exception {
		
		String rtnXml = null;
		StringWriter outputWriter = new StringWriter();
		outputWriter.write("<?xml version='1.0\' encoding='UTF-8' ?>\r\n");
		BeanWriter beanWriter = null;
		
		beanWriter = new BeanWriter(outputWriter);

		
		beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
		beanWriter.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(true);
		beanWriter.getBindingConfiguration().setMapIDs(false);
		beanWriter.enablePrettyPrint();
		

		beanWriter.write("obj", obj);
		rtnXml = outputWriter.toString();
		//System.out.println(outputWriter.toString());		
		outputWriter.close();

		return rtnXml;
	}
	/**
	 * XML
	 * @param strXML
	 * @return
	 */
	public static Object readXml(String strXML, Class beanClass) throws Exception {
	
		Object obj = null;
		StringReader xmlReader = new StringReader(strXML);
		
		BeanReader beanReader = new BeanReader();
		
		beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
        beanReader.getBindingConfiguration().setMapIDs(false);
        
        beanReader.registerBeanClass("museum", beanClass);
        beanReader.registerBeanClass("museum/users", User.class);        
        
        obj = (Object)beanReader.parse(xmlReader);
        return obj;
        
	}
}
