package com.test;

import com.data.TimeRbr;
import gr.uom.java.xmi.diff.CodeRange;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class TimeRBR {

    private static String printTime(int commitTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampString=String.valueOf(commitTime);
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = formatter.format(new Date(timestamp));
        return date;
        //System.out.println("It's commit time: "+date);
    }

    private static List<DiffEntry> getChangedFileList(RevCommit revCommit, Repository repo) {
        List<DiffEntry> returnDiffs = null;

        try {
            RevCommit previsouCommit=getPrevHash(revCommit,repo);
            if(previsouCommit==null)
                return null;
            ObjectId head=revCommit.getTree().getId();

            ObjectId oldHead=previsouCommit.getTree().getId();

            // System.out.println("Printing diff between the Revisions: " + revCommit.getName() + " and " + previsouCommit.getName());

            try (ObjectReader reader = repo.newObjectReader()) {
                CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
                oldTreeIter.reset(reader, oldHead);
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader, head);


                try (Git git = new Git(repo)) {
                    List<DiffEntry> diffs= git.diff()
                            .setNewTree(newTreeIter)
                            .setOldTree(oldTreeIter)
                            .call();
                    for (DiffEntry entry : diffs) {
//                        System.out.println("Entry: " + entry);
                    }
                    returnDiffs=diffs;
                } catch (GitAPIException e) {
                    e.printStackTrace();
                }
            } catch (IncorrectObjectTypeException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return returnDiffs;
    }
    private static RevCommit getPrevHash(RevCommit commit, Repository repo)  throws  IOException {
        try (RevWalk walk = new RevWalk(repo)) {
            // Starting point
            walk.markStart(commit);
            int count = 0;
            for (RevCommit rev : walk) {
                // got the previous commit.
                if (count == 1) {
                    return rev;
                }
                count++;
            }
            walk.dispose();
        }
        //Reached end and no previous commits.
        return null;
    }

    private static String rbr (int dividend, int divisor){
        float resultRbr = (float) dividend/divisor;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String s = decimalFormat.format(resultRbr);
        return  s;
    }

    public static void main(String[] args) throws Exception {
        List<TimeRbr> listTimeRbr = new ArrayList<>();
        Map<Integer,Double> map = new HashMap<>();
        Map<Integer,Double> map1 = new HashMap<>();

        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists("hop", "");
        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()){
                    Set<String> sumFileList = new HashSet<>();
                    Set<String> sumRefactoringFile = new HashSet<>();
                    System.out.println("Refactorings at " + commitId);
                    RevWalk walk = new RevWalk(repo);
                    ObjectId versionId = null;
                    try {
                        versionId = repo.resolve(commitId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    RevCommit verCommit = null;
                    try {
                        verCommit = walk.parseCommit(versionId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    List<DiffEntry> diffFix = getChangedFileList(verCommit, repo);//????????????§Ò?

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    DiffFormatter df = new DiffFormatter(out);
                    df.setRepository(repo);
                    for (DiffEntry entry : diffFix) {

                        try {
                            df.format(entry);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            String diffText = out.toString("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
//				System.out.println(diffText);
                        String fileName = entry.getNewPath();
                        sumFileList.add(fileName);
                    }
                    for (Refactoring ref : refactorings) {

                        List<CodeRange> list1 = ref.leftSide();
                        for (CodeRange range : list1) {
                            System.out.println("" + range.getFilePath());
                            sumRefactoringFile.add(range.getFilePath());
                        }
                        List<CodeRange> list2 = ref.rightSide();
                        for (CodeRange range : list2) {
                            sumRefactoringFile.add(range.getFilePath());
                        }


                    }

                    //Ê±Ðò
                    int verCommitTime=verCommit.getCommitTime();
                    String time = printTime(verCommitTime).substring(0,4);
                    Integer itime = Integer.valueOf(time);

                    TimeRbr timeRbr = new TimeRbr();
                    Double d = Double.parseDouble(rbr(sumRefactoringFile.size(),sumFileList.size()));
                    timeRbr.setRbr(d);
                    timeRbr.setTime(itime);
                    timeRbr.setCommitId(commitId);
                    listTimeRbr.add(timeRbr);



                }

            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }

        });

        for (TimeRbr timeRbr : listTimeRbr){
            map.put(timeRbr.getTime(), timeRbr.getRbr());
        }
        Set<Integer> set = map.keySet();
        for (Integer time : set){
            int number=0 ;
            Double d = 0.0;
            for (TimeRbr timeRbr : listTimeRbr){

                if (time == timeRbr.getTime()){
                    number += 1;
                    d = d + timeRbr.getRbr();
                }

            }
            Double aev = d/number;
            map1.put(number,aev);
        }
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

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("time","timeRBR");
        CSVPrinter printer = null;

        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
      Set<Map.Entry<Integer,Double>> entry = map1.entrySet();
        for (Map.Entry<Integer,Double> en : entry){
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


        //System.out.println(map1);

    }
}
