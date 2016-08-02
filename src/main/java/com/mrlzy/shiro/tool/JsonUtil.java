package com.mrlzy.shiro.tool;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	private static  ObjectMapper objectMapper =new ObjectMapper();
	private static SimpleDateFormat defaultDateFormat=DateUtil.getSimpleDateFormat("yyyy-MM-dd");

	static {
		      objectMapper.setDateFormat(defaultDateFormat);
	}

	
	
	public static String obj2JsonWithExption(Object obj) throws JsonProcessingException {
		   String v="";
			try {
				v=objectMapper.writeValueAsString(obj);
			}finally {
			}
			return v;

		
	}
	
	public static String obj2Json(Object obj){
		String v="";
		try {
			v=obj2JsonWithExption(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return v;

	}
	
	public  static Object json2ObjWithExption(Class<?> cls,String json) throws JsonParseException, JsonMappingException, IOException{
		return  objectMapper.readValue(json, cls);
	}
	
	public static Object json2Obj(Class<?> cls,String json){
		Object obj=null;
		try {
			obj = json2ObjWithExption(cls,json);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return obj;
	}
	

	
	public  static  <T> List<T>  json2ListExption(String json) throws JsonParseException, JsonMappingException, IOException{
		return objectMapper.readValue(json, new TypeReference<List<T>>() {});   
		
	}
	
	
	public static  <T> List<T> json2List(String json){
		List<T> list=null;
		try {
			list =json2ListExption(json);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return list;
	}
	
	


}
