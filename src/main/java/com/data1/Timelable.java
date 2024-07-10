package com.data1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Timelable {
    public static void main(String[] args) throws IOException {
        String fileName = "ws-wss4j";
        int line1 = 84;

        Map<String,String> timeMap = new HashMap<>();

         try (Reader reader1 = Files.newBufferedReader(Paths.get("D:\\360\\ts\\commitmessage\\commitmessage\\"+fileName+".csv"))) {
         Iterable<CSVRecord> records1 = CSVFormat.DEFAULT.parse(reader1);
         for (CSVRecord record : records1) {
             /**if (record.getRecordNumber() == line1) {
                 break;
             } else {*/



         if (record.getRecordNumber() == 1) {
         continue;
         } else {
         String commitid = record.get(0);
         String message = record.get(1);
         timeMap.put(commitid,message);

         }

         }
          }



        // }
         catch (IOException ex) {
         ex.printStackTrace();


         }
        String filePath = "D:\\360\\ts\\ref\\"+fileName+".json";
        File file2 = new File(filePath);

        String refactoring = getStr(file2);
        JSONArray refacroyingArray = JSON.parseArray(refactoring);

        for (Object refactoryingObject : refacroyingArray ){
            JSONObject refactoryingJsonObject = (JSONObject) refactoryingObject;

            refactoryingJsonObject.put("commitMessage","0");

        }
        Set<Map.Entry<String,String>> timeset = timeMap.entrySet();
        for (Object refactoryingObject : refacroyingArray ){
            JSONObject refactoryingJsonObject = (JSONObject) refactoryingObject;

            for (Map.Entry<String,String> time : timeset){
                String commitid = time.getKey();
                String year = time.getValue();
                if (commitid.equals(refactoryingJsonObject.getString("commitId"))){

                    refactoryingJsonObject.put("commitMessage",year);

                }else {
                    //refactoryingJsonObject.put("isBuginducing",0);
                    continue;

                }
            }
        }

        createJsonFile(refacroyingArray,"D:\\360\\ts\\ref\\"+fileName+".json");



    }
    public static boolean createJsonFile(Object jsonData, String filePath) {
        String content = JSON.toJSONString(jsonData, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Writer write = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            write.write(content);
            write.flush();
            write.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }


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

}
