package com.data1;

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
public class Time {
    private static String printTime(int commitTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampString=String.valueOf(commitTime);
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = formatter.format(new Date(timestamp));
        return date;
        //System.out.println("It's commit time: "+date);
    }
    public static void main(String[] args) throws Exception {
        Map<String,String> map = new HashMap<>();
        GitService gitService = new GitServiceImpl();
        String repoName = "datafu";

        Repository Repo = gitService.cloneIfNotExists(repoName,"");


        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        miner.detectAll(Repo, "main", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()) {


                    String versionTag = commitId;
                    ObjectId versionId = null;
                    try {
                        versionId = Repo.resolve(versionTag);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    RevWalk walk = new RevWalk(Repo);
                    RevCommit verCommit = null;
                    try {
                        verCommit = walk.parseCommit(versionId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int verCommitTime = verCommit.getCommitTime();
                    String time = printTime(verCommitTime);
                    map.put(commitId,time);
                }else {

                }

            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }
        });

        File file = new File("D:\\360\\ts\\time\\"+repoName+".csv");
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("commit","time");

        CSVPrinter printer = csvFormat.print(fw);
        Set<Map.Entry<String,String>> set = map.entrySet();

        for (Map.Entry<String,String> en : set){
            printer.printRecord(en.getKey(),en.getValue());
        }

    }
}

