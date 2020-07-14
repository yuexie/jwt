package com.xieyue.jwt.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbmUtils {
	public static final String SUCCESSFLAG = "SUCCESSFLAG";
	
	public static Map getSingleLineMapResult(List<HashMap>source){
		if(source==null || source.isEmpty()){
			return null;
		}
		return source.get(0);
	}
	
	public static Object getSimpleMapResultValue(List<HashMap> source,String keyName){
		Object result = null;
		if(source==null || source.isEmpty()){
			return null;
		}
		Map map = source.get(0);
		if(map!=null && !map.isEmpty()){
			result = map.get(keyName);
		}
		return result;
	}
	
	public static boolean getMapResultSuccessFlag(List<HashMap> source){
		boolean result = false;
		Object obj = getSimpleMapResultValue(source,SUCCESSFLAG);
		if(obj!=null && "1".equals(obj.toString())){
			result = true;
		}
		return result;
	}
	
}
