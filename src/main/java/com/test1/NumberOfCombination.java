package com.test1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.*;

/**
 *
 */
public class NumberOfCombination {
    public static void main(String[] args) throws IOException {
        File file1 = new File("D:\\360\\refactoyData\\mina\\refactorysOf2\\zuhe2.txt");
        FileReader fr = new FileReader(file1);
        BufferedReader bf = null;
        bf = new BufferedReader(fr);
        SortedMap<String,Integer> map = new TreeMap<>();
        String line;
        while((line = bf.readLine()) != null ){
            String[] ss = line.split("\\s+");
            for (String s: ss) {
                if (map.containsKey(s)) {
                    map.put(s, map.get(s) + 1);
                } else {
                    map.put(s, 1);
                }
            }

        }
        //把每个组合重构的数量进行排序,得到一个按数量排序的list
        Set<Map.Entry<String,Integer>> set = map.entrySet();
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });



        //输出到文件里

        File file2 = new File("D:\\360\\refactoyData\\mina\\refactorysOf1\\oneCommitOneRefactory.csv" );
        FileWriter fw = null;
        fw = new FileWriter(file2,true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("重构名称","数量");
        CSVPrinter printer = csvFormat.print(fw);
        for ( Map.Entry<String,Integer> en: list){
            printer.printRecord(en.getKey(),en.getValue());

        }
        printer.flush();
        printer.close();



    }

}
