package com.xieyue.jwt.common;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-07-17 10:46
 **/

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import java.util.HashMap;
import java.util.Map;

public class GetCameraPreviewURL {

    public static String GetCameraPreviewURL() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "192.168.19.200:8443";         // artemis网关服务器ip端口
        ArtemisConfig.appKey = "27316362";                  // 秘钥appkey
        ArtemisConfig.appSecret = "yRZI9ftvzhEWSg8B7kej";   // 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + "/api/resource/v2/person/personList";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };

        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";

        /**
         * STEP5：组装请求参数
         */

        Map<String, String> headers = new HashMap<>();



        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("cameraIndexCode", "748d84750e3a4a5bbad3cd4af9ed5101");
//        jsonBody.put("streamType", 0);
//        jsonBody.put("protocol", "rtsp");
//        jsonBody.put("transmode", 1);
//        jsonBody.put("expand", "streamform=ps");
        jsonBody.put("pageSize", 1);
        jsonBody.put("pageNo",   1);
        String body = jsonBody.toJSONString();


        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数
        return result;
    }

    public static void main(String[] args) {
        String result = GetCameraPreviewURL();
        System.out.println("result结果示例: " + result);
    }
}

