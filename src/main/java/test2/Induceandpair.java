package com.test2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 */
public class Induceandpair {
    private static Map<String,String> mapRight = new HashMap<>();
    private static Map<String,String> inducemap = new HashMap<>();
    private static Map<String,String> map = new HashMap<>();


    private static  String basic = "D:\\360\\r\\idrevesion";
    public static void main(String[] args) throws IOException {
        String fileName = "ws-wss4j";
        //int line1 = 1837;

        try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName+"idReversion.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : records) {
                if (record.getRecordNumber() == 32546) {
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
                    if (((iyear1-iyear2)==0 && (imonth1-imonth2)<=3)|| ((iyear1-iyear2)==1 && (12-imonth2+imonth1)<=3)){
                        String rightLable = record.get(4);
                        String rightRefact = record.get(3);
                        mapRight.put(rightLable,rightRefact);
                    }



                }

            }


            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }

        //int line2 = 6;
        try (Reader reader1 = Files.newBufferedReader(Paths.get("D:\\360\\after\\"+fileName+"afterfileidPair.csv"))) {
            Iterable<CSVRecord> records1 = CSVFormat.DEFAULT.parse(reader1);
            for (CSVRecord record : records1) {
                /** if (record.getRecordNumber() == line2) {
                 break;
                 } else {*/


                if (record.getRecordNumber() == 1) {
                    continue;
                } else {
                    String lable = record.get(3);
                    String refact = record.get(0);
                    inducemap.put(lable,refact);


                }

            }
        }



        // }
        catch (IOException ex) {
            ex.printStackTrace();


        }
        System.out.println(inducemap);
        Set<Map.Entry<String,String>> enright = mapRight.entrySet();
        Set<Map.Entry<String,String>> eninduce = inducemap.entrySet();
        for (Map.Entry<String,String> en : enright){
            String rightlable = en.getKey();
            for (Map.Entry<String,String> en1 : eninduce){
                String lable = en1.getKey();
                String refact = en1.getValue();
                if (rightlable.equals(lable)){
                    map.put(lable,refact);
                }
            }
        }



        File file2 = new File("D:\\360\\revision\\afterBugFixing\\"+fileName+".csv" );
        FileWriter fw = null;
        fw = new FileWriter(file2,true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("id","÷ÿππ");
        CSVPrinter printer = csvFormat.print(fw);
        Set<Map.Entry<String,String>> entrySet = map.entrySet();

        for(Map.Entry<String,String> en : entrySet){


            printer.printRecord(en.getKey(),en.getValue());
            //System.out.println(keys+":"+iterator.next());

        }

        printer.flush();
        printer.close();
        System.out.println(map);
    }
}
