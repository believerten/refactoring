package com.test;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Summary {

    public static void main(String[] args) throws IOException {

        String line;
        FileReader fr = null;
        BufferedReader br = null;
        SortedMap<String , Integer> map2 = new TreeMap<>();

        String basePath = "D:\\360\\eclipseRef";
        File dir = new File(basePath);
        List<File> allFileList = new ArrayList<>();

        if (!dir.exists()) {
            System.out.println("目录不存在");
            return;
        }
        getAllFile(dir, allFileList);
        for (File file : allFileList) {
            //System.out.println(file.getName());
            if (file.getName().matches("zuhe4.txt")){
                System.out.println("yes");



                fr = new FileReader(file);
                br = new BufferedReader(fr);
                while((line = br.readLine())!=null) {


                     String[] ss = line.split("\\s+");
                    for (String s : ss) {
                        if (map2.containsKey(s)) {
                            map2.put(s, map2.get(s) + 1);
                        } else {
                            map2.put(s, 1);

                        }
                    }



                }

            }


        }



//在列表里排序

        Set<Map.Entry<String,Integer>> set = map2.entrySet();
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });


        //输出到文件里
        File file = new File("D:\\360\\eclipseRef\\summary\\oneCommitFourRefactorySum.csv");
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file,true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("重构名称","数量");
        CSVPrinter printer = csvFormat.print(writer);

        List<Ref> refArrayList = new ArrayList<>();
        Ref ref = new Ref();


        for(Map.Entry<String,Integer> en : list){
            printer.printRecord(en.getKey(),en.getValue());


        }
        printer.flush();
        printer.close();


    }
    /**
     *
     * @param fileInput
     * @param allFileList
     */
    public static void getAllFile(File fileInput, List<File> allFileList) {
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                getAllFile(file, allFileList);
            } else {
                allFileList.add(file);
            }
        }

    }
}
