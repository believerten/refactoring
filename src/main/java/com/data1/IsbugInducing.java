package com.data1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class IsbugInducing {
    public static void main(String[] args) throws IOException {
        String fileName = "jnosql";
        /**int line1 = 5863;
        //List<JSONObject> list = new ArrayList<>();
        //Set<String> bugInducingIdSet = new HashSet<>();
        Set<String> yearUndulation = new HashSet<>();
        /**
        try (Reader reader1 = Files.newBufferedReader(Paths.get("D:\\360\\r\\idfilepair\\"+fileName+"fileidPair.csv"))) {
            Iterable<CSVRecord> records1 = CSVFormat.DEFAULT.parse(reader1);
            for (CSVRecord record : records1) {



                if (record.getRecordNumber() == 1) {
                    continue;
                } else {
                    String lable = record.get(3);
                    bugInducingIdSet.add(lable);


                }

            }
            // }



        }
        catch (IOException ex) {
            ex.printStackTrace();


        }*/

/**
        try (Reader reader = Files.newBufferedReader(Paths.get("D:\\360\\r\\idrevesion\\" + fileName+"idReversion1.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : records) {
               if (record.getRecordNumber() == line1) {
                    break;
                } else {

                    if (record.getRecordNumber() == 1) {
                        continue;
                    } else {
                        String time1 = record.get(0);
                        String time2 = record.get(1);
                        String[] s1 = time1.split("/");
                        String[] s2 = time2.split("/");
                        String year1 = s1[0];
                        String month1 = s1[1];
                        int iyear1 = Integer.valueOf(year1);
                        //System.out.println(iyear1);
                        int imonth1 = Integer.valueOf(month1);
                        String year2 = s2[0];
                        String month2 = s2[1];
                        int iyear2 = Integer.parseInt(year2);
                        int imonth2 = Integer.parseInt(month2);

                        //if (((iyear1-iyear2)==0 && (imonth1-imonth2)<=6)|| ((iyear1-iyear2)==1 && (12-imonth2+imonth1)<=6)) {
                        //if (((iyear1-iyear2)==0  )|| ((iyear1-iyear2)==1 && (12-imonth2+imonth1)<=12)) {
                            if (((iyear1-iyear2)==0) && (imonth1-imonth2)<=3|| ((iyear1-iyear2)==1 && (12-imonth2+imonth1)<=3)){
                            String rightLable = record.get(3);

                            yearUndulation.add(rightLable);

                        }

                    }

                }


      }
            System.out.println(yearUndulation.size());
        } catch (IOException ex) {
            ex.printStackTrace();

        }*/
        String filePath = "D:\\360\\ts\\ref\\"+fileName+".json";
        File file2 = new File(filePath);

            String refactoring = getStr(file2);
            JSONArray refacroyingArray = JSON.parseArray(refactoring);

        for (Object refactoryingObject : refacroyingArray ){
            JSONObject refactoryingJsonObject = (JSONObject) refactoryingObject;

                    refactoryingJsonObject.put("isHaflfyearUnstable",0);
            //isThreemonthUnstable
            //isHaflfyearUnstable
                    //isYearUnstable


        }

               /** for (Object refactoryingObject : refacroyingArray ){
                    JSONObject refactoryingJsonObject = (JSONObject) refactoryingObject;
                    for (String lable : yearUndulation){
                        String s = lable;
                    if (s.equals(refactoryingJsonObject.getString("commitId")+refactoryingJsonObject.getInteger("lable"))){
                        System.out.println(refactoryingJsonObject.getString("commitId")+refactoryingJsonObject.getInteger("lable"));
                        System.out.println(1);
                        refactoryingJsonObject.put("isThreemonthUnstable",1);

                        System.out.println(refactoryingJsonObject);

                    }else {
                        //refactoryingJsonObject.put("isBuginducing",0);
                        continue;

                    }
                }
            }*/

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
