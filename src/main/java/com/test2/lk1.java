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
 * 功能:
 */
public class lk1 {
    private static Map<String,String> maprightyear = new HashMap<>();
    private static Map<String,Integer> mapyear = new HashMap<>();
    private static Map<String,String> mapRight = new HashMap<>();
    private static Map<String,Integer> map = new HashMap<>();
    private static Map<String,String> mapRight1 = new HashMap<>();
    private static Map<String,Integer> map1 = new HashMap<>();
    public static void main(String[] args) throws IOException {
        int i = 0;
        //File file = new File("D:\\360\\r\\idrevesioncopy");
        String basic = "D:\\360\\r\\idrevesioncopy";
        String fileName = "ws-wss4jidReversion1.csv";
       // String[] resultList = file.list();

           // if (fileName.contains("1")) {
                try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                    Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                    for (CSVRecord record : records) {
                        if (record.getRecordNumber() == 9464) {
                         break;
                         } else {


                        if (record.getRecordNumber() == 1) {
                            continue;
                        } else {/**
                            String rightLable1 = record.get(3);
                            String rightRefact1 = record.get(2);
                            mapRight1.put(rightLable1,rightRefact1);*/

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

                            if (((iyear1-iyear2)==0 && (imonth1-imonth2)<=6)|| ((iyear1-iyear2)==1 && (12-imonth1+imonth2)<=6)) {


                                String rightLable = record.get(3);
                                String rightRefact = record.get(2);
                                mapRight.put(rightLable, rightRefact);
                            }
/**
                        if (((iyear1-iyear2)==0)||((iyear1-iyear2)==1 && (imonth1<=imonth2))){
                                String rightLable = record.get(3);
                                String rightRefact = record.get(2);
                                maprightyear.put(rightLable, rightRefact);

                            }*/
                        }

                        }


                    }
                } catch (IOException ex) {
                    ex.printStackTrace();

                }
/**
        Set<Map.Entry<String,String>> set2 = mapRight1.entrySet();
        for (Map.Entry<String,String> e2 : set2){
            String ref = e2.getValue();
            if (map1.containsKey(ref)){
                map1.put(ref,map1.get(ref)+1);
            }else {
                map1.put(ref,1);
            }
        }
        Set<Map.Entry<String,Integer>> set4 = map1.entrySet();
        for (Map.Entry<String,Integer> entry : set4){
            i = i + entry.getValue();
        }*/

        /**Set<Map.Entry<String,String>> set3 = mapRight.entrySet();
        for (Map.Entry<String,String> e3 : set3){
            String ref = e3.getValue();
            if (map.containsKey(ref)){
                map.put(ref,map.get(ref)+1);
            }else {
                map.put(ref,1);
            }
        }*/




/**
        Set<Map.Entry<String,String>> set1 = mapRight.entrySet();
        for (Map.Entry<String,String> e1 : set1){
            String ref = e1.getValue();
            if (map.containsKey(ref)){
                map.put(ref,map.get(ref)+1);
            }else {
                map.put(ref,1);
            }
        }*/


        File file2 = new File(basic+"\\"+"1idhalfyear"+fileName );
        FileWriter fw = null;
        fw = new FileWriter(file2,true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("id","重构");
        CSVPrinter printer = csvFormat.print(fw);
        Set<Map.Entry<String,String>> entrySet = mapRight.entrySet();

        for(Map.Entry<String,String> en : entrySet){


            printer.printRecord(en.getKey(),en.getValue());
            //System.out.println(keys+":"+iterator.next());

        }

        printer.flush();
        printer.close();

/**
        File file = new File(basic+"\\"+"1year"+fileName );
        FileWriter fw1 = null;
        fw1 = new FileWriter(file,true);
        BufferedWriter bw1 = new BufferedWriter(fw1);
        if (!file.exists()) {
            file.createNewFile();
        }
        CSVFormat csvFormat1 = CSVFormat.EXCEL.withHeader("重构","数量");
        CSVPrinter printer1 = csvFormat1.print(fw1);
        Set<Map.Entry<String,Integer>> entrySet1 = mapyear.entrySet();

        for(Map.Entry<String,Integer> en1 : entrySet1){


            printer.printRecord(en1.getKey(),en1.getValue());
            //System.out.println(keys+":"+iterator.next());

        }

        printer1.flush();
        printer1.close();
*/
        System.out.println(fileName+i);

    }
}

