package com.fixInducing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.ChangeLeftLineJira;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class GetJiraDeleteLine {

   private static List<ChangeLeftLineJira> inducingLineList = new ArrayList<>();
    // private static List<String> inducingList = new ArrayList<>();  //buginducingCommitList
    public static void main(String[] args) throws IOException {

        String basic = "D:\\360\\SZZUnleashed\\SZZUnleashed\\szz\\build\\libs\\resultses4x";
        File file = new File(basic);
        String[] resultList = file.list();
        /**
        for (String resultn : resultList){
            File file1 = new File(basic+"\\"+resultn+"\\fix_and_introducers_pairs.json");
            if (file1.exists()){
                String fixInducePair = getStr(file1);
                //JSONObject jsonObject = (JSONObject) JSON.parseObject(fixInducePair);
                JSONArray array = JSON.parseArray(fixInducePair);
                for (Object object : array){
                    //第二层数组
                    JSONArray array1 = (JSONArray) object;
                    String inducingCommit = array.getString(1);
                    inducingList.add(inducingCommit);
                }
            }else {

            }
        }
**/
        for (String resultn : resultList){
            File file2 = new File(basic+"\\"+resultn+"\\annotations.json");
            if (file2.exists()){
                String annotation = getStr(file2);
                JSONObject jsonObjecta = (JSONObject) JSON.parseObject(annotation);
                Set<String> keys = jsonObjecta.keySet();
                for (String key : keys){
                    //第二层数组
                    JSONArray array = jsonObjecta.getJSONArray(key);
                    for (Object object : array){
                        JSONObject jsonObject = (JSONObject) object;
                        chuli(jsonObject);
                    }

                }

            }else{

            }
        }


        //输出到文件中
        File file2 = new File("D:\\360\\buginducing\\lineOfRefact\\jiraes4xLeftSideLine.csv" );
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(file2,true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bw = new BufferedWriter(fw);

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("commitId","行号");
        CSVPrinter printer = null;
        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (ChangeLeftLineJira leftLineJira:inducingLineList){
            try {
                printer.printRecord(leftLineJira.getCommitId(),leftLineJira.getLineNumber());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            printer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            printer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    //递归处理json数组
    public static void chuli(JSONObject object){
        Set<String> keys = object.keySet();
        for (String key : keys){
            if (key.equals("mappings")){
               JSONObject mappingObject = object.getJSONObject(key);
               Set<String> mappingKeys = mappingObject.keySet();
               for (String mappingKey :mappingKeys){
                   String commit = mappingKey;
                   JSONObject objectLine = mappingObject.getJSONObject(commit);
                   Set<String> lines = objectLine.keySet();
                   for (String line : lines){
                       ChangeLeftLineJira changeLeftLineJira = new ChangeLeftLineJira();
                       changeLeftLineJira.setLineNumber(Integer.valueOf(line));
                       changeLeftLineJira.setCommitId(commit);
                       inducingLineList.add(changeLeftLineJira);
                   }
               }

            } else if (key.equals("subgraphs")) {
                JSONObject subgraphsObject = object.getJSONObject(key);
                Set<String> subKeys = subgraphsObject.keySet();
                for (String subKey : subKeys){
                    JSONObject subObject = subgraphsObject.getJSONObject(subKey);
                    chuli(subObject);
                }

                
            }
        }


    }



    //jsonFile to string
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
