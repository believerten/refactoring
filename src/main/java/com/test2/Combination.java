package com.test2;

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
 * ¹¦ÄÜ:
 */
public class Combination {
    private static String name;
    private static Map<Set<String>, Integer> map = new HashMap<>();

    public static void main(String[] args) throws Exception {

        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = null;
        name = "kura";
        repo = gitService.cloneIfNotExists(name, "");
        miner.detectAll(repo, "develop", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()) {


                    //System.out.println("Refactorings at " + commitId);
                    Set<String> typeSet = new HashSet<>();
                    for (Refactoring ref : refactorings) {
                        typeSet.add(ref.getName());
                    }
                    if (!map.containsKey(typeSet)) {
                        map.put(typeSet, 1);
                    } else {
                        map.put(typeSet, map.get(typeSet) + 1);
                    }
                }

            }

            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }
        });
        Set<Map.Entry<Set<String>,Integer>> set1 = map.entrySet();

        File file = new File("D:\\360\\r\\combinationType\\combinationType"+name+"TypeNumber.csv");
        if (!file.exists()) {
            file.createNewFile();
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("combination","number");
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        CSVPrinter printer = csvFormat.print(fw);

        for (Map.Entry<Set<String>,Integer> en : set1){
            printer.printRecord(en.getKey(),en.getValue());
        }

        //System.out.println(keys+":"+iterator.next());


        printer.flush();
        printer.close();

    }
}
