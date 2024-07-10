package com.test;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
/**
 */
public class EveryTypeNumber {
    private static Map<Set<String>, Integer> map1 = new HashMap<>();
    private static Map<Integer,Integer> map2 = new HashMap<>();
    public static void main(String[] args) throws Exception {

        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = null;
        String name  =  "wicket";
        repo = gitService.cloneIfNotExists(name, "");
        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                //System.out.println("Refactorings at " + commitId);
                Set<String> typeSet = new HashSet<>();
                for (Refactoring ref : refactorings) {
                    typeSet.add(ref.getName());
                }
                if (!map1.containsKey(typeSet)){
                    map1.put(typeSet,1);
                }
                else {
                    map1.put(typeSet,map1.get(typeSet)+1);
                }
            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }


        });
        Set<Map.Entry<Set<String>,Integer>> s = map1.entrySet();
        int numOfType ;
        int num;
        for (Map.Entry<Set<String>,Integer> en : s){
            numOfType = en.getKey().size();
            num = en.getValue();
            if (map2.containsKey(numOfType)){
                map2.put(numOfType, map2.get(numOfType)+num);
            }else {
                map2.put(numOfType,num);
            }
        }
        File file = new File("D:\\360\\refactoyData\\combinationType"+"\\sumNumber"+name+".csv");
        FileWriter fw1 = null;
        fw1 = new FileWriter(file,true);
        if (!file.exists()) {
            file.createNewFile();
        }
        CSVFormat csvFormat1 = CSVFormat.EXCEL.withHeader("组合重构种类个数","数量");
        CSVPrinter printer1 = csvFormat1.print(fw1);
        Set<Map.Entry<Integer,Integer>> entries = map2.entrySet();
        for (Map.Entry<Integer,Integer> en : entries ){
            printer1.printRecord(en.getKey(),en.getValue());
        }
        printer1.flush();
        printer1.close();
        fw1.close();



            Set<Map.Entry<Set<String>,Integer>> set1 = map1.entrySet();
        List<Map.Entry<Set<String>,Integer>> list = new ArrayList<Map.Entry<Set<String>,Integer>>(set1);
        Collections.sort(list, new Comparator<Map.Entry<Set<String>, Integer>>() {
            @Override
            public int compare(Map.Entry<Set<String>, Integer> o1, Map.Entry<Set<String>, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        File file2 = new File("D:\\360\\refactoyData\\combinationType\\"+name+"TypeNumber.csv" );
        FileWriter fw = null;
        fw = new FileWriter(file2,true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file2.exists()) {
            file2.createNewFile();
        }

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("组合名称","数量");
        CSVPrinter printer = csvFormat.print(fw);
        Set<Map.Entry<Set<String>,Integer>> set = map1.entrySet();
        for (Map.Entry<Set<String>,Integer> en : set){
            printer.printRecord(en.getKey(),en.getValue());

        }

        printer.flush();
        printer.close();
        fw.close();

    }



    }
