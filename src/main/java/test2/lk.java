package com.test2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * 功能:
 */
public class lk {
    private static Map<String,String> mapRight = new HashMap<>();
    private static Map<String,Integer> map = new HashMap<>();
    private static List<String> list = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\360\\r\\idrevesioncopy");
        String basic = "D:\\360\\r\\idrevesioncopy";
        String[] resultList = file.list();
        for (String fileName : resultList) {
            if (fileName.contains("1year")) {
                try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                    Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                    for (CSVRecord record : records) {
                        /** if (record.getRecordNumber() == line1) {
                         break;
                         } else {*/

                        if (record.getRecordNumber() == 1) {
                            continue;
                        } else {/**
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
*/

                               Integer number = Integer.valueOf(record.get(1));
                                String rightRefact = record.get(0);
                                if (map.containsKey(rightRefact))
                                {map.put(rightRefact, map.get(rightRefact)+number);
                                }else {
                                    map.put(rightRefact,number);
                                }
                            }
                       // }

                        //}


                    }
                } catch (IOException ex) {
                    ex.printStackTrace();

                }

            }
        }

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


        File file2 = new File(basic+"\\"+"sumhalfyear.csv" );
        FileWriter fw = null;
        fw = new FileWriter(file2,true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("重构","数量");
        CSVPrinter printer = csvFormat.print(fw);
        Set<Map.Entry<String,Integer>> entrySet = map.entrySet();

        for(Map.Entry<String,Integer> en : entrySet){


            printer.printRecord(en.getKey(),en.getValue());
            //System.out.println(keys+":"+iterator.next());

        }

        printer.flush();
        printer.close();
    }
}
