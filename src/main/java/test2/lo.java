package com.test2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 功能:
 */

public class lo {
    private static  String basic = "D:\\360\\r\\idrevesion";
    private static Map<String,String> map = new HashMap<>();
    private  static Map<String,String> mapRight = new HashMap<>();
    public static void main(String[] args) throws IOException {
        String fileName = "ws-wss4jidReversion1.csv";
        int line1 = 9464;
        //int line2 = 156247;
        try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
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
                        if (((iyear1-iyear2)==0 && (imonth1-imonth2)<=6)|| ((iyear1-iyear2)==1 && (12-imonth1+imonth2)<=6)){

                            String rightLable = record.get(3);
                            String rightRefact = record.get(2);
                            mapRight.put(rightLable,rightRefact);

                        }

                    }

                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        System.out.println(mapRight.size());
        try (Reader reader1 = Files.newBufferedReader(Paths.get(basic + "\\" + fileName.replace("1","")))) {
            Iterable<CSVRecord> records1 = CSVFormat.DEFAULT.parse(reader1);
            for (CSVRecord record : records1) {
                /** if (record.getRecordNumber() == line2) {
                 break;
                 } else {*/

                if (record.getRecordNumber() == 1) {
                    continue;
                } else {
                    Set<Map.Entry<String,String>> entrySet = mapRight.entrySet();

                    for (Map.Entry<String,String> e : entrySet){
                        if (e.getKey().equals(record.get(6))){
                            String leftRefact = record.get(3);
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
                            if (leftRefact.equals(e.getValue())&& (((iyear1-iyear2)==0 && (imonth1-imonth2)<=6)|| ((iyear1-iyear2)==1 && (12-imonth1+imonth2)<=6))){
                                //System.out.println(leftRefact);


                                    map.put(e.getKey(),leftRefact);


                            }else {
                                continue;
                            }
                        }else {
                            continue;
                        }
                    }


                }

            }

        }
        // }
        catch (IOException ex) {
            ex.printStackTrace();


        }
        Set<Map.Entry<String,String>> entrySet = map.entrySet();
        File file2 = new File("D:\\360\\r\\new\\same2.csv" );
        FileWriter fw = null;
        fw = new FileWriter(file2,true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("lable","重构","数量");
        CSVPrinter printer = csvFormat.print(fw);

        for(Map.Entry<String,String> en : entrySet){


                printer.printRecord(en.getKey(),en.getValue(),"1");
                //System.out.println(keys+":"+iterator.next());

        }

        printer.flush();
        printer.close();

/**
        Set<Map.Entry<String,Integer>> entrySet = map.entrySet();
        for (Map.Entry<String,Integer> en : entrySet){
            System.out.println(en.getKey());
        }
        for (Map.Entry<String,Integer> en : entrySet){
            System.out.println(en.getValue());
        }*/

    }
}
