package com.test1;

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
 *
 */
public class Numoftypeinacommit {
    public static void main(String[] args) throws IOException {
        Map<Integer,Integer> map = new HashMap<>();
        String basic = "D:\\360\\r\\combinationType";
        File file8 = new File(basic);
        String[] resultList = file8.list();

        for (String fileName : resultList) {
            try (Reader reader = Files.newBufferedReader(Paths.get(basic + "\\" + fileName))) {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                for (CSVRecord record : records) {
                    if (record.getRecordNumber() == 1) {
                        continue;
                    } else {

                        String[] strs = record.get(0).split(",");
                        int numberoftype = strs.length;
                        String number = record.get(1);
                        int inumber = Integer.valueOf(number);
                        if (map.containsKey(numberoftype)) {
                            map.put(numberoftype, map.get(numberoftype) + inumber);
                        } else {
                            map.put(numberoftype, inumber);
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }

        File file = new File("D:\\360\\r\\numberOfTypeInACommit\\RefCommit.csv");
        if (!file.exists()) {
            file.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("commit中重构的种类","commit数量");
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        CSVPrinter printer = csvFormat.print(fw);
        Set<Map.Entry<Integer,Integer>> list = map.entrySet();

        for (Map.Entry<Integer,Integer> en : list){
            printer.printRecord(en.getKey(),en.getValue());
        }

        //System.out.println(keys+":"+iterator.next());


        printer.flush();
        printer.close();



    }
}
