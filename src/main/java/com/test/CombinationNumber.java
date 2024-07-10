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
public class CombinationNumber {
    public static Map<Integer,Integer> map = new HashMap<>();
    public static void main(String[] args) throws Exception {
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo = null;
        repo = gitService.cloneIfNotExists("erlide_eclipse", "");
        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                //System.out.println("Refactorings at " + commitId);
                 if (!(map.containsKey(refactorings.size()))){
                     map.put(refactorings.size(),1);
                 }
                 else {

                     map.put(refactorings.size(), map.get(refactorings.size())+1);
                 }
            }
        });
        Set<Map.Entry<Integer,Integer>> set1 = map.entrySet();
        List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer,Integer>>(set1);
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        File file = new File("D:\\360\\refactoyData\\commitWithRef\\erlide_eclipseProjectRefCommit.csv");
        if (!file.exists()) {
            file.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("commit中重构的数量","commit数量");
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        CSVPrinter printer = csvFormat.print(fw);

        for (Map.Entry<Integer,Integer> en : list){
            printer.printRecord(en.getKey(),en.getValue());
        }

                //System.out.println(keys+":"+iterator.next());


        printer.flush();
        printer.close();
        bw.close();
        fw.close();


    }


}


