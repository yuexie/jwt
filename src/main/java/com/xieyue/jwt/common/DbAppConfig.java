package com.xieyue.jwt.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import com.wx.util.ConvertUtils;
import com.wx.util.XmlUtils;
import com.wx.vmind.common.StringComparator;

public class DbAppConfig {

	 /**
     * 命名空间
     */
    private Namespace namespace = Namespace.getNamespace("");
    private static DbAppConfig dbConf = null;
    private static List<AppEntity> appEntList = new ArrayList<AppEntity>();
    private static int[] mainDbId;

    /**
     * 重载构造函数
     * @param filename
     * @throws Exception
     */
    private DbAppConfig(String filename) throws Exception {
        readConfig(filename);
    }
    /**
     * 根据数据库配置文件，封装实体类
     * @param filename
     * @throws Exception
     */
    public void readConfig(String filename) throws Exception {
        Document document = XmlUtils.readDocument(filename);
        Element rootElement = document.getRootElement();
        namespace = rootElement.getNamespace();
        List appList = rootElement.getChildren(APP, namespace);
        int count = appList.size();
        for (int i = 0; i < count; i++) {
            Element app = (Element) appList.get(i);
            AppEntity appEntity = new AppEntity();
            appEntity.setAppName(ConvertUtils.toString(app.getAttributeValue(APP_NAME,namespace)));
            appEntity.setDes(ConvertUtils.toString(app.getAttributeValue(DESCRIPTION,namespace)));
            String[] sIds = (ConvertUtils.toString(app.getAttributeValue(SYSTEMIDS,namespace))).split(",");
            String[] dbIds = (ConvertUtils.toString(app.getAttributeValue(DBIDS,namespace))).split(",");
            String[] protocolNames = (ConvertUtils.toString(app.getAttributeValue(PROTOCOLNAMES,namespace))).split(",");
            appEntity.setSystemIds(sIds);
            appEntity.setDbIds(dbIds);
            appEntity.setProtocolTypeNames(protocolNames);
            appEntList.add(appEntity);
        }
        for (AppEntity appEntity:appEntList){
            if (appEntity.getAppName().equals(MAIN_NAME)){
                mainDbId = transformStringsToInts(appEntity.getDbIds());
            }
        }
    }
    public static void init(String name) throws Exception {
        if (dbConf == null) {
            dbConf = new DbAppConfig(name);
        }
    }   
    /**
     * 根据systemId返回所查询的管理主库对应的dbIds
     * @param systemId
     * @return
     */
    
    public static int[] getMainDbId(int systemId){
        for (AppEntity appEntity:appEntList){
            if (appEntity.getAppName().equals(MAIN_NAME)){
                String[] sIds = appEntity.getSystemIds();
                for (int i=0;i<sIds.length;i++){
                    if (ConvertUtils.toInt(sIds[i]) == systemId){
                        return transformStringsToInts(appEntity.getDbIds());
                    }
                }
            }
        }
        return null;
    }

    public static int[] getMainDbId() {
        return mainDbId;
    }
    

	/**
	 * 返回定义的所有的账号密码库的dbid ,以','分割
	 * @return 
	 */
	public static String getAllAccountPasswordDbids(){
    	List<String> dbIdList=new ArrayList<String>(); 
    	for (AppEntity appEntity:appEntList){  //将所有帐号密码库dbid放入集合

    		String[] dbIds=appEntity.dbIds;
    		if(appEntity.getAppName().equals(BASEDBGROUPDBID)){
    			for(int i=0;i<dbIds.length;i++){
        			dbIdList.add(dbIds[i]);
        		}
    		}
    	}
    	StringComparator stringComparator = new StringComparator();
    	Collections.sort(dbIdList, stringComparator);  //对dbid的集合内容进行排序

    	List<String> dbIds=new ArrayList<String>();  //声明一个集合存放去重后的所有dbid

    	for(int i=0;i<dbIdList.size();i++){
    		if(i==0){
    			dbIds.add(dbIdList.get(0));
    		}else{
    			String dbId=dbIds.get(dbIds.size()-1);
    			if(!dbId.equals(dbIdList.get(i)))
    				dbIds.add(dbIdList.get(i));
    		}
    	}
    	String returnDbIds="";
    	for(int i=0;i<dbIds.size();i++){
    		if(i==0)
    			returnDbIds=dbIds.get(i);
    		else
    		returnDbIds+=","+dbIds.get(i);
    	}
    	return returnDbIds;
    }
	 class AppEntity{

	        public void setAppName(String appName){
	            this.appName=appName;
	        }

	        public String getAppName(){
	            return this.appName;
	        }

	        public void setSystemIds(String[] systemIds){
	            this.systemIds=systemIds;
	        }

	        public String[] getSystemIds(){
	            return this.systemIds;
	        }

	        public void setDbIds(String[] dbIds){
	            this.dbIds=dbIds;
	        }

	        public String[] getDbIds(){
	            return this.dbIds;
	        }

	        public void setProtocolTypeNames(String[] protocolTypeNames){
	            this.protocolTypeNames=protocolTypeNames;
	        }

	        public String[] getProtocolTypeNames(){
	            return this.protocolTypeNames;
	        }

	        public void setDes(String des){
	            this.des=des;
	        }

	        public String getDes(){
	            return this.des;
	        }

	        private String appName;
	        private String[] systemIds;
	        private String[] dbIds;
	        private String[] protocolTypeNames;
	        private String des;
	    }

	    
	    private static String NO_NAME      = "";
	    private static String MAIN_NAME      = "main";
	    private static String ACCOUNTPASSWORD      = "accountPassord";
	    private static String DATA_NAME      = "data";
	    private static String APP       = "app";
	    private static String APP_NAME        = "appName";
	    private static String SYSTEMIDS             = "systemIds";
	    private static String DBIDS    = "dbIds";
	    private static String PROTOCOLNAMES          = "protocolTypeNames";
	    private static String DESCRIPTION      = "des"; 
	    private static String BASEDBGROUPDBID      = "baseDbGroupDbid"; 
	    
	    /**
	     * 将输入的字符串数组转为整型数组
	     * @param inputs
	     * @return
	     */
	    private static int[] transformStringsToInts(String[] inputs){
	        int[] outputs = new int[inputs.length];
	        for (int i=0;i<outputs.length;i++){
	            outputs[i] = ConvertUtils.toInt(inputs[i]);
	        }
	        return outputs;
	    }
	    public static void main(String[] args) throws Exception{
	        String name = "I:\\web\\WEB-INF\\config\\dbtype-config.xml";
	        init(name);
	       // System.out.println(getAllAccountPasswordDbids());

	        //System.out.println(appEntList.get(4).getAppName());

	    }
}
