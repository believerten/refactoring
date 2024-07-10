package com.test;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class CycleCommit {

    private static String printTime(int commitTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampString=String.valueOf(commitTime);
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = formatter.format(new Date(timestamp));
        return date;
    }
     private static double start;
    private static List<Double> cycleList = new ArrayList<>();
    private static double numOfCommit;
    private static double numOfRCommit;

    public static void main(String[] args) throws Exception {

        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo =  gitService.cloneIfNotExists("paho.mqtt.java", "");

        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {



                //System.out.println("Refactorings at " + commitId);
                String versionTag=commitId;
                RevWalk walk = new RevWalk(repo);
                ObjectId versionId= null;
                try {
                    versionId = repo.resolve(versionTag);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                RevCommit verCommit= null;
                try {
                    verCommit = walk.parseCommit(versionId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int verCommitTime=verCommit.getCommitTime();
               String now = printTime(verCommitTime);
               String end1 = now.substring(0,4).trim();
               int end =Integer.parseInt(end1);
                if (start == 0){
                    start = end;
                } else if ((end - start ) == 1 ){
                    cycleList.add(numOfRCommit/numOfCommit);
                    //System.out.println(numOfRCommit);
                    //System.out.println(numOfCommit);
                    start = end;
                    numOfCommit = 0 ;
                    numOfRCommit = 0;

                } else {
                    if (refactorings.isEmpty()) {
                        numOfCommit += 1;
                    } else {
                        numOfRCommit += 1;

                    }
                }





    }
});

        File file2 = new File("D:\\360\\buginducing\\time\\paho.mqtt.javaTime.csv" );
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
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("ÖØ¹¹±ÈÀý");
        CSVPrinter printer = null;
        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Double i : cycleList){
            printer.printRecord(i);
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