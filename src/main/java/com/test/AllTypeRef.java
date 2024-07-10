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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class AllTypeRef {
    public static void main(String[] args) throws Exception {
        Map<String,Integer> typeMap = new HashMap<>();
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo = null;
        String name  =  "wicket";
        repo = gitService.cloneIfNotExists(name, "");
        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()) {
                    for (Refactoring ref : refactorings) {
                        if (typeMap.containsKey(ref.getName())){
                            typeMap.put(ref.getName(), typeMap.get(ref.getName())+1);
                        }
                        else {
                            typeMap.put(ref.getName(),1);
                        }

                    }
                }
            }
        });
        File file2 = new File("/home/stu/Documents/lixinyang/result/phoenix/timeRBR.csv" );
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(file2,true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //BufferedWriter bw = new BufferedWriter(fw);

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("name","number");
        CSVPrinter printer = null;

        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<Map.Entry<String, Integer>> entry = typeMap.entrySet();
        for (Map.Entry<String, Integer> en : entry){
            printer.printRecord(en.getKey(),en.getValue());
        }



        try {
            printer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            printer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
