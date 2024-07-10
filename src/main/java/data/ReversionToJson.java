package com.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 *
 */
public class ReversionToJson {
    public static String getStr(File jsonFile) throws IOException {
        String jsonStr = "";
        FileReader fileReader = new FileReader(jsonFile);
        Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"UTF-8");
        int ch = 0 ;
        StringBuffer sb = new StringBuffer();
        while((ch=reader.read())!=-1){
            sb.append((char)ch);
        }
        fileReader.close();
        reader.close();
        jsonStr = sb.toString();
        return jsonStr;

    }
    public static void main(String[] args) throws IOException {
        File fileLeftCodeElement = new File("D:\\360\\ts\\leftCodeElement.json");
        JSONArray jsonArrayLeft = JSON.parseArray(getStr(fileLeftCodeElement));
        File fileRightCodeElement = new File("D:\\360\\ts\\rightCodeElement.json");
        JSONArray jsonArrayRight = JSON.parseArray(getStr(fileRightCodeElement));
        for (int i = 0 ; i < jsonArrayRight.size() ; i++) {
            JSONObject rightObject = jsonArrayRight.getJSONObject(i);
            for (int j = 0 ; j < jsonArrayLeft.size() ; j++){
                JSONObject leftObject = jsonArrayLeft.getJSONObject(j);
                if (rightObject.getString("codeElement").equals(leftObject.getString("codeElement"))){
                    System.out.println(rightObject.getString("codeElement"));
                }
            }
        }

    }
}
