package com.test1;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class CodeLine {


    private static double start;
    private static List<Double> cycleList = new ArrayList<>();
    private static double numOfCommit;
    private static double numOfRCommit;
    public static RevCommit getPrevHash(RevCommit commit, Repository repo)  throws  IOException {
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
    private static List<DiffEntry> getChangedFileList(RevCommit revCommit, Repository repo) {
        List<DiffEntry> returnDiffs = null;

        try {
            RevCommit previsouCommit=getPrevHash(revCommit,repo);
            if(previsouCommit==null)
                return null;
            ObjectId head=revCommit.getTree().getId();

            ObjectId oldHead=previsouCommit.getTree().getId();

            System.out.println("Printing diff between the Revisions: " + revCommit.getName() + " and " + previsouCommit.getName());

            // prepare the two iterators to compute the diff between
            try (ObjectReader reader = repo.newObjectReader()) {
                CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
                oldTreeIter.reset(reader, oldHead);
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader, head);

                // finally get the list of changed files
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnDiffs;
    }

    public static void main(String[] args) throws Exception {

        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        Repository repo = gitService.cloneIfNotExists("paho.mqtt.java", "");

        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()){

                    //System.out.println("Refactorings at " + commitId);
                    String versionTag = commitId;
                    RevWalk walk = new RevWalk(repo);
                    ObjectId versionId = null;
                    try {
                        versionId = repo.resolve(versionTag);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    RevCommit verCommit = null;
                    try {
                        verCommit = walk.parseCommit(versionId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    List<DiffEntry> diffFix=getChangedFileList(verCommit,repo);//获取变更的文件列表

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    DiffFormatter df = new DiffFormatter(out);
//			df.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);//如果加上这句，就是在比较的时候不计算空格，WS的意思是White Space
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

                        System.out.println(entry.getNewPath());//变更文件的路径

                        FileHeader fileHeader = null;
                        try {
                            fileHeader = df.toFileHeader(entry);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        List<HunkHeader> hunks = (List<HunkHeader>) fileHeader.getHunks();
                        int addSize = 0;
                        int subSize = 0;
                        for(HunkHeader hunkHeader:hunks){
                            EditList editList = hunkHeader.toEditList();
                            for(Edit edit : editList){
                                subSize += edit.getEndA()-edit.getBeginA();
                                addSize += edit.getEndB()-edit.getBeginB();
                            }
                        }
                        System.out.println("addSize="+addSize);
                        System.out.println("subSize="+subSize);
                        out.reset();
                    }

                }



            }
        });
/**
        File file2 = new File("D:\\360\\buginducing\\time\\paho.mqtt.javaTime.csv");
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(file2, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("重构比例");
        CSVPrinter printer = null;
        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Double i : cycleList) {
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
        }*/

    }
}

