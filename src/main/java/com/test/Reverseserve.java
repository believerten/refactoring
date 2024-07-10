package com.test;

import com.data.CodeElement;
import com.data.Reversion;
import gr.uom.java.xmi.diff.CodeRange;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class  Reverseserve {
    private static String printTime(int commitTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampString=String.valueOf(commitTime);
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = formatter.format(new Date(timestamp));
        return date;
    }

    private static List<CodeElement> leftCodeElementList = new ArrayList<>();
    private static List<CodeElement> rightCodeElementList = new ArrayList<>();
    private static List<Reversion> reversionList = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo =  gitService.cloneIfNotExists("storm", "");
        miner.detectAll(repo, "master", new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                if (!refactorings.isEmpty()){
                    for (int i = 0 ; i < refactorings.size();i++){
                        Refactoring ref = refactorings.get(i);

                        List<CodeRange> leftCodeRange = ref.leftSide();
                        for (CodeRange range : leftCodeRange){
                            CodeElement leftCodeElement = new CodeElement();

                            RevWalk walk = new RevWalk(repo);
                            ObjectId versionId = null;
                            try {
                                versionId = repo.resolve(commitId);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            RevCommit commit = null;
                            try {
                                commit = walk.parseCommit(versionId);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            int verCommitTime = commit.getCommitTime();
                            String commitTime = printTime(verCommitTime);
                            leftCodeElement.setTime(commitTime);
                            leftCodeElement.setCodeElement(range.getFilePath()+"\\"+range.getCodeElement());
                            leftCodeElement.setRefactoring(ref.getName());
                            leftCodeElement.setCommitId(commitId);
                            leftCodeElement.setLable(commitId+i);
                            leftCodeElementList.add(leftCodeElement);
                        }
                        List<CodeRange> rightCodeRange = ref.rightSide();
                        for (CodeRange range : rightCodeRange){
                            CodeElement rightCodeElement = new CodeElement();
                            RevWalk walk = new RevWalk(repo);
                            ObjectId versionId = null;
                            try {
                                versionId = repo.resolve(commitId);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            RevCommit commit = null;
                            try {
                                commit = walk.parseCommit(versionId);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            int verCommitTime = commit.getCommitTime();
                            String commitTime = printTime(verCommitTime);
                            rightCodeElement.setTime(commitTime);
                            rightCodeElement.setRefactoring(ref.getName());
                            rightCodeElement.setCodeElement(range.getFilePath()+"\\"+range.getCodeElement());
                            rightCodeElement.setCommitId(commitId);
                            rightCodeElement.setLable(commitId+i);
                            rightCodeElementList.add(rightCodeElement);

                        }


                    }
                }
            }
            @Override
            public void handleException(String commit, Exception e) {
                System.err.println("Error processing commit " + commit);
                e.printStackTrace(System.err);
            }
        });
/**
        for (CodeElement codeElement : leftCodeElementList){
            for (CodeElement codeElement1 : rightCodeElementList){
                if (codeElement.getCodeElement()== codeElement1.getCodeElement()){
                    Reversion reversion = new Reversion();
                    reversion.setCodeElement(codeElement.getCodeElement());
                    reversion.setLeftCommitId(codeElement.getCommitId());
                    reversion.setLeftRefactory(codeElement.getRefactoring());
                    reversion.setLeftTime(codeElement.getTime());
                    reversion.setRightCommitId(codeElement1.getCommitId());
                    reversion.setRightRefactory(codeElement1.getRefactoring());
                    reversion.setRightTime(codeElement1.getTime());
                    reversionList.add(reversion);
                }
            }
        }*/
        for (int i = 0 ; i < leftCodeElementList.size() ; i++){
            CodeElement leftCode = leftCodeElementList.get(i);
            for (int j = 0 ; j < rightCodeElementList.size() ; j++){
                CodeElement rightCode = rightCodeElementList.get(j);
                if (leftCode.getCodeElement().equals(rightCode.getCodeElement()) && !leftCode.getCommitId().equals(rightCode.getCommitId()) && leftCode.getTime().compareTo(rightCode.getTime())>0){
                    Reversion reversion = new Reversion();
                    reversion.setCodeElement(leftCode.getCodeElement());
                    reversion.setLeftCommitId(leftCode.getCommitId());
                    reversion.setLeftRefactory(leftCode.getRefactoring());
                    reversion.setLeftTime(leftCode.getTime());
                    reversion.setRightCommitId(rightCode.getCommitId());
                    reversion.setRightRefactory(rightCode.getRefactoring());
                    reversion.setRightTime(rightCode.getTime());
                    reversion.setLeftlable(leftCode.getLable());
                    reversion.setRightlable(rightCode.getLable());
                    reversionList.add(reversion);
                }
            }
        }
        File file2 = new File("/home/stu/Documents/lixinyang/result/ecf/ecfidReversion.csv" );
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

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("element","leftTime","rightTime","leftRefactory","leftLable","rightRefactory","rightLable","leftCommitid","rightCommitid","sumLable");

        CSVPrinter printer = null;
        try {
            printer = csvFormat.print(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Reversion reversion : reversionList) {
            printer.printRecord(reversion.getCodeElement(),reversion.getLeftTime(),reversion.getRightTime(),reversion.getLeftRefactory(),reversion.getLeftlable(),reversion.getRightRefactory(),reversion.getRightlable(),reversion.getLeftCommitId(),reversion.getRightCommitId(),reversion.getLeftlable()+reversion.getRightlable());
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
