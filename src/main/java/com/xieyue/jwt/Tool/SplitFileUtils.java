package com.xieyue.jwt.Tool;

import java.io.*;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-08-14 14:45
 **/

public class SplitFileUtils {

    public static void main(String[] args) {


        // 目标文件
        String targetFile = "D:\\Code\\data\\hdwxw.sql"; //"D:\\hdwxw.sql";//

        BigFileReader.Builder builder = new BigFileReader.Builder(targetFile, new IHandle() {

            String tableName = "" + Thread.currentThread().getId();

            @Override
            public void handle(String line) throws IOException {
                if(line.indexOf("Table structure for") != -1){
                    tableName = line.replace("Table structure for", "")
                                    .replaceAll("\r|\n", "")
                                    .replace("-","")
                                    .replace(" ","") + "_" + Thread.currentThread().getId();
                    System.out.println(tableName);
                }
            }
        });
        builder.withTreahdSize(20)
                .withCharset("utf8")
                .withBufferSize(1024*1024);
        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();
    }
}
