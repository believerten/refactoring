package com.data1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.data.ChangeLeftLineJira;
import com.data.RightCodeElementToJson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 */
public class afterRefactInformation {
    public static void main(String[] args) throws IOException {



        List<ChangeLeftLineJira.LeftRefact> list = new ArrayList<>();

        List<RightCodeElementToJson.Pair> list1 = new ArrayList<>();
        String fileName = "storm";
        try (Reader reader = Files.newBufferedReader(Paths.get("D:\\360\\revision\\jj\\j\\" + fileName+"idReversion.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : records) {
                 if (record.getRecordNumber() == 315028) {
                    break;
                } else {

                    if (record.getRecordNumber() == 1) {
                        continue;
                    } else {
                        String time1 = record.get(1);
                        String time2 = record.get(2);
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

                        String afterRefact = record.get(3);
                        String element = record.get(0);
                        String afterCommitId = record.get(7);

                        String beforeCommitId = record.get(8);
                        String beforeRefact = record.get(5);
                        if (((iyear1-iyear2)==0 && (imonth1-imonth2)<=3)|| ((iyear1-iyear2)==1 && (12-imonth2+imonth1)<=3)) {

                            ChangeLeftLineJira.LeftRefact leftRefact = new ChangeLeftLineJira.LeftRefact();
                            leftRefact.setLeftRefact(afterRefact);
                            leftRefact.setLeftCommitId(afterCommitId);
                            leftRefact.setElement(element);
                            leftRefact.setBeforeRefact(beforeRefact);
                            leftRefact.setBeforeCommitId(beforeCommitId);
                            System.out.println(leftRefact);
                            /**for (LeftRefact l : list){
                                String bcommitid = l.getBeforeCommitId();
                                String acommitid = l.getLeftCommitId();
                                if (!afterCommitId.equals(acommitid) && !beforeCommitId.equals(bcommitid)){*/
                                    list.add(leftRefact);
                               // }
                           // }


                        }
                    }

                }
            System.out.println(list);


            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        String filePath = "D:\\360\\ts\\ref\\"+fileName+".json";
        File file2 = new File(filePath);

        String refactoring = getStr(file2);
        JSONArray refacroyingArray = JSON.parseArray(refactoring);


            for (ChangeLeftLineJira.LeftRefact left : list){
                RightCodeElementToJson.Pair pair = new RightCodeElementToJson.Pair();
                String element = left.getElement();
                String afterRefact = left.getLeftRefact();
                String beforeRefact = left.getBeforeRefact();
                String afterCommitId = left.getLeftCommitId();
                String beforeCommitId = left.getBeforeCommitId();

                pair.setElement(element);
                pair.setAfterRefact(afterRefact);
                pair.setBeforeRefact(beforeRefact);
                pair.setAfterCommitId(afterCommitId);
                pair.setBeforeCommitId(beforeCommitId);

                for (Object refactoryingObject : refacroyingArray ){
                    JSONObject refactoryingJsonObject = (JSONObject) refactoryingObject;
                    String commitId = refactoryingJsonObject.getString("commitId");
                    String message = refactoryingJsonObject.getString("commitMessage");
                if (pair.getAfterCommitId().equals(commitId)){
                    pair.setAfterCommitId(commitId);
                    pair.setAfterMessage(message);
                } else if (pair.getBeforeCommitId().equals(commitId)) {
                    pair.setBeforeCommitId(commitId);
                    pair.setBeforeMessage(message);

                }
                }

                list1.add(pair);






        }
        File file = new File("D:\\360\\revision\\jj\\"+fileName+".csv" );
        FileWriter fw = null;
        fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file.exists()) {
            file.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("commitId1","commitMessage1","element","type1","commitId2","commitMessage2","type2");
        CSVPrinter printer = csvFormat.print(fw);


        for(RightCodeElementToJson.Pair pair : list1){


            printer.printRecord(pair.getBeforeCommitId(),pair.getBeforeMessage(),pair.getElement(),pair.getBeforeRefact(),pair.getAfterCommitId(),pair.getAfterMessage(),pair.getAfterRefact());
            //System.out.println(keys+":"+iterator.next());

        }

        printer.flush();
        printer.close();



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
