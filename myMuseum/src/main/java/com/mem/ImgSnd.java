package com.mem;

import java.io.*;
import java.io.Serializable;

public class ImgSnd implements Serializable
{
	public String owner = null;
	public String id = null;
	public int    num = 0;
	public String type = null;
	public String url = null;
	public String size = null;
	public String message = null;
	public String writer = null;
	public String writernm = null;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getWriternm() {
		return writernm;
	}
	public void setWriternm(String writernm) {
		this.writernm = writernm;
	}
	
}
