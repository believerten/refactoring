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
 *
 */
public class CombinationType {
    public static Map<Integer, Set<Set<String>>> map1 = new HashMap<>();
    public static int number;
    public static void main(String[] args) throws Exception {
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo = null;
            repo = gitService.cloneIfNotExists("wicket", "https://github.com/apache/wicket.git");

            miner.detectAll(repo, "master", new RefactoringHandler() {
                @Override
                public void handle(String commitId, List<Refactoring> refactorings) {
                    //System.out.println("Refactorings at " + commitId);
                    Set<String> typeSet = new HashSet<>();
                    for (Refactoring ref : refactorings) {
                        typeSet.add(ref.getName());
                    }
                    if (map1.containsKey(typeSet.size())){
                        for (Map.Entry<Integer,Set<Set<String>>> entry : map1.entrySet()){
                            if (entry.getKey() == typeSet.size()){
                                Set<Set<String>> set1 = entry.getValue();
                                set1.add(typeSet);

                            }
                        }

                    }else {
                        Set<Set<String>> set2 = new HashSet<>();
                        set2.add(typeSet);
                        map1.put(typeSet.size(),set2);
                    }


                }
            });/**
        Set<Integer> keySet = map1.keySet();
        for(Integer keys : keySet){
            Iterator<Set<String>> iterator = map1.get(keys).iterator();
            while (iterator.hasNext()){
                printer.printRecord(keys,iterator.next());
                System.out.println(keys+":"+iterator.next());
            }
        }
         */
        File file2 = new File("D:\\360\\refactoyData\\combinationType\\wicketRefactoryType.csv" );
        FileWriter fw = null;
        fw = new FileWriter(file2);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("种类数量","名称");
        CSVPrinter printer = csvFormat.print(fw);
        Set<Integer> keySet = map1.keySet();
        for(Integer keys : keySet){
            Iterator<Set<String>> iterator = map1.get(keys).iterator();
            while (iterator.hasNext()){
                printer.printRecord(keys,iterator.next());
                //System.out.println(keys+":"+iterator.next());
            }
        }

        printer.flush();
        printer.close();


    }
}
