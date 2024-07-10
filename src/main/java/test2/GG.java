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
public class GG {
    private static Map<String,String> map1 = new HashMap<>();
    private static Map<String,Integer> map2 = new HashMap<>();
    private static  String basic = "D:\\360\\r\\idfilepair";
    public static void main(String[] args) throws IOException {
        String fileName= "ws-wss4jfileidPair.csv";
        //int line1 = 395;

        try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : records) {
                /**if (record.getRecordNumber() == line1) {
                    break;
                } else {*/

                    if (record.getRecordNumber() == 1) {
                        continue;
                    } else {

                        String lable = record.get(3);
                        String refact = record.get(0);
                        map1.put(lable,refact);

                    }

               // }


            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        System.out.println(map1);

        Set<Map.Entry<String,String>> entrySet1 = map1.entrySet();
        for (Map.Entry<String,String> en1 : entrySet1){
            String ref = en1.getValue();
            if (map2.containsKey(ref)){
                map2.put(ref,map2.get(ref)+1);
            }else {
                map2.put(ref,1);
            }
        }

        Set<Map.Entry<String,Integer>> entrySet2 = map2.entrySet();
        File file2 = new File("D:\\360\\r\\idfilepair\\1"+fileName );
        FileWriter fw = null;
        fw = new FileWriter(file2,true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("引入缺陷重构","数量","引入缺陷总数量");
        CSVPrinter printer = csvFormat.print(fw);

        for(Map.Entry<String,Integer> en : entrySet2){


            printer.printRecord(en.getKey(),en.getValue(),map1.size());
            //System.out.println(keys+":"+iterator.next());

        }

        printer.flush();
        printer.close();

        }
    }

