<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.*" %>
<%
	String fileName = request.getHeader("X-File-Name");
	String ext = fileName.substring(fileName.lastIndexOf("."));
	String uploadFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + ext;
	//String fileUrl = application.getContextPath() + "/fileupload/" + uploadFileName;	
	String fileUrl = request.getContextPath() + "/fileupload/" + uploadFileName;
	File uploadFile = new File(application.getRealPath("fileupload") + "/" + uploadFileName);
	
	InputStream in = request.getInputStream();
	OutputStream outFile = new FileOutputStream(uploadFile);
	byte[] buf = new byte[1024*2];
	int size = 0;
	while((size=in.read(buf)) != -1){
		outFile.write(buf, 0, size);
	}
	outFile.close();
	in.close();
	out.write("<a href='"+fileUrl+"'>"+fileName+"</a>");
%>