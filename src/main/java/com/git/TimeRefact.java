package com.git;

/**
 *
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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


public class TimeRefact {

    private static String printTime(int commitTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampString=String.valueOf(commitTime);
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = formatter.format(new Date(timestamp));
        return date;
        //System.out.println("It's commit time: "+date);
    }
    private static Repository Repo = null;
    private static Map<Integer,Integer> mapRegular = new HashMap<>();
    private static List<Integer> list = new ArrayList<>();
    private static Map<Integer,Map<String,Integer>> mapRefctory = new HashMap<>();

   // private static Map<Integer,Double>  mapTime = new HashMap<>();

    public static void main(String[] args) throws Exception {
        GitService gitService = new GitServiceImpl();
        String repoName = "tez";

        Repo = gitService.cloneIfNotExists(repoName,"");


        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        miner.detectAll(Repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                Map<String,Integer> refact = new HashMap<>();
                String versionTag = commitId;
                ObjectId versionId= null;
                try {
                    versionId = Repo.resolve(versionTag);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                RevWalk walk = new RevWalk(Repo);
                RevCommit verCommit= null;
                try {
                    verCommit = walk.parseCommit(versionId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int verCommitTime=verCommit.getCommitTime();
                String time = printTime(verCommitTime).substring(0,4);
               // String month = printTime(verCommitTime).substring(5,7);
                //System.out.println(time);
                int year = Integer.parseInt(time) ;
                //int intmonth = Integer.parseInt(month);
                int now = year;//*100+intmonth;
                if (!list.contains(now)){
                    list.add(now);
                }
                System.out.println(now);





                if (!refactorings.isEmpty()) {
                    for (Refactoring ref : refactorings){
                        String refactoring = ref.getName();
                        if (refact.containsKey(ref.getName())){
                            refact.put(refactoring, refact.get(refactoring)+1);
                        }else {
                            refact.put(refactoring,1);
                        }
                    }

                    if (mapRefctory.keySet().contains(now)){
                        Set<Map.Entry<String,Integer>> reset = refact.entrySet();
                        for (Map.Entry<String,Integer> en : reset){
                            if (mapRefctory.get(now).containsKey(en.getKey())){
                                mapRefctory.get(now).put(en.getKey(), en.getValue()+mapRefctory.get(now).get(en.getKey()));
                            }else {
                              mapRefctory.get(now).put(en.getKey(), en.getValue());
                            }
                        }
                    }else {
                        mapRefctory.put(now,refact);
                    }

                }




            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }
        });





       // Set<Map.Entry<Integer,Integer>> entryRegular = mapRegular.entrySet();


        File file2 = new File("/home/stu/Documents/lixinyang/result/"+repoName+"/"+repoName+"monthTime.csv" );
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
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("time","refact","number","nianshu");
        CSVPrinter printer = null;
        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<Map.Entry<Integer,Map<String,Integer>>> set = mapRefctory.entrySet();

        for (Map.Entry<Integer,Map<String,Integer>> en : set){
            printer.printRecord(en.getKey(),en.getValue(),list.size());
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

        fw.close();




    }

}
