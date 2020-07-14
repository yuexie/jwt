package com.xieyue.jwt.common;

import java.util.ArrayList;
import java.util.List;

import com.wx.vmind.common.Global;
import com.wx.vmind.common.SystemConfig;
import com.wx.vmind.common.SystemConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import com.wx.util.ConvertUtils;
import com.wx.util.Validators;
import com.wx.util.XmlUtils;
import com.wx.vmind.cases.domain.ClueContentEx;
import com.wx.vmind.cases.domain.ClueTypeEx;
import com.wx.vmind.data.domain.QueryConditionEx;
import com.wx.vmind.data.domain.QueryConfigEx;
import com.wx.vmind.domain.business.SystemParam;
import com.wx.vmind.system.domain.SystemParameter;

public final class DataQueryConfig {
	private static String configPath = null;
	
	public static void init(String location){
		try{
			configPath = location;
			readConfig(location);
		}catch(Exception e){
			e.printStackTrace();
//			System.out.println(e.toString());
		}
	}
	public static void reInit(){
		try{
			if(configPath == null){
				configPath = Global.CONFIG_DIR +"/"+ SystemConfig.getValue("dataQueryConfigLocation","dataQuery-config.xml");
			}
			readConfig(configPath);
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	private static void readConfig(String location)throws Exception{
		Document document = XmlUtils.readDocument(location);
        Element rootElement = document.getRootElement();
        Namespace namespace = rootElement.getNamespace();
        List protocolTypeElementList = rootElement.getChildren("protocolType", namespace);
        dataQueryList = new ArrayList<QueryConfigEx>();
        for(int i=0;i<protocolTypeElementList.size();i++)
        {     	
        	QueryConfigEx queryEx = new QueryConfigEx();
        	Element protocolTypeElement = (Element) protocolTypeElementList.get(i);        	
        	queryEx.setProtocolTypeIds(protocolTypeElement.getAttributeValue("id"));
        	
        	queryEx.setProtocolTypeNames(protocolTypeElement.getAttributeValue("name"));        	
        	Namespace namespace1 = protocolTypeElement.getNamespace();
        	List conditionElementList = protocolTypeElement.getChildren("condition",namespace1);
        	List<QueryConditionEx> conditionList = new ArrayList<QueryConditionEx>();
        	for (int j=0;j<conditionElementList.size();j++)
        	{
        		QueryConditionEx condition = new QueryConditionEx();
        		Element conditionElement = (Element) conditionElementList.get(j);   
        		condition.setName(conditionElement.getAttributeValue("name"));
        		condition.setDesc(conditionElement.getAttributeValue("desc"));
        		condition.setType(conditionElement.getAttributeValue("type"));
        		if (!Validators.isNull(conditionElement.getAttributeValue("varname")))
        			condition.setVarName(conditionElement.getAttributeValue("varname"));
        		if (!Validators.isNull(conditionElement.getAttributeValue("checkDesc")))
        			condition.setCheckDesc(getCheckDesc(conditionElement.getAttributeValue("checkDesc")));        		

        		conditionList.add(condition);
        	}
        	queryEx.setConditionList(conditionList);
        	dataQueryList.add(queryEx);
        }        
	}
	
	public static List<QueryConfigEx> getDataQueryConfig(){
//		reInit();
		return dataQueryList;
	}
	public static List<QueryConditionEx> getQueryCondition(int typeId){
		List<QueryConditionEx> newConditionList = new ArrayList<QueryConditionEx>();
		for(QueryConfigEx query : dataQueryList){
			if(query.getProtocolTypeIds().indexOf(",") != -1){
				String[] ids = query.getProtocolTypeIds().split(",");
				for(String id : ids){
					if(id.equals(""+typeId)){
						return query.getConditionList();
					}
				}
			}else{
				if(query.getProtocolTypeIds().equals(""+typeId)){
					return query.getConditionList();
				}
			}
		}
		return null;
	}
	public static String[] getVarNameArr(int typeId){
		String varNameStr = "";
		for(QueryConditionEx condition : getQueryCondition(typeId)){
			
		}
		return varNameStr.split(",");
	}
	public static List<QueryConditionEx> getVarNameList(){
		List<QueryConditionEx> newConditionList = new ArrayList<QueryConditionEx>();
		QueryConditionEx newCondition = null;
		String varNameStr = "";
		for(QueryConfigEx query : dataQueryList) {			
			for(QueryConditionEx condition : query.getConditionList()) {
				if(varNameStr.indexOf("'"+condition.getVarName()+ "'")==-1) {
					varNameStr += "'"+condition.getVarName() + "',";
					newCondition = new QueryConditionEx();
					newCondition.setVarName(condition.getVarName());
					newCondition.setType(condition.getType());
					newCondition.setName(condition.getName());					
					newCondition.setDesc(condition.getDesc());
					newCondition.setCheckDesc(getCheckDesc(condition.getCheckDesc()));
					
					newConditionList.add(newCondition);
				}
			}
		}
		return newConditionList;
	}
	private static String getCheckDesc(String checkDes){
		if(checkDes != null && isDbLoad(checkDes)){
			List<SystemParam> paramList = SystemConstants.getSystemParam(0, checkDes);
			StringBuffer sb = new StringBuffer();
			for(SystemParam parm : paramList){
				sb.append(",").append(parm.getParamName()).append("=").append(parm.getParamValue());
			}
			if(sb.toString().startsWith(",")){
				return (sb.toString().substring(1));
			}else{
				return (sb.toString());
			}
		}else{
			return checkDes;
		}
	}
	private static boolean isDbLoad(String destype){
		if(destype.equals("DATA_SNS_TYPE")){
			return true;
		}else if(destype.equals("DATA_SNS_OPTYPE")){
			return true;
		}else if(SystemConstants.getSystemParam(0, destype) != null && SystemConstants.getSystemParam(0, destype).size()>0){
			return true;
		}
		return false;
	}
	private static List<QueryConfigEx> dataQueryList = null;	
}
